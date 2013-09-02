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
	private int photoCount, curPhoto, photoSet;
	private long waitPeriod = 3000;
	private boolean end = false;
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
		layoutM = new BorderLayout();
	}

	public void buildGUI() {
		//set = new Settings();
		this.add(vid = new Cam());
		//layoutM.addLayoutComponent(vid = new Cam(), BorderLayout.CENTER);
		//this.setLayout(layoutM);
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
					ImageIO.write(image, "jpg", outputfile);
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

	private class Cam extends JPanel {

		private BorderLayout layout = new BorderLayout();		
		public Cam() {
			super();
		}

		public void screen() {
			CvCapture capture = opencv_highgui.cvCreateCameraCapture(0);

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
				g.drawImage(image, ((screensize.width - image.getWidth())/2), ((screensize.height - image.getHeight())/4), this);
		}

	}
}
