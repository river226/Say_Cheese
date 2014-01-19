package river.soft.main;

/**
 * @author river
 * This class builds the GUI and as of this moment sets up the Cam feed
 * Ugly code needs to be fixed. 
 */

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GUI extends JFrame implements KeyListener{

	/**
	 * Keep eclipse happy
	 */
	private static final long serialVersionUID = 1L;


	private Dimension screensize; // resolution of the monitor
	@SuppressWarnings("unused")
	private Settings set; // Not implemented Yet
	private Picture takePic; // Handles pulling pictures from the cam
	private Cam vid; // The video feed from the webcam
	private int photoCount; // Number of photos in the set
	private int curPhoto; // The current photo being saved
	private int photoSet; // The current photo set for saving purposes

	public GUI() throws Exception { // Default Builder of GUI

		// get dimensions
		screensize = Toolkit.getDefaultToolkit().getScreenSize();
		
		// set characteristics of this JFrame
		this.setSize(screensize);
		this.setUndecorated(true);
		this.addKeyListener(this);
		this.setBackground(Color.white);
		
		// Set defaults for the program
		setPhoto();
		
		// Create Webcam feed
		vid = new Cam(screensize);
		
		// Set Layout for JFrame
		new BorderLayout();
	}

	/**
	 * Sets the default counts for the photo saving
	 * ** Future version will call to settings **
	 */
	private void setPhoto() {
		photoCount = 6;
		photoSet = 1;
		curPhoto = 0;
	}

	/**
	 * Adds video feed to the GUI
	 */
	public void buildGUI() {
		this.add(vid);
		vid.screen(); // Activate Video
	}

	/**
	 * Operation for closing down application safely
	 * not currently used. remove?
	 * @throws Exception
	 */
	public void breakGUI() throws Exception {
		throw new Exception();
	}

	/**
	 * Changes the background color to indicate that 
	 * the cam is currently taking pictures
	 * @param progress
	 */
	private void setCount(int progress) {
		if(progress == 5000) // done taking pictures
			this.setBackground(Color.white);
		else // Starting to take pictures 
			this.setBackground(Color.black);
	}

	@Override
	public void keyPressed(KeyEvent e){
		switch(e.getKeyChar()) {
		case 'q': // Close Program
			System.exit(0); // break is useless here
			
		case 's': 
			/**
			 *  Open Settings, not implemented yet
			 */
			break;
			
		case 'p': // take picture
			// First make sure we are not currently taking pictures
			if(takePic == null || SwingWorker.StateValue.DONE == takePic.getState()) {

				takePic = new Picture(3000, photoCount, vid); // Set picture to current settings
				takePic.execute(); // Activate SwingWorker to run without freezing cam
				
				takePic.addPropertyChangeListener(new PropertyChangeListener(){ 

					@Override
					public void propertyChange(PropertyChangeEvent arg0) { // Listen for SwingWorker state changes
						setCount(takePic.getProgress()); // Change Background color
						if (SwingWorker.StateValue.DONE == takePic.getState()){ // Cam is done taking pictures
							snapPicture();
						} 

					}
				});
			}
			break;
			
		case 'v': 
			/** 
			 * Record Video, not implemented yet
			 */
			break;
			
		default: /* ignore */ break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) { /* ignore */ }

	@Override
	public void keyTyped(KeyEvent e) { /* ignore */ }

	/**
	 * Records all snapped pictures to file, and sends them to the printer
	 */
	private void snapPicture() {
		try {
			ArrayList<BufferedImage> p = takePic.get(); // Gather images for saving/printing
			curPhoto = 0; // reset current photo count

			// cycle through and save photos to computer for later collection
			for(BufferedImage i : p) {
				File temp = new File("Pics/Set" + photoSet);
				if(curPhoto == 0) temp.mkdirs();
				
				File outputfile = new File(temp.getAbsolutePath() + "/Photo_Set_" + photoSet + "_Num_" + curPhoto + ".jpg");
				ImageIO.write(i, "jpg", outputfile); // ** Future versions will access settings for file name **
				curPhoto++;
			}

			// Send to the printer
			@SuppressWarnings("unused")
			PrinterJob printJob = printPictures();     
			//printJob.print();
			setCount(5000); // reset background
			photoSet++;
		} catch (InterruptedException | ExecutionException | IOException  /*| PrinterException*/ e1) { /* Do Nothing */}
		// ** future versions will log issues for bug detection **
	}

	/** 
	 * Send the current photos to the printer to be organized for printing
	 * will print from snap pictures
	 * @return
	 */
	private PrinterJob printPictures() {
		PrinterJob printJob = PrinterJob.getPrinterJob();
		printJob.setPrintable(new Printer(){
			public int print(Graphics g0, PageFormat pf, int pg) throws PrinterException {
				// TODO Auto-generated method stub
				@SuppressWarnings("unused")
				PrinterJob job = PrinterJob.getPrinterJob();


				return 0;
			}
		});
		return printJob;
	}
}