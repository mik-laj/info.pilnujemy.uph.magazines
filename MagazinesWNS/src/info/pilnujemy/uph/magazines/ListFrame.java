package info.pilnujemy.uph.magazines;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import info.pilnujemy.uph.magazines.StatsFrame.StatsStrategy;
import info.pilnujemy.uph.magazines.filter.MagazineRowFilter;
import info.pilnujemy.uph.magazines.model.Magazine;
import info.pilnujemy.uph.magazines.model.MagazineStorage;
import info.pilnujemy.uph.magazines.stats.CountByTitle;
import info.pilnujemy.uph.magazines.stats.CountByYear;
import info.pilnujemy.uph.magazines.view.PlaceholderTextField;

/**
 * Główne okno aplikacji zawierające listę czasopism
 * 
 * @author andrzej
 *
 */
public class ListFrame extends JFrame implements DocumentListener {

	private PlaceholderTextField tfSearch;
	private JTable tTable;
	private MagazineTableModel dataModel;
	private TableRowSorter<MagazineTableModel> sorter;
	private MagazineStorage storage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListFrame frame = new ListFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ListFrame() {
		storage = new MagazineStorage();

		createUi();

		sorter = new TableRowSorter<>(dataModel);
		tTable.setRowSorter(sorter);

		dataModel.addAll(storage.getAll());
		dataModel.addTableModelListener(this::onTableChanged);

		tfSearch.getDocument()
				.addDocumentListener(this);
	}

