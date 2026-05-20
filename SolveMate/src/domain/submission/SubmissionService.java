package domain.submission;

import java.util.List;

public interface SubmissionService {
	void add(Submission submission);
	void update(Submission submission);
	void remove(long subId);
	Submission search(long subId);
	List<Submission> searchAll();
}
