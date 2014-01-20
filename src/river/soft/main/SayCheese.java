/**
 * Say Cheese - Photobooth application
 * developed by river226
 */
package river.soft.main;

/**
 * @author river
 *
 */
public class SayCheese {

	/**
	 * @param args
	 */

	private static GUI window;

	public static void main(String[] args) {
		try {
			window = new GUI();
			window.setVisible(true); // create the GUI Frame
			window.buildGUI(); // pull up WebCam feed
		} catch (Exception e) {
			System.exit(0); 
			// Create Log
		}
	}


}
