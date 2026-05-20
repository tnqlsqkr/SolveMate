package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

public class AppButton extends JButton {
	private static final long serialVersionUID = 1L;

	private final Color backgroundColor;
	private final Color borderColor;

	private AppButton(String text, Color backgroundColor, Color borderColor) {
		super(text);
		this.backgroundColor = backgroundColor;
		this.borderColor = borderColor;

		setFont(new Font("Malgun Gothic", Font.BOLD, 13));
		setForeground(Color.WHITE);
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
		setFocusPainted(false);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setPreferredSize(new Dimension(120, 38));
		setMinimumSize(new Dimension(120, 38));
		setBorder(new EmptyBorder(8, 14, 8, 14));
	}

	public static AppButton primary(String text) {
		return new AppButton(text, new Color(0, 97, 224), new Color(0, 69, 170));
	}

	public static AppButton secondary(String text) {
		return new AppButton(text, new Color(76, 86, 96), new Color(56, 64, 72));
	}

	public static AppButton danger(String text) {
		return new AppButton(text, new Color(192, 38, 38), new Color(145, 28, 28));
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(backgroundColor);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(borderColor);
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		super.paintComponent(g);
	}
}
