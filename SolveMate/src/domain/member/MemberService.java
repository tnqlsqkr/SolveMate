package domain.member;

import java.sql.SQLException;
import java.util.List;

public interface MemberService {
	int insertMember(MemberDTO member); // 등록
	int updateMember(MemberDTO member); // 수정
	int deleteMember(Long groupId); // 삭제
	List<MemberDTO> searchAllMembers(); // 조회
	List<MemberDTO> searchGroupMembers(Long groupId); // 조회
	MemberDTO getMember(Long memberId); // 조회
}

