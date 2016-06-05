package info.pilnujemy.uph.magazines;

import java.awt.BorderLayout;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import info.pilnujemy.uph.magazines.model.Magazine;
import info.pilnujemy.uph.magazines.stats.CountByTitle;

/**
 * Okno z pozwalajaca statystyki
 * 
 * @author andrzej
 *
 */
public class StatsFrame extends JFrame {
	/**
	 * Interfejs pozwalajaćy określić strategię na podstawię
	 */
	public interface StatsStrategy {
		/**
		 * Pobiera wszystkie wiersze
		 * 
		 * @return zwraca tablice wierszy z komórkami.
		 */
		public Object[][] getRows();

		/**
		 * Zwraca tablice klas, które określajaca typ komórek
		 * 
		 * @return tablica zawierająca informacje o typach komórek
		 */
		public Class<?>[] getColumnTypes();

		/**
		 * Zwraca nazwy kolumn
		 * 
		 * @return tablica nazw kolumn
		 */
		public String[] getColumnNames();

		/**
		 * Zwraca nazwa okna
		 * 
		 * @return nazwa okna
		 */
		public String getName();
	}

	private JTable table;

	/**
	 * Tworzy okno z dana strategia pozwlaajaća pobrać elementy
	 * 
	 * @param strategy
	 *            strategia wyświetlania
	 */
	public StatsFrame(StatsStrategy strategy) {
		super();

		setBounds(100, 100, 650, 600);
		setTitle(strategy.getName());

		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setAutoCreateRowSorter(true);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		scrollPane.setViewportView(table);

		DefaultTableModel dataModel = new DefaultTableModel(strategy.getRows(), strategy.getColumnNames()) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return strategy.getColumnTypes()[columnIndex];
			}
		};
		table.setModel(dataModel);
	}

	/**
	 * Uruchamia aplikacje
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() ->
		{
			StatsFrame frame = new StatsFrame(
				new CountByTitle(
					Arrays.asList(
						new Magazine("Kot dachowiec", 1, 0),
						new Magazine("Kot", 1, 0), 
						new Magazine("Kot dachowiec", 1, 0), 
						new Magazine("Kot dachowiec", 1, 0),
						new Magazine("Kot", 1, 0)
					)
				)
			);
			frame.setVisible(true);
		});
	}

}
