package river.soft.main;

import java.io.File;

public class Settings {
	
	private File settings; 
	//private String PhotoName;
	
	public Settings(){
		settings = new File("Settings.scj"); 
		// .scj will be default file format for this program
		
		if(settings.exists()) readInFile();
		else writeDefaultSettings();
		
	}

	private void readInFile() {
		// TODO Auto-generated method stub
		
	}

	private void writeDefaultSettings() {
		// TODO Auto-generated method stub
	}
	

}
