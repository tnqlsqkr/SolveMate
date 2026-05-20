package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class SolveMateFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private static final String CARD_MEMBER = "member";
	private static final String CARD_PROBLEM = "problem";
	private static final String CARD_RANK = "rank";
	private static final String CARD_SUBMISSION = "submission";

	private final CardLayout cardLayout = new CardLayout();
	private final JPanel cardPanel = new JPanel(cardLayout);

	public static void showFrame() {
		SwingUtilities.invokeLater(() -> {
			setLookAndFeel();
			SolveMateFrame frame = new SolveMateFrame();
			frame.setVisible(true);
		});
	}

	private static void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) {
		}
	}

	public SolveMateFrame() {
		setTitle("SolveMate");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(1080, 720));
		setLocationRelativeTo(null);

		JPanel root = new JPanel(new BorderLayout());
		root.setBackground(Color.WHITE);
		root.add(createHeader(), BorderLayout.NORTH);
		root.add(createContent(), BorderLayout.CENTER);
		setContentPane(root);

		pack();
	}

	private JPanel createHeader() {
		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(Color.WHITE);
		header.setBorder(new EmptyBorder(24, 32, 16, 32));

		JLabel title = new JLabel("SolveMate 관리자 화면");
		title.setFont(new Font("Malgun Gothic", Font.BOLD, 28));
		title.setForeground(new Color(28, 28, 28));

		JLabel subtitle = new JLabel("domain 연동 전 Swing UI");
		subtitle.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));
		subtitle.setForeground(new Color(116, 116, 116));

		JPanel textBox = new JPanel();
		textBox.setOpaque(false);
		textBox.setLayout(new BoxLayout(textBox, BoxLayout.Y_AXIS));
		textBox.add(title);
		textBox.add(Box.createVerticalStrut(8));
		textBox.add(subtitle);

		header.add(textBox, BorderLayout.WEST);
		return header;
	}

	private JPanel createContent() {
		JPanel content = new JPanel(new BorderLayout(20, 0));
		content.setBackground(Color.WHITE);
		content.setBorder(new EmptyBorder(0, 32, 32, 32));

		cardPanel.setBackground(Color.WHITE);
		cardPanel.add(new MemberPanel(), CARD_MEMBER);
		cardPanel.add(new ProblemPanel(), CARD_PROBLEM);
		cardPanel.add(new RankPanel(), CARD_RANK);
		cardPanel.add(new SubmissionPanel(), CARD_SUBMISSION);

		content.add(new NavigationPanel(cardLayout, cardPanel), BorderLayout.WEST);
		content.add(cardPanel, BorderLayout.CENTER);
		return content;
	}
}
