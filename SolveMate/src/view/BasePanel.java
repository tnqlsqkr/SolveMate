package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public abstract class BasePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	protected BasePanel(String title, String subtitle) {
		setLayout(new BorderLayout(0, 18));
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(new Color(220, 220, 220), 1, true),
				new EmptyBorder(24, 28, 28, 28)));
		add(createTitlePanel(title, subtitle), BorderLayout.NORTH);
	}

	protected JPanel createToolbar(Component... components) {
		JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		toolbar.setBackground(Color.WHITE);
		for (Component component : components) {
			toolbar.add(component);
		}
		return toolbar;
	}

	protected JComboBox<String> createComboBox() {
		JComboBox<String> comboBox = new JComboBox<>();
		comboBox.setPreferredSize(new Dimension(160, 36));
		return comboBox;
	}

	protected JScrollPane createTable(String[] columns) {
		DefaultTableModel model = new DefaultTableModel(columns, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JTable table = new JTable(model);
		table.setRowHeight(36);
		table.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowVerticalLines(false);
		table.setGridColor(new Color(232, 232, 232));
		table.setSelectionBackground(new Color(226, 240, 255));

		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
		header.setBackground(new Color(248, 249, 250));
		header.setForeground(new Color(55, 55, 55));
		header.setPreferredSize(new Dimension(0, 38));

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(new LineBorder(new Color(225, 225, 225), 1, true));
		scrollPane.getViewport().setBackground(Color.WHITE);
		return scrollPane;
	}

	private JPanel createTitlePanel(String title, String subtitle) {
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));

		JLabel titleLabel = new JLabel(title);
		titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 20));
		titleLabel.setForeground(new Color(20, 20, 20));

		JLabel subtitleLabel = new JLabel(subtitle);
		subtitleLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));
		subtitleLabel.setForeground(new Color(76, 76, 76));
		subtitleLabel.setBorder(new EmptyBorder(8, 0, 0, 0));

		panel.add(titleLabel);
		panel.add(subtitleLabel);
		return panel;
	}
}
