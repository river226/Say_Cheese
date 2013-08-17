package river.soft.main;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JWindow;
import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_highgui;
import com.googlecode.javacv.cpp.opencv_highgui.CvCapture;

public class Cam extends JWindow{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//private int pics, time;
	//private String name, location;
	private CanvasFrame canvas;
	private String[] args;
	private CanvasFrame vid;


	public Cam(Settings set) {
		// TODO Auto-generated constructor stub
	}

	public void setSettings(Settings set) {
		// TODO
	}
	
	public CanvasFrame getCam() {
		return vid;
	}

	public void screen() {
		
		CvCapture capture = opencv_highgui.cvCreateCameraCapture(0);

        opencv_highgui.cvSetCaptureProperty(capture, opencv_highgui.CV_CAP_PROP_FRAME_HEIGHT, 720);
        opencv_highgui.cvSetCaptureProperty(capture, opencv_highgui.CV_CAP_PROP_FRAME_WIDTH, 1280);

        IplImage grabbedImage = opencv_highgui.cvQueryFrame(capture);

        CanvasFrame frame = new CanvasFrame("Webcam");

        while (frame.isVisible() && (grabbedImage = opencv_highgui.cvQueryFrame(capture)) != null) {
            frame.showImage(grabbedImage);
        }

        frame.dispose();
        opencv_highgui.cvReleaseCapture(capture);
		
		/*CvCapture capture = opencv_highgui.cvCreateCameraCapture(0);
		double inverseGamma = 1.0;
		Graphics g;

        opencv_highgui.cvSetCaptureProperty(capture, opencv_highgui.CV_CAP_PROP_FRAME_HEIGHT, 720);
        opencv_highgui.cvSetCaptureProperty(capture, opencv_highgui.CV_CAP_PROP_FRAME_WIDTH, 1280);

        IplImage grabbedImage = opencv_highgui.cvQueryFrame(capture);

        vid = new CanvasFrame("Webcam");

        while (vid.isVisible() && (grabbedImage = opencv_highgui.cvQueryFrame(capture)) != null) {
        	Image i = grabbedImage.getBufferedImage(grabbedImage.getBufferedImageType() == BufferedImage.TYPE_CUSTOM ? 1.0 : inverseGamma, false);
        	g.drawImage(i, HEIGHT, WIDTH, observer);
            vid.paintComponents(g);
        }

        vid.dispose();
        opencv_highgui.cvReleaseCapture(capture);*/
	}

	public Component picCount() {
		// TODO Auto-generated method stub
		return null;
	}

	public Component vidTimer() {
		// TODO Auto-generated method stub
		return null;
	}

}


