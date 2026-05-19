package domain.study;

import java.sql.SQLException;
import java.util.List;

public interface StudyService {
	int insertStudy(String gname); // 등록
	int updateStudy(Long groupId, String newGname); // 수정
	int deleteStudy(Long groupId); // 삭제
	List<StudyDTO> searchAllStudies(); // 조회
	StudyDTO getStudy(Long groupId); // 조회
}

