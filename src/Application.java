import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;

import java.util.*;

/**
 * This class encapsulates the GUI for the application.
 * @author Rob Kelley
 * @version 1.0
 * Programming Project 1
 * Fall 2018
 */

public class Application extends JFrame {
	
	private JFileChooser fc;
	
	private JPanel pnlButtons;
	private JButton btnChoose;
	private JButton btnProcess;
	
	
	private JPanel pnlSpeech;
	private JTextArea jtaSpeech;
	private JScrollPane scrollSpeech;
	
	private JPanel pnlDictionary;
	private JTextArea jtaDictionary;
	private JScrollPane scrollDictionary;
	
	private LinkedPriorityQueue<String> pq;
	private HashedDictionary<String, Integer> dictionary;
		
	/**
	 * Constructor sets up the display.
	 */
	public Application() {
		super("Speech Processor");
		this.setLayout(new BorderLayout());
		
		pnlButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
		btnChoose = new JButton("Choose File");
		pnlButtons.add(btnChoose);
		
		btnProcess = new JButton("Process File");
		pnlButtons.add(btnProcess);
		
		ButtonHandler handler = new ButtonHandler();
		btnChoose.addActionListener(handler);
		btnProcess.addActionListener(handler);
		
		add(pnlButtons, BorderLayout.NORTH);
		
		pnlSpeech = new JPanel();
		pnlSpeech.setBorder ( new TitledBorder ( new EtchedBorder (), "Speech Text" ) );
		jtaSpeech = new JTextArea(20,50);
		jtaSpeech.setLineWrap(true);
		jtaSpeech.setWrapStyleWord(true);
		scrollSpeech = new JScrollPane(jtaSpeech); 
		scrollSpeech.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pnlSpeech.add(scrollSpeech);
		add(pnlSpeech, BorderLayout.WEST);
		
		pnlDictionary = new JPanel();
		pnlDictionary.setBorder ( new TitledBorder ( new EtchedBorder (), "Dictionary" ) );
		jtaDictionary = new JTextArea(30,20);
		jtaDictionary.setLineWrap(true);
		jtaDictionary.setWrapStyleWord(true);
		scrollDictionary = new JScrollPane(jtaDictionary); 
		scrollDictionary.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pnlDictionary.add(scrollDictionary);
		add(pnlDictionary, BorderLayout.EAST);
		
		pq = new LinkedPriorityQueue<String>();
		
	}//end constructor

	/**
	 * This inner class supports button clicks.
	 * @author Rob Kelley
	 * @version 1.0
	 * Programming Project 1
	 * Fall 2018
	 */
	public class ButtonHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==btnProcess)
				buildDictionary();
			if(e.getSource()==btnChoose)
				fillPQ();
			
		}//end actionPerformed
		
	}//end inner class

	/**
	 * This method consumes the data from the priority queue, counts the 
	 * number of occurrences for each word, computes the hash and places the
	 * hash and the data into the dictionary. Finally, it displays the dictionary
	 * the jtaDictionary.
	 * Precondition: the priority queue is not empty.
	 */
	private void buildDictionary() {
			
	}//end build dictionary
		
	 /** 
	   * This method launches a JFileChooser object, 
	   * allows user to navigate the files system to 
	   * choose a file to process.
	   * @return
	   */
	 private String getFileOrDirectoryPath()
	 {
	    // configure dialog allowing selection of a file or directory
	    JFileChooser fileChooser = new JFileChooser();
	    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	    int result = fileChooser.showOpenDialog(this);

	    // if user clicked Cancel button on dialog, return
	    if (result == JFileChooser.CANCEL_OPTION)
	       return null;

	    // return Path (as a string) representing the selected file
	    return fileChooser.getSelectedFile().getAbsolutePath();
	    
	 }//end getFileOrDirectoryPath

	 /**
	  * This method extracts the works from the speech and puts them into
	  * a priority queue and displays the speech in the JTextArea.
	  */
	 private void fillPQ() {
		 String fileName = getFileOrDirectoryPath();
		 StringBuilder sb = new StringBuilder();
		 Scanner input = null;
		 
		 try {
			input = new Scanner(Paths.get(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
		 if(fileName!=null) {
			 while(input.hasNextLine()) {
				 String nextLine = input.nextLine().replaceAll("[^a-zA-Z0-9]", " ").toLowerCase().trim();
				 StringTokenizer tokens = new StringTokenizer(nextLine);
				 while(tokens.hasMoreTokens()) {
					 pq.add(tokens.nextToken());
				 }
				 
				 sb.append(nextLine+"\n");
			 }
		 }
		 jtaSpeech.setText(sb.toString());
	 }//end processFile
	
	public static void main(String[] args) {
		
		Application app = new Application();
		app.setDefaultCloseOperation(EXIT_ON_CLOSE);
		app.setLocationRelativeTo(null);
		app.pack();
		app.setVisible(true);

	}//end main

}//end class
