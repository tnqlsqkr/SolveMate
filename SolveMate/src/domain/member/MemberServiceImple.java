package domain.member;

import java.sql.SQLException;
import java.util.List;

import domain.problem.ProblemException;

public class MemberServiceImple implements MemberService {
	
	private MemberDAO MemberDAO = new MemberDAOImple();

	@Override
	public int insertMember(MemberDTO member) {
		try {
			return MemberDAO.insertMember(member);
		} catch (Exception e) {
			throw new ProblemException("멤버 등록 중 오류 발생", e);
		}
	}

	@Override
	public int updateMember(MemberDTO member) {
		try {
			return MemberDAO.updateMember(member);
		} catch (Exception e) {
			throw new ProblemException("멤버 수정 중 오류 발생", e);
		}
	}

	@Override
	public int deleteMember(Long groupId) {
		try {
			return MemberDAO.deleteMember(groupId);
		} catch (Exception e) {
			throw new ProblemException("멤버 삭제 중 오류 발생", e);
		}
	}

	@Override
	public List<MemberDTO> searchAllMembers() {
		try {
			return MemberDAO.searchAllMembers();
		} catch (Exception e) {
			throw new ProblemException("멤버 전체 조회 중 오류 발생", e);
		}
	}

	@Override
	public List<MemberDTO> searchGroupMembers(Long groupId) {
		try {
			return MemberDAO.searchGroupMembers(groupId);
		} catch (Exception e) {
			throw new ProblemException("멤버 그룹 조회 중 오류 발생", e);
		}
	}

	@Override
	public MemberDTO getMember(Long memberId) {
		try {
			return MemberDAO.getMember(memberId);
		} catch (Exception e) {
			throw new ProblemException("멤버 조회 중 오류 발생", e);
		}
	}

	
	
	
}
