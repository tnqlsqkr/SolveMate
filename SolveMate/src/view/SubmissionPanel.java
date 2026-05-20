package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import domain.member.MemberDTO;
import domain.member.MemberService;
import domain.member.MemberServiceImple;
import domain.problem.ProblemDTO;
import domain.problem.ProblemService;
import domain.problem.ProblemServiceImple;
import domain.submission.Submission;
import domain.submission.SubmissionService;
import domain.submission.SubmissionServiceImpl;
import domain.submission.SubmissionStatus;

public class SubmissionPanel extends BasePanel {
	private static final long serialVersionUID = 1L;

	private MemberService memberService;
	private ProblemService problemService;
	private SubmissionService submissionService;
	private JComboBox<MemberOption> memberComboBox;
	private JComboBox<ProblemOption> problemComboBox;
	private JComboBox<SubmissionStatus> resultComboBox;

	public SubmissionPanel() {
		super("풀이 등록", "풀이 정보");

		JPanel form = new JPanel(new GridBagLayout());
		form.setBackground(Color.WHITE);
		form.setBorder(new EmptyBorder(20, 8, 0, 8));

		GridBagConstraints gbc = createConstraints();
		form.add(createFieldLabel("회원 선택"), gbc);

		gbc.gridx = 1;
		gbc.weightx = 1;
		memberComboBox = createMemberComboBox();
		loadMembers();
		form.add(memberComboBox, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		gbc.weightx = 0;
		form.add(createFieldLabel("문제 선택"), gbc);

		gbc.gridx = 1;
		gbc.weightx = 1;
		problemComboBox = createProblemComboBox();
		loadProblems();
		form.add(problemComboBox, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		gbc.weightx = 0;
		form.add(createFieldLabel("결과 선택"), gbc);

		gbc.gridx = 1;
		gbc.weightx = 1;
		resultComboBox = new JComboBox<>(SubmissionStatus.values());
		form.add(resultComboBox, gbc);

		gbc.gridx = 1;
		gbc.gridy++;
		gbc.anchor = GridBagConstraints.WEST;
		AppButton saveButton = AppButton.primary("저장");
		saveButton.addActionListener(event -> saveSubmission());
		form.add(saveButton, gbc);

		add(form, BorderLayout.NORTH);
		add(Box.createVerticalGlue(), BorderLayout.CENTER);
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent event) {
				refreshOptions();
			}
		});
	}

	private JComboBox<MemberOption> createMemberComboBox() {
		JComboBox<MemberOption> comboBox = new JComboBox<>();
		comboBox.setPreferredSize(new java.awt.Dimension(160, 36));
		comboBox.addPopupMenuListener(new PopupMenuListener() {
			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent event) {
				loadMembers();
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent event) {
			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent event) {
			}
		});
		return comboBox;
	}

	private JComboBox<ProblemOption> createProblemComboBox() {
		JComboBox<ProblemOption> comboBox = new JComboBox<>();
		comboBox.setPreferredSize(new java.awt.Dimension(160, 36));
		comboBox.addPopupMenuListener(new PopupMenuListener() {
			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent event) {
				loadProblems();
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent event) {
			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent event) {
			}
		});
		return comboBox;
	}

	private void loadMembers() {
		try {
			if (memberService == null) {
				memberService = new MemberServiceImple();
			}

			memberComboBox.removeAllItems();
			List<MemberDTO> members = memberService.searchAllMembers();
			for (MemberDTO member : members) {
				memberComboBox.addItem(new MemberOption(member.getMemberId(), member.getName()));
			}
		} catch (RuntimeException | ExceptionInInitializerError e) {
			JOptionPane.showMessageDialog(this, "회원 목록을 불러오지 못했습니다.\n" + e.getMessage());
		}
	}

	private void loadProblems() {
		try {
			if (problemService == null) {
				problemService = new ProblemServiceImple();
			}

			problemComboBox.removeAllItems();
			List<ProblemDTO> problems = problemService.seachAllProblems();
			for (ProblemDTO problem : problems) {
				problemComboBox.addItem(new ProblemOption(problem.getProblemId(), problem.getTitle()));
			}
		} catch (RuntimeException | ExceptionInInitializerError e) {
			JOptionPane.showMessageDialog(this, "문제 목록을 불러오지 못했습니다.\n" + e.getMessage());
		}
	}

	private void refreshOptions() {
		loadMembers();
		loadProblems();
	}

	private void saveSubmission() {
		MemberOption memberOption = (MemberOption) memberComboBox.getSelectedItem();
		ProblemOption problemOption = (ProblemOption) problemComboBox.getSelectedItem();
		SubmissionStatus status = (SubmissionStatus) resultComboBox.getSelectedItem();

		if (memberOption == null) {
			JOptionPane.showMessageDialog(this, "회원을 선택하세요.");
			return;
		}
		if (problemOption == null) {
			JOptionPane.showMessageDialog(this, "문제를 선택하세요.");
			return;
		}
		if (status == null) {
			JOptionPane.showMessageDialog(this, "결과를 선택하세요.");
			return;
		}

		try {
			if (submissionService == null) {
				submissionService = new SubmissionServiceImpl();
			}

			Submission submission = new Submission();
			submission.setMemberId(memberOption.memberId);
			submission.setProblemId(problemOption.problemId);
			submission.setStatus(status);

			submissionService.add(submission);
			JOptionPane.showMessageDialog(this, "풀이가 저장되었습니다.");
		} catch (RuntimeException | ExceptionInInitializerError e) {
			JOptionPane.showMessageDialog(this, "풀이 저장 중 오류가 발생했습니다.\n" + e.getMessage());
		}
	}

	private GridBagConstraints createConstraints() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 0, 8, 12);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		return gbc;
	}

	private JLabel createFieldLabel(String text) {
		JLabel label = new JLabel(text);
		label.setForeground(new Color(70, 70, 70));
		label.setPreferredSize(new java.awt.Dimension(90, 32));
		return label;
	}

	private static class MemberOption {
		private final long memberId;
		private final String name;

		private MemberOption(Long memberId, String name) {
			this.memberId = memberId == null ? 0L : memberId;
			this.name = name;
		}

		@Override
		public String toString() {
			return name + " (" + memberId + ")";
		}
	}

	private static class ProblemOption {
		private final long problemId;
		private final String title;

		private ProblemOption(Long problemId, String title) {
			this.problemId = problemId == null ? 0L : problemId;
			this.title = title;
		}

		@Override
		public String toString() {
			return title + " (" + problemId + ")";
		}
	}
}
