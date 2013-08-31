package river.soft.main;

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
	// screen is customized to match the resolution and orientation of the monitor
	private Dimension screensize; // height and width of the window
	private Settings set;
	private String[] args;
	private Cam vid;
	private BufferedImage image;
	private ArrayList<Image> camRoll = new ArrayList<>();
	private boolean end = false;
	
	public GUI() throws Exception {
		
		// get dimensions
		screensize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screensize);
		this.setUndecorated(true);
		this.addKeyListener(this);
	}

	public void buildGUI() {
		//set = new Settings();
		this.add(vid = new Cam());
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
			File outputfile = new File("image.jpg");
			try {
				ImageIO.write(image, "jpg", outputfile);
			} catch (IOException e1) {
			}
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

	private class Cam extends JPanel {
		public void screen() {
			CvCapture capture = opencv_highgui.cvCreateCameraCapture(1);

			opencv_highgui.cvSetCaptureProperty(capture, opencv_highgui.CV_CAP_PROP_FRAME_HEIGHT, 720);
			opencv_highgui.cvSetCaptureProperty(capture, opencv_highgui.CV_CAP_PROP_FRAME_WIDTH, 1280);

			IplImage grabbedImage = opencv_highgui.cvQueryFrame(capture);

			while ((grabbedImage = opencv_highgui.cvQueryFrame(capture)) != null) {
				this.setImage(grabbedImage.getBufferedImage());
				if(end) break;
			}

			opencv_highgui.cvReleaseCapture(capture);
		}

		private void setImage(BufferedImage img) {
			image = img;
			repaint();
		}

		protected void paintComponent(Graphics g) {
			//vid.paintComponent(g);
			if (image != null)      // Not efficient, but safer.
				g.drawImage(image, 0, 0, this);
		}
	}
}
