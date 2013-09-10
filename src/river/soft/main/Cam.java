package river.soft.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

import javax.swing.JPanel;

import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_highgui;
import com.googlecode.javacv.cpp.opencv_highgui.CvCapture;

public class Cam extends JPanel {
	
	private BorderLayout layout = new BorderLayout();		
	private BufferedImage image;
	private Dimension screensize;
	private boolean end = false;
	
	
	public Cam(Dimension ss) {
		super();
		screensize = ss;
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

	public BufferedImage getImage() {
		return image;
	}

}
