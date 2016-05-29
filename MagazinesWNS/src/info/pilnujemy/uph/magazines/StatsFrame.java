package info.pilnujemy.uph.magazines;

import java.awt.BorderLayout;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import info.pilnujemy.uph.magazines.model.Magazine;
import info.pilnujemy.uph.magazines.stats.CountByTitle;

public class StatsFrame extends JFrame {
	public interface StatsStrategy {
		public Object[][] getRows();

		public Class<?>[] getColumnTypes();

		public String[] getColumnNames();

		public String getName();
	}

	private JTable table;

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
