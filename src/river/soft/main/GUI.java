package river.soft.main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class GUI extends JFrame implements KeyListener{

	/**
	 * Keep eclipse happy
	 */
	private static final long serialVersionUID = 1L;
	// screen is customized to match the resolution and orientation of the monitor
	private Dimension screensize; // height and width of the window
	private Cam video;
	private Settings set;
	
	public GUI() throws Exception {
		
		// get dimensions
		screensize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screensize);
		this.setUndecorated(true);
		this.addKeyListener(this);
	}

	public void buildGUI() {
		set = new Settings();
		video = new Cam(set);
		video.screen();
		//this.add(video.getCam());
	}

	public void breakGUI() throws Exception {
		throw new Exception();
	}

	@Override
	public void keyPressed(KeyEvent e){
		switch(e.getKeyChar()) {
		case 'q':
			System.exit(0); // break is useless here
		case 's':
			// open settings
			video.setSettings(set);
			break;
		case 'p':
			// take picture
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

}
