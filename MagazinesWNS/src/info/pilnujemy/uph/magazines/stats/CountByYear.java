package info.pilnujemy.uph.magazines.stats;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import info.pilnujemy.uph.magazines.StatsFrame.StatsStrategy;
import info.pilnujemy.uph.magazines.model.Magazine;

public class CountByYear implements StatsStrategy {
	private Object[][] elements;

	public CountByYear(List<Magazine> all) {
		Map<Integer, Long> stats_map = all.stream()
				.collect(Collectors.groupingBy(Magazine::getYear, Collectors.counting()));

		elements = stats_map.entrySet()
				.stream()
				.sorted(Comparator.comparing(Entry::getValue))
				.map(t ->
				{
					return new Object[] { t.getKey(), t.getValue() };
				})
				.toArray(size -> new Object[size][]);

		System.out.println(elements);
	}

	@Override
	public Object[][] getRows() {
		return elements;
	}

	@Override
	public Class<?>[] getColumnTypes() {
		return new Class<?>[] { String.class, Long.class };
	}

	@Override
	public String[] getColumnNames() {
		return new String[] { "Rok", "Ilość" };
	}

	@Override
	public String getName() {
		return "Statystyki ilości numerów czasopism";
	}

}