package domain.submission;

import java.sql.SQLException;
import java.util.List;

public interface SubmissionDAO {
	void add(Submission submission)			throws SQLException;
	void update(Submission submission)		throws SQLException;
	void remove(long subId)					throws SQLException;
	Submission search(long subId) 			throws SQLException;
	List<Submission> searchAll()			throws SQLException;
	
	void addPoint(Long memberId, int point) throws SQLException;
	
	Submission findByMemberAndProblem(long memberId, long problemId) throws SQLException;
	int getScore(long problemId)	throws SQLException;
}
