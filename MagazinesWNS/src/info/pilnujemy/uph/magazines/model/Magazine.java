package info.pilnujemy.uph.magazines.model;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "Magazine [title=" + title + ", no=" + no + ", year=" + year + "]";
	}

}
