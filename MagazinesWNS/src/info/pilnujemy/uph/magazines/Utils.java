package info.pilnujemy.uph.magazines;

/**
 * Klasa pomocniczaa
 * 
 * @author andrzej
 *
 */
public class Utils {

	/**
	 * Funkcja pomocnicza, która pozwala sprawdzić, czy String przekazany
	 * skłądaa się tylko z cyfr
	 * 
	 * @param str
	 *            string do sprawdzenia
	 * @return jeśli składa się z samych cyfr to true
	 */
	public static boolean isNumeric(String str) {
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c))
				return false;
		}
		return true;
	}

}
