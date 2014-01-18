package river.soft.main;

/**
 * The cam class uses OpenCV wrapper JavaCV to pull video from a webcam
 * It puts the video feed into a jpanel for easy integration to the greater GUI class
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
	private Dimension screensize;
	private boolean end = false;
	private boolean flashed =  false;
	private float[] scales = {1f, 1f, 1f, 0.5f};
	private float[] offsets = new float[4];
	public Cam(Dimension ss) {
		super();
		screensize = ss;
		 new RescaleOp(scales, offsets, null);
	}

	public void endcam() {
		end = true;
	}

	public void screen() {
		CvCapture capture = opencv_highgui.cvCreateCameraCapture(0);


		opencv_highgui.cvSetCaptureProperty(capture, opencv_highgui.CV_CAP_PROP_FRAME_HEIGHT, 720);
		opencv_highgui.cvSetCaptureProperty(capture, opencv_highgui.CV_CAP_PROP_FRAME_WIDTH, 1280);

		IplImage grabbedImage = opencv_highgui.cvQueryFrame(capture);

		while ((grabbedImage = opencv_highgui.cvQueryFrame(capture)) != null) {
			if(flashed) flash();
			else this.setImage(grabbedImage.getBufferedImage());
			if(end) break;
		}

		opencv_highgui.cvReleaseCapture(capture);
	}

	private void setImage(BufferedImage img) {
		image = img;
		repaint();
	}

	protected void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
        if (image != null) // Not efficient, but safer.
                g.drawImage(image, ((screensize.width - image.getWidth())/2), ((screensize.height - image.getHeight())/4), this);
	}

	public BufferedImage getImage() {
		BufferedImage temp = image;
		flashed = true;
		return temp;
	}

	public void flash() {
		if(!flashed) {
			
	        flashed = true;
		} else {
			this.scales[3] = 0.75f;
	        new RescaleOp(scales, offsets, null);
			flashed = false;
		}
		
		
	}

}
