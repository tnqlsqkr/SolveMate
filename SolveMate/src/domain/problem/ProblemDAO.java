package domain.problem;

import java.sql.SQLException;
import java.util.List;

public interface ProblemDAO {
	int insertProblem(ProblemDTO problem) throws SQLException; // 등록
	int updateProblem(ProblemDTO problem) throws SQLException; // 수정
	int deleteProblem(Long problemId) throws SQLException; // 삭제
	List<ProblemDTO> searchAllProblems() throws SQLException; // 조회
	ProblemDTO getProblem(Long problemId) throws SQLException; // 조회
	List<DifficultyScoreDTO> searchAllDifficulties() throws SQLException; 
}
