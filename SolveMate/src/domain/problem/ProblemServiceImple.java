package domain.problem;

import java.sql.SQLException;
import java.util.List;

public class ProblemServiceImple implements ProblemService {
	
	private ProblemDAO problemDAO = new ProblemDAOImple();

	@Override
	public int insertProblem(ProblemDTO problem) {
		try {
			return problemDAO.insertProblem(problem);
		} catch (Exception e) {
			throw new ProblemException("문제 등록 중 오류 발생", e);
		}
	}

	@Override
	public int updateProblem(ProblemDTO problem) {
		try {
			return problemDAO.updateProblem(problem);
		} catch (Exception e) {
			throw new ProblemException("문제 수정 중 오류 발생", e);
		}
	}

	@Override
	public int deleteProblem(Long problemId) {
        try {
            return problemDAO.deleteProblem(problemId);
        } catch (SQLException e) {
            throw new ProblemException("문제 삭제 중 오류 발생", e);
        }
	}

	@Override
	public List<ProblemDTO> seachAllProblems(){
        try {
            return problemDAO.searchAllProblems();
        } catch (SQLException e) {
            throw new ProblemException("문제 전체 조회 중 오류 발생", e);
        }
	}

	@Override
	public ProblemDTO getProblem(Long problemId) {
        try {
            return problemDAO.getProblem(problemId);
        } catch (SQLException e) {
            throw new ProblemException("문제 조회 중 오류 발생", e);
        }
	}

	@Override
	public List<DifficultyScoreDTO> searchAllDifficulties() {
	    try {
	        return problemDAO.searchAllDifficulties();
	    } catch (SQLException e) {
	        throw new ProblemException("난이도 목록 조회 중 오류 발생", e);
	    }
	}
	
	
}
