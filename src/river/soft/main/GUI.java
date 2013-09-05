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
		photoCount = pc;
		photoSet = ps;
		vid = new Cam(screensize);
		takePic = new Picture(3000, photoCount, photoSet, vid, this);
		layoutM = new BorderLayout();
	}

	public void buildGUI() {
		this.add(vid);
		vid.screen();
	}

	public void breakGUI() throws Exception {
		throw new Exception();
	}

	@Override
	public void keyPressed(KeyEvent e){
		switch(e.getKeyChar()) {
		case 'q':
			System.exit(0); // break is useless here
		case 's':
			// open settings
			//video.setSettings(set);
			break;
		case 'p':
			// take picture
			pic = new Thread(takePic);
			pic.run();
			
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
