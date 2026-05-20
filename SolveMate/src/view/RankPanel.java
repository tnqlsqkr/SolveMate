package view;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import domain.rank.Rank;
import domain.rank.RankService;
import domain.rank.RankServiceImpl;

public class RankPanel extends BasePanel {
	private static final long serialVersionUID = 1L;
	private static final String ALL_STUDIES = "전체";

	private RankService rankService;
	private DefaultTableModel tableModel;
	private JComboBox<String> studyComboBox;

	public RankPanel() {
		super("랭킹", "랭킹 목록");

		studyComboBox = createComboBox();
		studyComboBox.addActionListener(event -> loadRanks());

		add(createToolbar(studyComboBox), BorderLayout.NORTH);
		add(createRankTable(), BorderLayout.CENTER);
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent event) {
				refreshRanks();
			}
		});
		refreshRanks();
	}

	private JScrollPane createRankTable() {
		JScrollPane scrollPane = createTable(new String[] {"순위", "이름", "스터디", "점수", "풀이 수"});
		JTable table = (JTable) scrollPane.getViewport().getView();
		tableModel = (DefaultTableModel) table.getModel();
		return scrollPane;
	}

	private void refreshRanks() {
		loadStudyComboBox();
		loadRanks();
	}

	private void loadStudyComboBox() {
		try {
			initRankService();

			Object selectedItem = studyComboBox.getSelectedItem();
			String selectedStudy = selectedItem == null ? ALL_STUDIES : selectedItem.toString();

			Set<String> studyNames = new LinkedHashSet<>();
			for (Rank rank : rankService.getRanking()) {
				studyNames.add(rank.getGroupName());
			}

			studyComboBox.removeAllItems();
			studyComboBox.addItem(ALL_STUDIES);
			for (String studyName : studyNames) {
				studyComboBox.addItem(studyName);
			}
			studyComboBox.setSelectedItem(studyNames.contains(selectedStudy) ? selectedStudy : ALL_STUDIES);
		} catch (RuntimeException | ExceptionInInitializerError e) {
			JOptionPane.showMessageDialog(this, "스터디 목록을 불러오지 못했습니다.\n" + e.getMessage());
		}
	}

	private void loadRanks() {
		try {
			initRankService();

			Object selectedItem = studyComboBox.getSelectedItem();
			String selectedStudy = selectedItem == null ? ALL_STUDIES : selectedItem.toString();

			tableModel.setRowCount(0);
			List<Rank> ranks = rankService.getRanking();
			for (Rank rank : ranks) {
				if (!ALL_STUDIES.equals(selectedStudy) && !selectedStudy.equals(rank.getGroupName())) {
					continue;
				}
				tableModel.addRow(new Object[] {
						rank.getRank(),
						rank.getMemberName(),
						rank.getGroupName(),
						rank.getPoint(),
						rank.getSolveCount()
				});
			}
		} catch (RuntimeException | ExceptionInInitializerError e) {
			JOptionPane.showMessageDialog(this, "랭킹 목록을 불러오지 못했습니다.\n" + e.getMessage());
		}
	}

	private void initRankService() {
		if (rankService == null) {
			rankService = new RankServiceImpl();
		}
	}
}
