package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import domain.member.MemberDTO;
import domain.member.MemberService;
import domain.member.MemberServiceImple;
import domain.study.StudyDTO;
import domain.study.StudyService;
import domain.study.StudyServiceImple;

public class MemberPanel extends BasePanel {
	private static final long serialVersionUID = 1L;

	private MemberService memberService;
	private StudyService studyService;
	private DefaultTableModel tableModel;
	private JTable memberTable;
	private JComboBox<StudyOption> studyComboBox;
	private final Map<Long, String> studyNames = new LinkedHashMap<>();

	public MemberPanel() {
		super("회원 관리", "회원 목록");

		AppButton addButton = AppButton.primary("+ 회원 추가");
		AppButton deleteButton = AppButton.danger("선택 삭제");

		addButton.addActionListener(event -> addMember());
		deleteButton.addActionListener(event -> deleteSelectedMemberOrStudy());

		studyComboBox = createStudyComboBox();
		loadStudyComboBox();
		studyComboBox.addActionListener(event -> filterMembersByStudy());

		add(createToolbar(studyComboBox, addButton, deleteButton), BorderLayout.NORTH);
		add(createMemberTable(), BorderLayout.CENTER);
		loadMembers();
	}

	private JComboBox<StudyOption> createStudyComboBox() {
		JComboBox<StudyOption> comboBox = new JComboBox<>();
		comboBox.setPreferredSize(new Dimension(160, 36));
		return comboBox;
	}

	private JScrollPane createMemberTable() {
		tableModel = new DefaultTableModel(new String[] {"MEMBER_ID", "GROUP_ID", "이름", "스터디", "가입일"}, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		memberTable = new JTable(tableModel);
		memberTable.setRowHeight(36);
		memberTable.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));
		memberTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		memberTable.setShowVerticalLines(false);
		memberTable.setGridColor(new Color(232, 232, 232));
		memberTable.setSelectionBackground(new Color(226, 240, 255));

		TableColumnModel columnModel = memberTable.getColumnModel();
		columnModel.removeColumn(columnModel.getColumn(1));
		columnModel.removeColumn(columnModel.getColumn(0));

		JTableHeader header = memberTable.getTableHeader();
		header.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
		header.setBackground(new Color(248, 249, 250));
		header.setForeground(new Color(55, 55, 55));
		header.setPreferredSize(new Dimension(0, 38));

