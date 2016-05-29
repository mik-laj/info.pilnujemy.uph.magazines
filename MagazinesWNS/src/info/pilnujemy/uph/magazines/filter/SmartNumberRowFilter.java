package info.pilnujemy.uph.magazines.filter;

import java.util.Arrays;

import javax.swing.RowFilter;

import info.pilnujemy.uph.magazines.Utils;

public class SmartNumberRowFilter<M, I> extends RowFilter<M, I> {
	private RowFilter<M, I> filter;

	public SmartNumberRowFilter(String keyword, int index) {
		if (keyword.length() >= 2) {
			if (keyword.startsWith("=")) {
				String value = keyword.substring(1);
				if (Utils.isNumeric(value)) {
					filter = RowFilter.numberFilter(ComparisonType.EQUAL, Integer.parseInt(value), index);
				}
			} else if (keyword.startsWith("<")) {
				String value = keyword.substring(1);
				if (Utils.isNumeric(value)) {
					filter = RowFilter.numberFilter(ComparisonType.BEFORE, Integer.parseInt(value), index);
				}
			} else if (keyword.startsWith(">")) {
				String value = keyword.substring(1);
				if (Utils.isNumeric(value)) {
					filter = RowFilter.numberFilter(ComparisonType.AFTER, Integer.parseInt(value), index);
				}
			} else if (keyword.startsWith("!")) {
				String value = keyword.substring(1);
				if (Utils.isNumeric(value)) {
					filter = RowFilter.numberFilter(ComparisonType.NOT_EQUAL, Integer.parseInt(value), index);
				}
			}
		}
		if (Utils.isNumeric(keyword)) {
			filter = RowFilter.numberFilter(ComparisonType.EQUAL, Integer.parseInt(keyword), index);
		}

		System.out.println(filter);
	}

	@Override
	public boolean include(javax.swing.RowFilter.Entry<? extends M, ? extends I> entry) {
		if (filter != null)
			return filter.include(entry);
		return false;
	}

}
