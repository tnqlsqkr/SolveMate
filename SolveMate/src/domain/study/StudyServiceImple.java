package domain.study;

import java.sql.SQLException;
import java.util.List;

public class StudyServiceImple implements StudyService {
	
	private StudyDAO studyDAO = new StudyDAOImple();

	@Override
	public int insertStudy(String gname) {
		try {
			return studyDAO.insertStudy(gname);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new StudyException("스터디 등록 중 오류 발생", e);
		}
	}

	@Override
	public int updateStudy(Long groupId, String newGname) {
		try {
			return studyDAO.updateStudy(groupId, newGname);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new StudyException("스터디 수정 중 오류 발생", e);
		}
	}

	@Override
	public int deleteStudy(Long groupId) {
		try {
			return studyDAO.deleteStudy(groupId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new StudyException("스터디 삭제 중 오류 발생", e);
		}
	}

	@Override
	public List<StudyDTO> searchAllStudies() {
		try {
			return studyDAO.seachAllStudies();
		}catch (SQLException e) {
			e.printStackTrace();
			throw new StudyException("전체 스터디 정보 조회 중 오류 발생", e);
		}
	}

	@Override
	public StudyDTO getStudy(Long groupId) {
		try {
			StudyDTO study = studyDAO.getStudy(groupId);
			if(study == null) throw new StudyException("해당 스터디를 찾을 수 없습니다.");
			return study;
		}catch (SQLException e) {
			e.printStackTrace();
			throw new StudyException("스터디 조회 중 오류 발생", e);
		}
	}
	
	
	
}
