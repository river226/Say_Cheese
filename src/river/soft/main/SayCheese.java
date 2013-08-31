/**
 * Say Cheese - Photobooth application
 * developed by river226
 */
package river.soft.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
			window.setVisible(true);
			window.buildGUI();
		} catch (Exception e) {
			System.exit(0);
		}
	}


}
