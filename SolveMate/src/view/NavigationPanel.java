package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class NavigationPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JButton selectedButton;

	public NavigationPanel(CardLayout cardLayout, JPanel cardPanel) {
		setPreferredSize(new Dimension(210, 0));
		setBackground(new Color(248, 249, 250));
		setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(new Color(224, 224, 224), 1, true),
				new EmptyBorder(16, 14, 16, 14)));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JLabel label = new JLabel("화면 구성");
		label.setFont(new Font("Malgun Gothic", Font.BOLD, 15));
		label.setForeground(new Color(90, 90, 90));
		label.setAlignmentX(Component.LEFT_ALIGNMENT);
		add(label);
		add(Box.createVerticalStrut(14));

		add(createNavButton("회원 관리", "member", true, cardLayout, cardPanel));
		add(Box.createVerticalStrut(8));
		add(createNavButton("문제 관리", "problem", false, cardLayout, cardPanel));
		add(Box.createVerticalStrut(8));
		add(createNavButton("랭킹", "rank", false, cardLayout, cardPanel));
		add(Box.createVerticalStrut(8));
		add(createNavButton("풀이 등록", "submission", false, cardLayout, cardPanel));
		add(Box.createVerticalGlue());
	}

	private JButton createNavButton(String text, String cardName, boolean selected, CardLayout cardLayout, JPanel cardPanel) {
		JButton button = new JButton(text);
		button.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
		button.setHorizontalAlignment(SwingConstants.LEFT);
		button.setFocusPainted(false);
		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
		button.addActionListener(event -> {
			cardLayout.show(cardPanel, cardName);
			selectButton(button);
		});

		if (selected) {
			selectButton(button);
		} else {
			paintButton(button, false);
		}
		return button;
	}

	private void selectButton(JButton button) {
		if (selectedButton != null) {
			paintButton(selectedButton, false);
		}
		selectedButton = button;
		paintButton(button, true);
	}

	private void paintButton(JButton button, boolean selected) {
		button.setOpaque(true);
		button.setContentAreaFilled(true);
		button.setBorderPainted(true);
		button.setBackground(selected ? new Color(222, 245, 237) : Color.WHITE);
		button.setForeground(selected ? new Color(19, 120, 95) : new Color(42, 42, 42));
		button.setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(selected ? new Color(0, 148, 178) : new Color(185, 185, 185), selected ? 2 : 1, false),
				new EmptyBorder(12, 14, 12, 14)));
	}
}
