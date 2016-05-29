package info.pilnujemy.uph.magazines.view;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JTextField;
import javax.swing.text.Document;

public class PlaceholderTextField extends JTextField {
	private static final long serialVersionUID = -3015044342607496146L;
	private String placeholder;

	public PlaceholderTextField() {
		super();
	}

	public PlaceholderTextField(Document doc, String text, int columns) {
		super(doc, text, columns);
	}

	public PlaceholderTextField(int columns) {
		super(columns);
	}

	public PlaceholderTextField(String text, int columns) {
		super(text, columns);
	}

	public PlaceholderTextField(String text) {
		super(text);
	}

	@Override
	protected void paintComponent(Graphics pG) {
		super.paintComponent(pG);

		if (placeholder.length() == 0 || getText().length() > 0) {
			return;
		}

		final Graphics2D g = (Graphics2D) pG;
		g.setColor(getDisabledTextColor());
		g.drawString(placeholder, getInsets().left, g.getFontMetrics().getMaxAscent() + getInsets().top);
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

}
