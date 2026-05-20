package domain.problem;

import java.sql.SQLException;
import java.util.List;

public interface ProblemService {
	int insertProblem(ProblemDTO problem); // 등록
	int updateProblem(ProblemDTO problem); // 수정
	int deleteProblem(Long problemId); // 삭제
	List<ProblemDTO> seachAllProblems(); // 조회
	ProblemDTO getProblem(Long problemId); // 조회
	List<DifficultyScoreDTO> searchAllDifficulties();
}

