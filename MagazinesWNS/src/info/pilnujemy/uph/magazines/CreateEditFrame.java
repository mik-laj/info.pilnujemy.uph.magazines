package info.pilnujemy.uph.magazines;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;

import info.pilnujemy.uph.magazines.model.FakeData;
import info.pilnujemy.uph.magazines.model.Magazine;

public class CreateEditFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3561794417438814934L;

	public interface onSaveClickListener {
		void onSaveClicked(Magazine m);
	}

	private JComboBox<String> cbTitle;
	private JTextField tfYear;
	private JTextField tfNo;
	private JButton btnAdd;
	private Magazine magazine;
	private onSaveClickListener listener;
	private String[] suggestion;

	public CreateEditFrame() {
		this(new Magazine("", 1, 2000), new String[] {});
	}

	/**
	 * Create the frame.
	 */
	public CreateEditFrame(Magazine magazine, String[] suggestion) {
		this.magazine = magazine;
		this.suggestion = suggestion;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel pFields = new JPanel();
		pFields.setBorder(null);
		pFields.setLayout(new GridLayout(3, 2, 0, 10));
		contentPane.add(pFields, BorderLayout.NORTH);

		// Title
		pFields.add(new JLabel("Title"));
		cbTitle = new JComboBox<>(suggestion);
		cbTitle.setEditable(true);
		cbTitle.getEditor()
				.setItem(magazine.getTitle());
		// cbTitle.setSelectedIndex(-1);
		pFields.add(cbTitle);

		// Used by JFormattedTextField in fields "No" and "Year"
		NumberFormatter numberFormatter = new NumberFormatter(new DecimalFormat("#########"));
		numberFormatter.setValueClass(Integer.class);
		numberFormatter.setAllowsInvalid(false);

		// No
		pFields.add(new JLabel("No"));
		tfNo = new JFormattedTextField(numberFormatter);
		tfNo.setColumns(10);
		tfNo.setText(String.valueOf(magazine.getNo()));
		pFields.add(tfNo);

		// Year
		pFields.add(new JLabel("Year"));
		tfYear = new JFormattedTextField(numberFormatter);
		tfYear.setColumns(10);
		tfYear.setText(String.valueOf(magazine.getYear()));
		pFields.add(tfYear);

		JPanel pButtons = new JPanel();
		contentPane.add(pButtons);
		pButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btnAdd = new JButton("Save");
		btnAdd.addActionListener(this::onSaveButton);
		pButtons.add(btnAdd);
		pack();
		setLocationRelativeTo(null);
	}

	private void onSaveButton(ActionEvent e) {
		if (!validateForm()) {
			return;
		}
		String title = (String) cbTitle.getEditor()
				.getItem();
		int year = Integer.parseInt(tfYear.getText());
		int no = Integer.parseInt(tfNo.getText());
		magazine.setTitle(title);
		magazine.setYear(year);
		magazine.setNo(no);
		notifyListener();
	}

	private boolean validateForm() {
		String title = (String) cbTitle.getEditor()
				.getItem();
		String year = tfYear.getText();
		String no = tfNo.getText();

		// Title validation
		if (title.trim()
				.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Field \"title\" can not be empty", "Error in form",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		// Year validation
		if (!Utils.isNumeric(year)) {
			JOptionPane.showMessageDialog(this, "Field \"Year\" must be a number between 1500 and 2500",
					"Error in form", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		int year_int = Integer.parseInt(year);
		if (year_int < 1500 || year_int > 2500) {
			JOptionPane.showMessageDialog(this, "Field \"Year\" must be a number between 1500 and 2500",
					"Error in form", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// No validation
		if (!Utils.isNumeric(no)) {
			JOptionPane.showMessageDialog(this, "Field \"No\" must be a number", "Error in form",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	private void notifyListener() {
		if (this.listener != null) {
			listener.onSaveClicked(magazine);
		}
	}

	public void setSaveListener(onSaveClickListener listener) {
		this.listener = listener;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateEditFrame frame = new CreateEditFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
