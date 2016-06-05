package info.pilnujemy.uph.magazines;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.table.AbstractTableModel;

/**
 * Moel implementowany na liście, który w pierwszej komórce zawiera pole
 * zaznaczania
 * 
 * @author andrzej
 *
 * @param <T>
 *            klasa, której dotyczy moel
 */
public abstract class CheckableListTableModel<T> extends AbstractTableModel {
	public static final int COLUMN_INDEX_CB = 0;

	private static class CheckboxItemModel<T> {
		public T obj;
		public boolean is_selected;

		public CheckboxItemModel(T obj) {
			this.obj = obj;
		}

		@SuppressWarnings("unused")
		public CheckboxItemModel(T obj, boolean is_selected) {
			super();
			this.obj = obj;
			this.is_selected = is_selected;
		}

	}

	private List<CheckboxItemModel<T>> mELements = new ArrayList<>();

	/**
	 * Zwraca liczbę aktualnych kolumn
	 */
	@Override
	public int getColumnCount() {
		return 1;
	}

	/**
	 * Zwraca liczbę wierszy
	 */
	@Override
	public int getRowCount() {
		return mELements.size();
	}

	/**
	 * Zwraca wartość komórki w wierszu `rowIndex` i kolumnie `columnIndex`
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == COLUMN_INDEX_CB) {
			return mELements.get(rowIndex).is_selected;
		}
		throw new IllegalStateException();
	}

	/**
	 * Zwraca najdokładniejszą nadklasę, która opisuje wszystkie wartości w
	 * kolumnie
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == COLUMN_INDEX_CB)
			return Boolean.class;
		return super.getColumnClass(columnIndex);
	}

	/**
	 * Zwraca nazwę kolumny
	 */
	@Override
	public String getColumnName(int columnIndex) {
		if (columnIndex == COLUMN_INDEX_CB) {
			return "[ ]";
		}
		return super.getColumnName(columnIndex);
	}

	/**
	 * Zwraca ilośc elementów w modelu
	 * 
	 * @return ilośc elementów
	 */
	public int size() {
		return mELements.size();
	}

	/**
	 * Dodaje element
	 * 
	 * @param obj
	 *            element do dodania
	 */
	public void add(T obj) {
		mELements.add(new CheckboxItemModel<T>(obj));
		fireTableRowsInserted(mELements.size() - 1, mELements.size() - 1);
	}

	/**
	 * Kasuje element
	 * 
	 * @param o
	 *            element do skasowania
	 */
	public void remove(T o) {
		int index = this.indexOf(o);
		mELements.remove(index);
		fireTableRowsDeleted(index, index);
	}

	/**
	 * Kasuje liste elementów
	 * 
	 * @param c
	 *            lista elementów do skasowania
	 */
	public void addAll(Collection<? extends T> c) {
		for (T e : c) {
			add(e);
		}
	}

	/**
	 * Kasuje element na pozycji
	 */
	public void remove(int index) {
		mELements.remove(index);
		fireTableRowsDeleted(index, index);
	}

	/**
	 * Kasuje wszystkie element z listy
	 * 
	 * @param c
	 */
	public void removeAll(Collection<? extends T> c) {
		for (T i : c) {
			this.remove(i);
		}
	}

	/**
	 * Zwraca informacje o wierszu `index`
	 * 
	 * @param index
	 * @return
	 */
	public T get(int index) {
		return mELements.get(index).obj;
	}

	/**
	 * Zwraca numer wiersza elementu w tabeli.
	 * 
	 * @param search
	 *            element szukany
	 * @return jeśli znajdzie to numer elementu. W innym wypadku -1
	 */
	public int indexOf(T search) {
		for (int i = 0; i < mELements.size(); i++) {
			T current = mELements.get(i).obj;
			if (search == current)
				return i;
		}
		return -1;
	}

	/**
	 * Ustawia wartosć w kolumnie
	 */
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (columnIndex == COLUMN_INDEX_CB) {
			this.fireTableDataChanged();
			mELements.get(rowIndex).is_selected = (boolean) aValue;
		}
		super.setValueAt(aValue, rowIndex, columnIndex);
	}

	/**
	 * Określa, czy można edytować komórkę
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return true;
		}
		return super.isCellEditable(rowIndex, columnIndex);
	}

	/**
	 * Zwraca listę wszystkich elementów
	 * 
	 * @return lista elementów
	 */
	public List<T> getElements() {
		return mELements.stream()
				.map(t -> t.obj)
				.collect(Collectors.toList());
	}

	/**
	 * Zwraca listę zaznaczonych elementów
	 * 
	 * @return lista elementów
	 */
	public List<T> getSelectedElements() {
		return mELements.stream()
				.filter(t -> t.is_selected)
				.map(t -> t.obj)
				.collect(Collectors.toList());
	}

	/**
	 * Zwraca listę NIE zaznaczonych elementów
	 * 
	 * @return lista elementów
	 */
	public List<T> getNotSelectedElements() {
		return mELements.stream()
				.filter(t -> !t.is_selected)
				.map(t -> t.obj)
				.collect(Collectors.toList());
	}
}
