package river.soft.main;

/**
 * The picture class manages picture taking for Say Cheese.
 * It runs as an independent SwingWorker thread 
 * to grab a pic from the WebCam feed every 3 seconds by default.
 */

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.SwingWorker;

public class Picture extends SwingWorker<ArrayList<BufferedImage>, Integer> {

	private int maxPhoto; // Number of pictures to be taken
	private Cam vid; // The WebCam Feed
	private long waitPeriod; // Wait period between pictures, default 3 seconds
	private ArrayList<BufferedImage> image = new ArrayList<>(); // All pictures taken
	private String log; // String to write a log for the program issue
	
	public Picture(int i, int mp, Cam v) {
		waitPeriod = i;
		maxPhoto = mp;
		vid = v;
		log = makeLog();
	}

	/**
	 * @return Caption for log
	 */
	private String makeLog() {
		return "Picture Log \n===========\n";
	}
	
	/**
	 * @param message to be added to the log
	 */
	private void updateLog(String message) {
		log += message + "\n";
	}
	
	/**
	 * @return get the log of any and all issues reported by this program
	 */
	public String getLog() {
		return log;
	}

	@Override
	protected  ArrayList<BufferedImage> doInBackground() throws Exception {
		int curPhoto = 0; // counter for current picture
		
		while(curPhoto < maxPhoto){
			curPhoto++;
			try {
				image.add(vid.getImage()); // Pull image from WebCam feed
				this.setProgress((Integer)((curPhoto/maxPhoto)*100)); 
				Thread.sleep(waitPeriod); // wait to grab new picture
			} catch (InterruptedException e1) { 
				updateLog(e1.getMessage());
			} 

		}
		updateLog("Set Finished at: " + System.nanoTime());
		return image;
	}
	
	/**
	 * Resets the number of pictures to be taken
	 * @param the number of pictures to be taken
	 */
	public void setMaxPhoto(int m) {
		maxPhoto = m;
		updateLog("max reset to: " + m);
	}
}
