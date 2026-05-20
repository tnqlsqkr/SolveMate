package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import domain.problem.DifficultyScoreDTO;
import domain.problem.ProblemDTO;
import domain.problem.ProblemService;
import domain.problem.ProblemServiceImple;

public class ProblemPanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	private static final String ALL_PLATFORMS = "전체";

	private ProblemService problemService;
	private DefaultTableModel tableModel;
	private JTable problemTable;
	private JComboBox<String> platformComboBox;
	private final Map<String, List<DifficultyScoreDTO>> difficultyOptions = new LinkedHashMap<>();

	public ProblemPanel() {
		super("문제 관리", "문제 목록");

		AppButton addButton = AppButton.primary("+ 문제 등록");
		AppButton deleteButton = AppButton.danger("선택 삭제");

		loadDifficultyOptions();

		addButton.addActionListener(event -> showAddProblemDialog());
		deleteButton.addActionListener(event -> deleteSelectedProblem());

		platformComboBox = createComboBox();
		loadPlatformComboBox();
		platformComboBox.addActionListener(event -> filterProblemsByPlatform());

		add(createToolbar(platformComboBox, addButton, deleteButton), BorderLayout.NORTH);
		add(createProblemTable(), BorderLayout.CENTER);
		loadProblems();
	}

	private JScrollPane createProblemTable() {
		tableModel = new DefaultTableModel(new String[] {"ID", "문제", "난이도", "플랫폼", "점수"}, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		problemTable = new JTable(tableModel);
		problemTable.setRowHeight(36);
		problemTable.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));
		problemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		problemTable.setShowVerticalLines(false);
		problemTable.setGridColor(new Color(232, 232, 232));
		problemTable.setSelectionBackground(new Color(226, 240, 255));

		TableColumnModel columnModel = problemTable.getColumnModel();
		columnModel.removeColumn(columnModel.getColumn(0));

		JTableHeader header = problemTable.getTableHeader();
		header.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
		header.setBackground(new Color(248, 249, 250));
		header.setForeground(new Color(55, 55, 55));
		header.setPreferredSize(new Dimension(0, 38));

		JScrollPane scrollPane = new JScrollPane(problemTable);
		scrollPane.setBorder(new LineBorder(new Color(225, 225, 225), 1, true));
		scrollPane.getViewport().setBackground(Color.WHITE);
		return scrollPane;
	}

	private void showAddProblemDialog() {
		if (difficultyOptions.isEmpty()) {
			loadDifficultyOptions();
		}
		if (difficultyOptions.isEmpty()) {
			JOptionPane.showMessageDialog(this, "등록 가능한 플랫폼/난이도 정보가 없습니다.");
			return;
		}

		JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
		JDialog dialog = new JDialog(owner, "문제 등록", true);
		dialog.setLayout(new BorderLayout());
		dialog.setSize(420, 300);
		dialog.setLocationRelativeTo(owner);

		JTextField titleField = new JTextField();
		JTextField urlField = new JTextField();
		JComboBox<String> dialogPlatformComboBox = new JComboBox<>(difficultyOptions.keySet().toArray(new String[0]));
		JComboBox<DifficultyScoreDTO> levelComboBox = new JComboBox<>();
		levelComboBox.setRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(
					JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (value instanceof DifficultyScoreDTO) {
					DifficultyScoreDTO difficulty = (DifficultyScoreDTO) value;
					setText(difficulty.getLevel() + " (" + difficulty.getPoint() + "점)");
				}
				return this;
			}
		});
		updateLevelComboBox(dialogPlatformComboBox, levelComboBox);
		dialogPlatformComboBox.addActionListener(event -> updateLevelComboBox(dialogPlatformComboBox, levelComboBox));

		JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
		form.setBorder(new EmptyBorder(20, 20, 20, 20));
		form.add(new JLabel("문제 제목"));
		form.add(titleField);
		form.add(new JLabel("URL"));
		form.add(urlField);
		form.add(new JLabel("플랫폼"));
		form.add(dialogPlatformComboBox);
		form.add(new JLabel("난이도"));
		form.add(levelComboBox);

		AppButton saveButton = AppButton.primary("등록");
		saveButton.addActionListener(event -> {
			String title = titleField.getText().trim();
			if (title.isEmpty()) {
				JOptionPane.showMessageDialog(dialog, "문제 제목을 입력하세요.");
				return;
			}

			DifficultyScoreDTO difficulty = (DifficultyScoreDTO) levelComboBox.getSelectedItem();
			if (difficulty == null) {
				JOptionPane.showMessageDialog(dialog, "난이도를 선택하세요.");
				return;
			}

			try {
				initProblemService();

				ProblemDTO problem = new ProblemDTO();
				problem.setTitle(title);
				problem.setUrl(urlField.getText().trim());
				problem.setDsId(difficulty.getDsId());

				int result = problemService.insertProblem(problem);
				if (result > 0) {
					dialog.dispose();
					filterProblemsByPlatform();
				} else {
					JOptionPane.showMessageDialog(dialog, "문제를 등록하지 못했습니다.");
				}
			} catch (RuntimeException | ExceptionInInitializerError e) {
				JOptionPane.showMessageDialog(dialog, "문제 등록 중 오류가 발생했습니다.\n" + e.getMessage());
			}
		});

		AppButton cancelButton = AppButton.danger("취소");
		cancelButton.addActionListener(event -> dialog.dispose());

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(cancelButton);
		buttonPanel.add(saveButton);

		dialog.add(form, BorderLayout.CENTER);
		dialog.add(buttonPanel, BorderLayout.SOUTH);
		dialog.setVisible(true);
	}

	private void updateLevelComboBox(
			JComboBox<String> sourcePlatformComboBox,
			JComboBox<DifficultyScoreDTO> levelComboBox) {
		levelComboBox.removeAllItems();
		String platform = (String) sourcePlatformComboBox.getSelectedItem();
		List<DifficultyScoreDTO> options = difficultyOptions.get(platform);
		if (options == null) {
			return;
		}

		for (DifficultyScoreDTO option : options) {
			levelComboBox.addItem(option);
		}
	}

	private void deleteSelectedProblem() {
		try {
			if (problemTable == null || problemTable.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(this, "삭제할 문제를 선택하세요.");
				return;
			}
			initProblemService();

			int modelRow = problemTable.convertRowIndexToModel(problemTable.getSelectedRow());
			Long problemId = (Long) tableModel.getValueAt(modelRow, 0);
			int result = problemService.deleteProblem(problemId);

			if (result > 0) {
				filterProblemsByPlatform();
			} else {
				JOptionPane.showMessageDialog(this, "삭제할 문제를 찾지 못했습니다.");
			}
		} catch (RuntimeException | ExceptionInInitializerError e) {
			JOptionPane.showMessageDialog(this, "문제 삭제 중 오류가 발생했습니다.\n" + e.getMessage());
		}
	}

	private void loadPlatformComboBox() {
		platformComboBox.removeAllItems();
		platformComboBox.addItem(ALL_PLATFORMS);

		for (String platform : difficultyOptions.keySet()) {
			platformComboBox.addItem(platform);
		}
	}

	private void filterProblemsByPlatform() {
		Object selectedItem = platformComboBox == null ? null : platformComboBox.getSelectedItem();
		if (selectedItem == null || ALL_PLATFORMS.equals(selectedItem.toString())) {
			loadProblems();
			return;
		}

		try {
			initProblemService();

			String selectedPlatform = selectedItem.toString();
			tableModel.setRowCount(0);
			for (ProblemDTO problem : problemService.seachAllProblems()) {
				DifficultyScoreDTO difficulty = problem.getDifficultyScore();
				if (difficulty != null && selectedPlatform.equals(difficulty.getPlatform())) {
					addProblemRow(problem);
				}
			}
		} catch (RuntimeException | ExceptionInInitializerError e) {
			JOptionPane.showMessageDialog(this, "문제 목록을 필터링하지 못했습니다.\n" + e.getMessage());
		}
	}

	private void loadProblems() {
		try {
			initProblemService();

			tableModel.setRowCount(0);
			List<ProblemDTO> problems = problemService.seachAllProblems();
			for (ProblemDTO problem : problems) {
				addProblemRow(problem);
			}
		} catch (RuntimeException | ExceptionInInitializerError e) {
			JOptionPane.showMessageDialog(this, "문제 목록을 불러오지 못했습니다.\n" + e.getMessage());
		}
	}

	private void addProblemRow(ProblemDTO problem) {
		DifficultyScoreDTO difficulty = problem.getDifficultyScore();
		tableModel.addRow(new Object[] {
				problem.getProblemId(),
				problem.getTitle(),
				difficulty == null ? null : difficulty.getLevel(),
				difficulty == null ? null : difficulty.getPlatform(),
				difficulty == null ? null : difficulty.getPoint()
		});
	}

	private void loadDifficultyOptions() {
		difficultyOptions.clear();

		try {
			initProblemService();

			List<DifficultyScoreDTO> difficulties = problemService.searchAllDifficulties();
			for (DifficultyScoreDTO difficulty : difficulties) {
				difficultyOptions
						.computeIfAbsent(difficulty.getPlatform(), key -> new ArrayList<>())
						.add(difficulty);
			}
		} catch (RuntimeException | ExceptionInInitializerError e) {
			JOptionPane.showMessageDialog(this, "난이도 정보를 불러오지 못했습니다.\n" + e.getMessage());
		}
	}

	private void initProblemService() {
		if (problemService == null) {
			problemService = new ProblemServiceImple();
		}
	}
}
