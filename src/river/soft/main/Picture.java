package river.soft.main;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.SwingWorker;

public class Picture extends SwingWorker<ArrayList<BufferedImage>, Integer> {

	private int maxPhoto;
	private Cam vid;
	private long waitPeriod;
	private ArrayList<BufferedImage> image = new ArrayList<>();
	
	public Picture(int i, int mp, Cam v) {
		waitPeriod = i;
		maxPhoto = mp;
		vid = v;
	}

	@Override
	protected  ArrayList<BufferedImage> doInBackground() throws Exception {
		int curPhoto = 0;
		
		while(curPhoto < maxPhoto){
			curPhoto++;
			try {
				image.add(vid.getImage());
				this.setProgress((Integer)((curPhoto/maxPhoto)*100));
				Thread.sleep(waitPeriod);
			} catch (InterruptedException e1) { /* DO NOTHING */ }

		}
		
		return image;
	}
	
	public void setMaxPhoto(int m) {
		maxPhoto = m;
	}

}
