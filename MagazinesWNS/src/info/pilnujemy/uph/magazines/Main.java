package info.pilnujemy.uph.magazines;

import java.awt.EventQueue;

/**
 * Kod uruchamiający aplikacje
 * 
 * @author andrzej
 *
 */
public class Main {
	/**
	 * Uruchamia aplikacje
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListFrame frame = new ListFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
