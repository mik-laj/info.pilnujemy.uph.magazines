package info.pilnujemy.uph.magazines.filter;

import java.util.regex.Pattern;

import javax.swing.RowFilter;

public class SmartTextRowFilter<M, I> extends RowFilter<M, I> {

	private RowFilter<Object, Object> filter;

	public SmartTextRowFilter(String keyword, int index) {
		super();
		String pattern = Pattern.quote(keyword);
		// Pattern.CASE_INSENSITIVE 	(?i)
		pattern = "(?i)" + pattern.replace("*", "\\E.+\\Q");
		System.out.println(pattern);
		this.filter = RowFilter.regexFilter(pattern, index);
	}

	@Override
	public boolean include(javax.swing.RowFilter.Entry<? extends M, ? extends I> entry) {
		return filter.include(entry);
	}
}