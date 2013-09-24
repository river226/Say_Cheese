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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.googlecode.javacv.cpp.opencv_highgui;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_highgui.CvCapture;

public class GUI extends JFrame implements KeyListener{

	/**
	 * Keep eclipse happy
	 */
	private static final long serialVersionUID = 1L;


	private Dimension screensize; // height and width of the window
	private Settings set; // Not implemented Yet
	private Picture takePic;
	private Cam vid;
	private int photoCount, curPhoto, photoSet;
	private BorderLayout layoutM;
	private Thread pic, print, film;

	public GUI() throws Exception {

		// get dimensions
		screensize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screensize);
		this.setUndecorated(true);
		this.addKeyListener(this);
		photoCount = 6;
		photoSet = 1;
		curPhoto = 0;
		vid = new Cam(screensize);
		layoutM = new BorderLayout();
	}

	public void buildGUI() {
		this.add(vid);
		//vid.setVisible(true);
		vid.screen();
	}

	public void breakGUI() throws Exception {
		throw new Exception();
	}
	
	private void setCount(int progress) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e){
		switch(e.getKeyChar()) {
		case 'q': // Close Program
			System.exit(0); // break is useless here
		case 's': // open settings
			break;
		case 'p': // take picture
			if(takePic == null || SwingWorker.StateValue.DONE == takePic.getState()) {

				takePic = new Picture(3000, photoCount, vid);
				takePic.execute();
				takePic.addPropertyChangeListener(new PropertyChangeListener(){

					@Override
					public void propertyChange(PropertyChangeEvent arg0) {
						if(SwingWorker.StateValue.STARTED == takePic.getState()) {
							setCount(takePic.getProgress());
						} else if (SwingWorker.StateValue.DONE == takePic.getState()){
							
							try {
								ArrayList<BufferedImage> p = takePic.get();
								curPhoto = 0;
								
								for(BufferedImage i : p) {
									File outputfile = new File("Photo_Set_" + photoSet + "_Num_" + curPhoto + ".jpg");
									ImageIO.write(i, "jpg", outputfile);
									curPhoto++;
								}
								
								photoSet++;
							} catch (InterruptedException | ExecutionException | IOException e1) { /* Do Nothing */}
						}

					}
					});

			}
			break;
		case 'v': // take video
			break;
		default: /* ignore */ break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) { /* ignore */ }

	@Override
	public void keyTyped(KeyEvent e) { /* ignore */ }
}
