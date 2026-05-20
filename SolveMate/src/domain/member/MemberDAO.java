package domain.member;

import java.sql.SQLException;
import java.util.List;

public interface MemberDAO {
	int insertMember(MemberDTO member) throws SQLException; // 등록
	int updateMember(MemberDTO member) throws SQLException; // 수정
	int deleteMember(Long groupId) throws SQLException; // 삭제
	List<MemberDTO> searchAllMembers() throws SQLException; // 조회
	List<MemberDTO> searchGroupMembers(Long groupId) throws SQLException; // 조회
	MemberDTO getMember(Long memberId) throws SQLException; // 조회
}
