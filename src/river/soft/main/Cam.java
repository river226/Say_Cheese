package river.soft.main;

/**
 * The cam class uses OpenCV wrapper JavaCV to pull video from a webcam
 * It puts the video feed into a JPanel for easy integration to the greater GUI class
 * @author river
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import javax.swing.JPanel;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_highgui;
import com.googlecode.javacv.cpp.opencv_highgui.CvCapture;

public class Cam extends JPanel {

	/**
	 * keep eclipse happy
	 */
	private static final long serialVersionUID = 1L;
	
	private BufferedImage image; 
	private Dimension screensize; // Used to make video fit in dimensions of the monitor
	private boolean end = false; // Tells the cam to stop
	private float[] scales = {1f, 1f, 1f, 0.5f}; // default scales for the cam
	private float[] offsets = new float[4]; // default setting
	
	public Cam(Dimension ss) {
		super();
		screensize = ss;
		new RescaleOp(scales, offsets, null);
	}

	/**
	 * set the webcam feed to close
	 */
	public void endcam() {
		end = true;
	}

	/**
	 * creates and ends webcam feed
	 */
	public void screen() {
		CvCapture capture = opencv_highgui.cvCreateCameraCapture(0); // 0 = internal, 1 = external

		opencv_highgui.cvSetCaptureProperty(capture, opencv_highgui.CV_CAP_PROP_FRAME_HEIGHT, 720); // set video height
		opencv_highgui.cvSetCaptureProperty(capture, opencv_highgui.CV_CAP_PROP_FRAME_WIDTH, 1280); // set video width

		IplImage grabbedImage = opencv_highgui.cvQueryFrame(capture);  // start video capture

		while ((grabbedImage = opencv_highgui.cvQueryFrame(capture)) != null) { // Should never be false, thats why we break
			this.setImage(grabbedImage.getBufferedImage()); // display current image
			if(end) break; // end program
		}

		opencv_highgui.cvReleaseCapture(capture); // end video capture
	}

	/**
	 * define the current image for the program and 
	 * paint the current image to the screen
	 * @param img
	 */
	private void setImage(BufferedImage img) {
		image = img;
		// ** flash here **
		repaint();
	}

	/**
	 * Paint the image to the screen
	 */
	protected void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
        if (image != null) // Not efficient, but safer.
                g.drawImage(image, ((screensize.width - image.getWidth())/2), ((screensize.height - image.getHeight())/4), this);
	}

	/**
	 * pull the current image for archive purposes
	 * @return the current image as a BufferedImage
	 */
	public BufferedImage getImage() {
		BufferedImage temp = image;
		return temp;	
	}

}
