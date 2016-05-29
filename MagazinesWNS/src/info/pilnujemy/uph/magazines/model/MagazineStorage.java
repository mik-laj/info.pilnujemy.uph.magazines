package info.pilnujemy.uph.magazines.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MagazineStorage {

	private Path path;
	private List<Magazine> mElements = new ArrayList<>();
	private String COLUMN_SEPERATOR = "@@###@@";

	public MagazineStorage() {
		path = Paths.get("data.txt").toAbsolutePath();
//		mElements.addAll(FakeData.LIST_OF_MAGAZINES);
//		saveStorage();
		parseFile();
	}

	private void parseFile() {
		try {
			List<Magazine> items = Files.lines(path)
					.map(t -> t.split(COLUMN_SEPERATOR))
					.map(t -> new Magazine(t[0], Integer.parseInt(t[1]), Integer.parseInt(t[2])))
					.collect(Collectors.toList());
			mElements.clear();
			mElements.addAll(items);
		} catch (NumberFormatException | IOException e) {
			throw new RuntimeException("Cann't read storage");
		}
	}

	public void saveStorage() {
		System.out.println("saveStorage");
		try {
			List<String> lines = mElements.stream()
					.map(t -> t.getTitle() + COLUMN_SEPERATOR + t.getNo() + COLUMN_SEPERATOR + t.getYear())
					.collect(Collectors.toList());
			Files.write(path, lines, StandardOpenOption.TRUNCATE_EXISTING);
			mElements.stream().forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean add(Magazine e) {
		boolean r = mElements.add(e);
		if (r) {
			saveStorage();
		}
		return r;
	}

	public boolean remove(Magazine o) {
		boolean r = mElements.remove(o);
		if (r) {
			saveStorage();
		}
		return r;
	}

	public boolean addAll(Collection<? extends Magazine> c) {
		boolean r = mElements.addAll(c);
		if (r) {
			saveStorage();
		}
		return r;
	}

	public boolean removeAll(Collection<? extends Magazine> c) {
		for(Magazine m:c){
			this.remove(m);
		}
		return true;
	}

	public void clear() {
		mElements.clear();
		saveStorage();
	}

	public void setElements(List<Magazine> c) {
		mElements.clear();
		mElements.addAll(c);
		saveStorage();
	}

	public List<Magazine> getAll() {
		return Collections.unmodifiableList(mElements);
	}

	public String[] getAllUniquesTitles() {
		return mElements.stream()
				.map(t -> t.getTitle())
				.distinct()
				.sorted()
				.toArray(s -> new String[s]);
	}
}
