package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public final class FormDialog {
	private FormDialog() {
	}

	public static String[] show(JFrame owner, String title, String[] fieldNames) {
		return show(owner, title, fieldNames, null);
	}

	public static String[] show(JFrame owner, String title, String[] fieldNames, String[][] dropdownOptions) {
		JDialog dialog = new JDialog(owner, title, true);
		dialog.setLayout(new BorderLayout());
		dialog.setSize(360, 140 + fieldNames.length * 40);
		dialog.setLocationRelativeTo(owner);

		JPanel form = new JPanel(new GridLayout(fieldNames.length, 2, 10, 10));
		form.setBorder(new EmptyBorder(20, 20, 20, 20));
		JComponent[] fields = new JComponent[fieldNames.length];
		String[] values = new String[fieldNames.length];
		for (int i = 0; i < fieldNames.length; i++) {
			String fieldName = fieldNames[i];
			form.add(new JLabel(fieldName));
			if (dropdownOptions != null && i < dropdownOptions.length && dropdownOptions[i] != null) {
				JComboBox<String> comboBox = new JComboBox<>(dropdownOptions[i]);
				fields[i] = comboBox;
				form.add(comboBox);
			} else {
				JTextField textField = new JTextField();
				fields[i] = textField;
				form.add(textField);
			}
		}

		AppButton closeButton = AppButton.primary("확인");
		closeButton.addActionListener(event -> {
			for (int i = 0; i < fields.length; i++) {
				if (fields[i] instanceof JTextField) {
					values[i] = ((JTextField) fields[i]).getText();
				} else if (fields[i] instanceof JComboBox) {
					Object selectedItem = ((JComboBox<?>) fields[i]).getSelectedItem();
					values[i] = selectedItem == null ? null : selectedItem.toString();
				}
			}
			dialog.dispose();
		});
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(closeButton);

		dialog.add(form, BorderLayout.CENTER);
		dialog.add(buttonPanel, BorderLayout.SOUTH);
		dialog.setVisible(true);
		return values;
	}
}
