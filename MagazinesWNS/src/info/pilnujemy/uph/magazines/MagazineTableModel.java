package info.pilnujemy.uph.magazines;

import info.pilnujemy.uph.magazines.model.Magazine;

public class MagazineTableModel extends CheckableListTableModel<Magazine> {

	public static final int COLUMN_INDEX_TITLE = 1;
	public static final int COLUMN_INDEX_NO = 2;
	public static final int COLUMN_INDEX_YEAR = 3;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1467371590011796518L;

	@Override
	public int getColumnCount() {
		return super.getColumnCount() + 1 // title
				+ 1 // year
				+ 1; // no
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case COLUMN_INDEX_TITLE:
			return this.get(rowIndex)
					.getTitle();
		case COLUMN_INDEX_NO:
			return this.get(rowIndex)
					.getNo();
		case COLUMN_INDEX_YEAR:
			return this.get(rowIndex)
					.getYear();
		}
		return super.getValueAt(rowIndex, columnIndex);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case COLUMN_INDEX_TITLE:
			return String.class;
		case COLUMN_INDEX_NO:
			return Integer.class;
		case COLUMN_INDEX_YEAR:
			return Integer.class;
		}
		return super.getColumnClass(columnIndex);
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
		case COLUMN_INDEX_TITLE:
			return "Title";
		case COLUMN_INDEX_NO:
			return "No";
		case COLUMN_INDEX_YEAR:
			return "Year";
		}
		return super.getColumnName(columnIndex);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case COLUMN_INDEX_TITLE:
			this.get(rowIndex)
					.setTitle((String) aValue);
			this.fireTableDataChanged();
			return;
		case COLUMN_INDEX_NO:
			this.get(rowIndex)
					.setNo((Integer) aValue);
			this.fireTableDataChanged();
			return;
		case COLUMN_INDEX_YEAR:
			this.get(rowIndex)
					.setYear((Integer) aValue);
			this.fireTableDataChanged();
			return;
		}
		super.setValueAt(aValue, rowIndex, columnIndex);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == COLUMN_INDEX_NO || columnIndex == COLUMN_INDEX_YEAR || columnIndex == COLUMN_INDEX_TITLE) {
			return true;
		}
		return super.isCellEditable(rowIndex, columnIndex);
	}
}