	/**
	 * Tworzy interfejs graficzny okna
	 */
	private void createUi() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 600);
		setLocationRelativeTo(null);

		// Top Panel
		JPanel pTop = new JPanel();
		pTop.setBorder(new EmptyBorder(10, 10, 10, 10));
		pTop.setLayout(new BorderLayout(0, 0));
		getContentPane().add(pTop, BorderLayout.NORTH);

		// Search TextField
		// tfSearch = new JTextField();
		tfSearch = new PlaceholderTextField();

		tfSearch.setMargin(new Insets(5, 5, 5, 5));
		tfSearch.setColumns(10);
		tfSearch.setPlaceholder("Kryterium wyszukiwania np. tytul:\"*kotek*\" no:>10 year:<2000");
		pTop.add(tfSearch);

		// Main Panel
		JPanel pMain = new JPanel();
		pMain.setLayout(new BorderLayout(0, 0));
		getContentPane().add(pMain, BorderLayout.CENTER);

		// Button's panel
		JPanel pButtons = new JPanel();
		pButtons.setBorder(new EmptyBorder(10, 10, 10, 10));
		pMain.add(pButtons, BorderLayout.EAST);
		pButtons.setLayout(new BorderLayout(0, 0));

		JPanel pButtonsInner = new JPanel();
		pButtons.add(pButtonsInner, BorderLayout.NORTH);
		pButtonsInner.setLayout(new GridLayout(4, 1, 0, 20));

		// Button "Add"
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(this::onButtonAdd);
		pButtonsInner.add(btnAdd);
		btnAdd.setAlignmentY(Component.TOP_ALIGNMENT);

		// Button "Delete"
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(this::onButtonDelete);
		pButtonsInner.add(btnDelete);
		btnDelete.setAlignmentY(Component.BOTTOM_ALIGNMENT);

		// Button "Stats per title"
		JButton btnStatsPerTitle = new JButton("Stats per title");
		btnStatsPerTitle.addActionListener(this::onButtonStatsPerTitle);
		pButtonsInner.add(btnStatsPerTitle);
		btnStatsPerTitle.setAlignmentY(Component.BOTTOM_ALIGNMENT);

		// Button "Stats per title"
		JButton btnStatsPerYear = new JButton("Stats per year");
		btnStatsPerYear.addActionListener(this::onButtonStatsPerYear);
		pButtonsInner.add(btnStatsPerYear);
		btnStatsPerYear.setAlignmentY(Component.BOTTOM_ALIGNMENT);

		// Table Panel
		JPanel pTable = new JPanel();
		pTable.setBorder(new EmptyBorder(10, 10, 10, 10));
		pTable.setLayout(new BoxLayout(pTable, BoxLayout.X_AXIS));
		pMain.add(pTable, BorderLayout.CENTER);

		// Table
		tTable = new JTable();
		dataModel = new MagazineTableModel();
		tTable.setModel(dataModel);
		tTable.setFillsViewportHeight(true);
		tTable.setAutoCreateRowSorter(true);
		tTable.setCellSelectionEnabled(false);

		TableColumnModel columnModel = tTable.getColumnModel();
		columnModel.getColumn(0)
				.setPreferredWidth(25);
		columnModel.getColumn(0)
				.setMaxWidth(25);
		columnModel.getColumn(1)
				.setPreferredWidth(500);

		JScrollPane spTable = new JScrollPane(tTable);
		pTable.add(spTable);
	}

	/**
	 * Metoda wykonywana podczas dokonywana edycji w polu tekstowym `tfSearch`
	 */
	@Override
	public void insertUpdate(DocumentEvent e) {
		onSearchFieldValueChanged();
	}

	/**
	 * Metoda wykonywana podczas dokonywana edycji w polu tekstowym `tfSearch`
	 */
	@Override
	public void removeUpdate(DocumentEvent e) {
		onSearchFieldValueChanged();
	}

	/**
	 * Metoda wykonywana podczas dokonywana edycji w polu tekstowym `tfSearch`
	 */

	@Override
	public void changedUpdate(DocumentEvent e) {
		onSearchFieldValueChanged();
	}

	/**
	 * Metoda wykonywana podczas dokonywana edycji w polu tekstowym `tfSearch`
	 */
	void onSearchFieldValueChanged() {
		String text = tfSearch.getText();
		if (!text.isEmpty()) {
			sorter.setRowFilter(new MagazineRowFilter(text));
		} else {
			sorter.setRowFilter(null);
		}

	}

	/**
	 * Metoda wykonywana podczas dokonywana zmian w tabeli np, dodanie wiersza,
	 * zmiany zawartości komórki
	 */
	void onTableChanged(TableModelEvent e) {
		System.out.println("onTableChanged");
		storage.setElements(dataModel.getElements());
	}

	/**
	 * Słuchasz naciśniecia przycisku "Add"
	 * 
	 * @param ev
	 */
	void onButtonAdd(ActionEvent ev) {
		Magazine m = new Magazine("", 1, 2000);
		String[] suggestions = storage.getAllUniquesTitles();
		CreateEditFrame frame = new CreateEditFrame(m, suggestions);
		frame.setSaveListener(t ->
		{
			dataModel.add(t);
			System.out.println("Save");
			frame.dispose();
		});
		frame.setVisible(true);
	}

	/**
	 * Słuchasz naciśniecia przycisku "Delete"
	 * 
	 * @param ev
	 */
	void onButtonDelete(ActionEvent ev) {
		List<Magazine> selected = dataModel.getSelectedElements();
		System.out.println(selected);
		dataModel.removeAll(selected);
	}

	/**
	 * Słuchasz naciśniecia przycisku "Stats per Title"
	 * 
	 * @param ev
	 */
	void onButtonStatsPerTitle(ActionEvent ev) {
		List<Magazine> all = storage.getAll();
		StatsStrategy strategy = new CountByTitle(all);
		new StatsFrame(strategy).setVisible(true);
	}

	/**
	 * Słuchasz naciśniecia przycisku "Stats per Year"
	 * 
	 * @param ev
	 */
	void onButtonStatsPerYear(ActionEvent ev) {
		List<Magazine> all = storage.getAll();
		StatsStrategy strategy = new CountByYear(all);
		new StatsFrame(strategy).setVisible(true);
	}

}
