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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
	private Cam vid; 
	private BufferedImage image;
	private ArrayList<Image> camRoll = new ArrayList<>();
	private int photoCount, curPhoto, photoSet;
	private long waitPeriod = 3000;
	private boolean end = false;
	private JLabel left;
	private BorderLayout layoutM;

	public GUI() throws Exception {

		// get dimensions
		screensize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screensize);
		this.setUndecorated(true);
		this.addKeyListener(this);
		photoCount = 6;
		curPhoto = 0;
		photoSet = 1;
		left = new JLabel((photoCount-curPhoto) + " Photos left");
		layoutM = new BorderLayout();
	}

	public void buildGUI() {
		this.add(vid = new Cam(screensize));
		vid.screen();
	}

	public void breakGUI() throws Exception {
		throw new Exception();
	}

	@Override
	public void keyPressed(KeyEvent e){
		switch(e.getKeyChar()) {
		case 'q':
			end = true;
			System.exit(0); // break is useless here
		case 's':
			// open settings
			//video.setSettings(set);
			break;
		case 'p':
			// take picture
			while(curPhoto < photoCount){
				File outputfile = new File("Photo_Set_" + photoSet + "_Num_" + curPhoto + ".jpg");
				curPhoto++;

				try {
					ImageIO.write(vid.getImage(), "jpg", outputfile);
					Thread.sleep(waitPeriod);
				} catch (IOException | InterruptedException e1) {
				}
				
			}
			photoSet++;
			curPhoto = 0;
			break;
		case 'v':
			// take video
			break;
		default:
			// ignore
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// ignore
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// ignore	
	}
}
