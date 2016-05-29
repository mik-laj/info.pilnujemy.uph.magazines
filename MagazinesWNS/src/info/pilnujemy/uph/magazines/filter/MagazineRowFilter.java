package info.pilnujemy.uph.magazines.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.RowFilter.ComparisonType;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;

import info.pilnujemy.uph.magazines.MagazineTableModel;
import info.pilnujemy.uph.magazines.model.Magazine;

public class MagazineRowFilter extends RowFilter<MagazineTableModel, Integer> {

	private final static Pattern REG_EXP_PATTERN = Pattern.compile("([a-z]+):(\".+?\"|[^ ]+)",
			Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
	private RowFilter<MagazineTableModel, Integer> filter;

	public MagazineRowFilter(String keyword) {
		List<RowFilter<MagazineTableModel, Integer>> filters = new ArrayList<>();

		Matcher matcher = REG_EXP_PATTERN.matcher(keyword);

		while (matcher.find()) {
			String part = matcher.group(0);
			String name = matcher.group(1);
			String value = matcher.group(2);
			if (value.length() >= 2 && value.startsWith("\"") && value.endsWith("\"")) {
				value = value.substring(1, value.length() - 1);
			}
			if (value.isEmpty())
				continue;
			if ("title".equals(name)) {
				filters.add(new SmartTextRowFilter<>(value, MagazineTableModel.COLUMN_INDEX_TITLE));
			} else if ("no".equals(name)) {
				filters.add(new SmartNumberRowFilter<>(value, MagazineTableModel.COLUMN_INDEX_NO));
			} else if ("year".equals(name)) {
				filters.add(new SmartNumberRowFilter<>(value, MagazineTableModel.COLUMN_INDEX_YEAR));
			}
		}
		String clearText = REG_EXP_PATTERN.matcher(keyword).replaceAll("").trim();
		
		filters.add(RowFilter.regexFilter(Pattern.quote(clearText)));
		filter = RowFilter.andFilter(filters);
//		filter =  RowFilter.numberFilter(ComparisonType.BEFORE, 2000, 3);
	}

	@Override
	public boolean include(javax.swing.RowFilter.Entry<? extends MagazineTableModel, ? extends Integer> entry) {
		return filter.include(entry);
	}
}
