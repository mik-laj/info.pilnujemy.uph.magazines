package info.pilnujemy.uph.magazines;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
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

	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public int getRowCount() {
		return mELements.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == COLUMN_INDEX_CB) {
			return mELements.get(rowIndex).is_selected;
		}
		throw new IllegalStateException();
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == COLUMN_INDEX_CB)
			return Boolean.class;
		return super.getColumnClass(columnIndex);
	}

	@Override
	public String getColumnName(int columnIndex) {
		if (columnIndex == COLUMN_INDEX_CB) {
			return "[ ]";
		}
		return super.getColumnName(columnIndex);
	}

	public int size() {
		return mELements.size();
	}

	public void add(T obj) {
		mELements.add(new CheckboxItemModel<T>(obj));
		fireTableRowsInserted(mELements.size() - 1, mELements.size() - 1);
	}

	public void remove(T o) {
		int index = this.indexOf(o);
		mELements.remove(index);
		fireTableRowsDeleted(index, index);
	}

	public void addAll(Collection<? extends T> c) {
		for (T e : c) {
			add(e);
		}
	}

	public void remove(int index) {
		mELements.remove(index);
		fireTableRowsDeleted(index, index);
	}

	public void removeAll(Collection<? extends T> c) {
		for(T i:c){
			this.remove(i);
		}
	}

	public T get(int index) {
		return mELements.get(index).obj;
	}

	public int indexOf(T search) {
		for (int i = 0; i < mELements.size(); i++) {
			T current = mELements.get(i).obj;
			if (search == current)
				return i;
		}
		return -1;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (columnIndex == COLUMN_INDEX_CB) {
			this.fireTableDataChanged();
			mELements.get(rowIndex).is_selected = (boolean) aValue;
		}
		super.setValueAt(aValue, rowIndex, columnIndex);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return true;
		}
		return super.isCellEditable(rowIndex, columnIndex);
	}

	public List<T> getElements() {
		return mELements.stream()
				.map(t -> t.obj)
				.collect(Collectors.toList());
	}

	public List<T> getSelectedElements() {
		return mELements.stream()
				.filter(t -> t.is_selected)
				.map(t -> t.obj)
				.collect(Collectors.toList());
	}

	public List<T> getNotSelectedElements() {
		return mELements.stream()
				.filter(t -> !t.is_selected)
				.map(t -> t.obj)
				.collect(Collectors.toList());
	}
}
