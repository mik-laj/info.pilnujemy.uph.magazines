package info.pilnujemy.uph.magazines.model;

/**
 * Określa informacji o magaznie
 * 
 * @author andrzej
 *
 */
public class Magazine {
	private String title;
	private int no;
	private int year;

	public Magazine(String title, int no, int year) {
		super();
		this.title = title;
		this.no = no;
		this.year = year;
	}

	/**
	 * Zwraca tytuł czasopisma
	 * 
	 * @return tyutl czasopisma
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Ustawia tytuł czasopisma
	 * 
	 * @param title
	 *            tytul czasopisma
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Zwraca numer czasopisma
	 * 
	 * @return numer czasopisma
	 */
	public int getNo() {
		return no;
	}

	/**
	 * Ustawia numer czasopisma
	 * 
	 * @param no
	 *            numer czasopisma
	 */
	public void setNo(int no) {
		this.no = no;
	}

	/**
	 * Zwraca rok wydania czaspisma
	 * 
	 * @return rok czasopisma
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Usstawia rok wydania czasopisma
	 * 
	 * @param year
	 *            rok wydania czasopisma
	 */
	public void setYear(int year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "Magazine [title=" + title + ", no=" + no + ", year=" + year + "]";
	}

}
