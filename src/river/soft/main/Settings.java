package river.soft.main;

/**
 * Setting panel for Say Cheese
 * Will provide supported functions, and provide 
 * 
 * program defaults when it is complete
 */

import java.io.File;

public class Settings {
	
	private File settings; 
	private String log;
	//private String PhotoName;
	
	public Settings(){
		settings = new File("Settings.scj"); 
		// .scj will be default file format for this program
		log = makeLog();
		
		if(settings.exists()) readInFile();
		else writeDefaultSettings();
	}

	private void readInFile() {
		// TODO Auto-generated method stub
		
	}

	private void writeDefaultSettings() {
		// TODO Auto-generated method stub
	}
	
	/**
	 * @return Caption for log
	 */
	private String makeLog() {
		return "Settings Log \n============\n";
	}
	
	/**
	 * @param message to be added to the log
	 */
	@SuppressWarnings("unused")
	private void updateLog(String message) {
		log += message + "\n";
}
	
	/**
	 * @return get the log of any and all issues reported by this program
	 */
	public String getLog() {
		return log;
	}
}
