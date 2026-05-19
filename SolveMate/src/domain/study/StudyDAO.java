package domain.study;

import java.sql.SQLException;
import java.util.List;

public interface StudyDAO {
	int insertStudy(String gname) throws SQLException; // 등록
	int updateStudy(Long groupId, String newGname) throws SQLException; // 수정
	int deleteStudy(Long groupId) throws SQLException; // 삭제
	List<StudyDTO> seachAllStudies() throws SQLException; // 조회
	StudyDTO getStudy(Long groupId) throws SQLException; // 조회
}
