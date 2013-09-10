package river.soft.main;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Picture implements Runnable {

	private int photoSet, photoCount;
	private Cam vid;
	private long waitPeriod;
	private GUI gui;
	
	public Picture(int i, int pc, int ps, Cam v, GUI g) {
		waitPeriod = i;
		photoCount = pc;
		photoSet = ps;
		vid = v;
		gui = g;
	}

	@Override
	public void run() {
		
		int curPhoto = 0;
		
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
	}

}
