package river.soft.main;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.SwingWorker;

public class Picture extends SwingWorker<GUI, Cam> {

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

	public void process() {
		
		int curPhoto = 0;
		
		while(curPhoto < photoCount){
			File outputfile = new File("Photo_Set_" + photoSet + "_Num_" + curPhoto + ".jpg");
			curPhoto++;

			try {
				ImageIO.write(vid.getImage(), "jpg", outputfile);
				doInBackground();
			} catch (IOException | InterruptedException e1) {
			} catch (Exception e) { /* DO NOTHING */ }

		}
		photoSet++;
		curPhoto = 0;
	}

	@Override
	protected GUI doInBackground() throws Exception {
		Thread.sleep(waitPeriod);
		return null;
	}

}
