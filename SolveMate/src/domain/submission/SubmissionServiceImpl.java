package domain.submission;

import java.sql.SQLException;
import java.util.List;

public class SubmissionServiceImpl implements SubmissionService {
	private SubmissionDAO dao = SubmissionFactory.getSubmission();
	
	@Override
	public void add(Submission submission) {
		try {
			long memberId = submission.getMemberId();
	        long problemId = submission.getProblemId();
			
	        Submission existing = dao.findByMemberAndProblem(memberId, problemId);
			
	        if (existing != null) {
	        	
	        	if(existing.getStatus() == SubmissionStatus.SUCCESS)
	        		throw new SubmissionException("이미 성공한 제출은 수정할 수 없습니다.");
	        	
	        	if (existing.getStatus() == SubmissionStatus.FAIL &&
	                    submission.getStatus() == SubmissionStatus.SUCCESS) {

	                    int point = dao.getScore(problemId);
	                    dao.addPoint(memberId, point);
	            }
	        	
	        	existing.setStatus(submission.getStatus());
	        	dao.update(existing);
	        	return ;
	        }
			
	        dao.add(submission);
	        
	        if (submission.getStatus() == SubmissionStatus.SUCCESS) {
	            int point = dao.getScore(problemId);
	            dao.addPoint(memberId, point);
	        }
	        
	        
		} catch(SQLException e) {
			throw new SubmissionException("등록 중 오류 발생", e);
		}
	}
	
	@Override
	public void update(Submission submission) {
		try {
			Submission existing = search(submission.getSubId());
			
			if(existing.getStatus() == SubmissionStatus.SUCCESS)
				throw new SubmissionException("이미 성공한 제출은 수정할 수 없습니다.");
			
			long memberId = submission.getMemberId();
	        long problemId = submission.getProblemId();
			
			if (existing.getStatus() == SubmissionStatus.FAIL &&
                    submission.getStatus() == SubmissionStatus.SUCCESS) {
				
				int point = dao.getScore(problemId);
                dao.addPoint(memberId, point);
			}
			
			dao.update(submission);
			
		}catch (SQLException e) {
			throw new SubmissionException("제출 정보 수정 중 오류 발생", e);
		}
	}
	
	@Override
	public void remove(long subId) {
		try {
			search(subId);
			
			dao.remove(subId);
			
		}catch (SQLException e) {
			throw new SubmissionException("제출 정보 삭제 중 오류 발생", e);
		}
	}
	
	@Override
	public Submission search(long subId) {
		try {
			Submission submission = dao.search(subId);
			
			if(submission == null) {
				throw new SubmissionException("제출 기록을 찾을 수 없음");
			}
			
			return submission;
			
		}catch (SQLException e) {
			throw new SubmissionException("제출 정보 조회 중 오류 발생", e);
		}
	}
	
	@Override
	public List<Submission> searchAll() {
		try {
			return dao.searchAll();
			
		}catch (SQLException e) {
			throw new SubmissionException("전체 제출 조회 중 오류 발생", e);
		}
	}
}