		JScrollPane scrollPane = new JScrollPane(memberTable);
		scrollPane.setBorder(new LineBorder(new Color(225, 225, 225), 1, true));
		scrollPane.getViewport().setBackground(Color.WHITE);
		return scrollPane;
	}

	private void addMember() {
		String[] values = FormDialog.show(
				(JFrame) SwingUtilities.getWindowAncestor(this),
				"회원 추가",
				new String[] {"이름", "스터디명"});
		if (values == null || values.length < 2 || values[0] == null || values[1] == null) {
			return;
		}

		String name = values[0].trim();
		String studyName = values[1].trim();
		if (name.isEmpty() || studyName.isEmpty()) {
			JOptionPane.showMessageDialog(this, "이름과 스터디명을 입력하세요.");
			return;
		}

		try {
			initServices();

			Long groupId = findStudyIdByName(studyName);
			if (groupId == null) {
				int studyResult = studyService.insertStudy(studyName);
				if (studyResult <= 0) {
					JOptionPane.showMessageDialog(this, "스터디를 생성하지 못했습니다.");
					return;
				}
				groupId = findStudyIdByName(studyName);
			}
			if (groupId == null) {
				JOptionPane.showMessageDialog(this, "생성한 스터디를 찾지 못했습니다.");
				return;
			}

			MemberDTO member = new MemberDTO();
			member.setName(name);
			member.setGroupId(groupId);

			int memberResult = memberService.insertMember(member);
			if (memberResult > 0) {
				loadStudyComboBox();
				
				filterMembersByStudy();
			} else {
				JOptionPane.showMessageDialog(this, "회원을 추가하지 못했습니다.");
			}
		} catch (RuntimeException | ExceptionInInitializerError e) {
			JOptionPane.showMessageDialog(this, "회원 추가 중 오류가 발생했습니다.\n" + e.getMessage());
		}
	}

	private void loadStudyComboBox() {
		try {
			initStudyService();

			studyNames.clear();
			studyComboBox.removeAllItems();
			studyComboBox.addItem(new StudyOption(null, "전체"));

			List<StudyDTO> studies = studyService.searchAllStudies();
			for (StudyDTO study : studies) {
				studyNames.put(study.getGroupId(), study.getGname());
				studyComboBox.addItem(new StudyOption(study.getGroupId(), study.getGname()));
			}
		} catch (RuntimeException | ExceptionInInitializerError e) {
			JOptionPane.showMessageDialog(this, "스터디 목록을 불러오지 못했습니다.\n" + e.getMessage());
		}
	}

	private Long findStudyIdByName(String studyName) {
		List<StudyDTO> studies = studyService.searchAllStudies();
		for (StudyDTO study : studies) {
			if (studyName.equals(study.getGname())) {
				return study.getGroupId();
			}
		}
		return null;
	}

	private void selectStudy(Long groupId) {
		for (int i = 0; i < studyComboBox.getItemCount(); i++) {
			StudyOption option = studyComboBox.getItemAt(i);
			if (groupId.equals(option.groupId)) {
				studyComboBox.setSelectedIndex(i);
				return;
			}
		}
	}

	private void deleteSelectedMemberOrStudy() {
		if (memberTable == null || memberTable.getSelectedRow() == -1) {
			deleteSelectedStudyIfEmpty();
			return;
		}
		deleteSelectedMember();
	}

	private void deleteSelectedMember() {
		try {
			initMemberService();

			int modelRow = memberTable.convertRowIndexToModel(memberTable.getSelectedRow());
			Long memberId = (Long) tableModel.getValueAt(modelRow, 0);
			int result = memberService.deleteMember(memberId);

			if (result > 0) {
				filterMembersByStudy();
			} else {
				JOptionPane.showMessageDialog(this, "삭제할 회원을 찾지 못했습니다.");
			}
		} catch (RuntimeException | ExceptionInInitializerError e) {
			JOptionPane.showMessageDialog(this, "회원 삭제 중 오류가 발생했습니다.\n" + e.getMessage());
		}
	}

	private void deleteSelectedStudyIfEmpty() {
		StudyOption selectedItem = (StudyOption) studyComboBox.getSelectedItem();
		if (selectedItem == null || selectedItem.groupId == null) {
			JOptionPane.showMessageDialog(this, "삭제할 회원 또는 스터디를 선택하세요.");
			return;
		}

		try {
			initServices();

			List<MemberDTO> members = memberService.searchGroupMembers(selectedItem.groupId);
			if (!members.isEmpty()) {
				JOptionPane.showMessageDialog(this, "스터디에 회원이 있어 스터디를 삭제할 수 없습니다.");
				return;
			}

			int result = studyService.deleteStudy(selectedItem.groupId);
			if (result > 0) {
				loadStudyComboBox();
				loadMembers();
			} else {
				JOptionPane.showMessageDialog(this, "삭제할 스터디를 찾지 못했습니다.");
			}
		} catch (RuntimeException | ExceptionInInitializerError e) {
			JOptionPane.showMessageDialog(this, "스터디 삭제 중 오류가 발생했습니다.\n" + e.getMessage());
		}
	}

	private void filterMembersByStudy() {
		StudyOption selectedItem = (StudyOption) studyComboBox.getSelectedItem();
		if (selectedItem == null || selectedItem.groupId == null) {
			loadMembers();
			return;
		}

		try {
			initMemberService();

			tableModel.setRowCount(0);
			addMemberRows(memberService.searchGroupMembers(selectedItem.groupId));
		} catch (RuntimeException | ExceptionInInitializerError e) {
			JOptionPane.showMessageDialog(this, "회원 목록을 필터링하지 못했습니다.\n" + e.getMessage());
		}
	}

	private void loadMembers() {
		try {
			initMemberService();

			tableModel.setRowCount(0);
			addMemberRows(memberService.searchAllMembers());
		} catch (RuntimeException | ExceptionInInitializerError e) {
			JOptionPane.showMessageDialog(this, "회원 목록을 불러오지 못했습니다.\n" + e.getMessage());
		}
	}

	private void addMemberRows(List<MemberDTO> members) {
		for (MemberDTO member : members) {
			tableModel.addRow(new Object[] {
					member.getMemberId(),
					member.getGroupId(),
					member.getName(),
					getStudyName(member.getGroupId()),
					member.getCreatedAt()
			});
		}
	}

	private String getStudyName(Long groupId) {
		String studyName = studyNames.get(groupId);
		if (studyName != null) {
			return studyName;
		}
		return groupId == null ? "" : String.valueOf(groupId);
	}

	private void initServices() {
		initMemberService();
		initStudyService();
	}

	private void initMemberService() {
		if (memberService == null) {
			memberService = new MemberServiceImple();
		}
	}

	private void initStudyService() {
		if (studyService == null) {
			studyService = new StudyServiceImple();
		}
	}

	private static class StudyOption {
		private final Long groupId;
		private final String name;

		private StudyOption(Long groupId, String name) {
			this.groupId = groupId;
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}
}
