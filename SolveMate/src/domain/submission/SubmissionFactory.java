package domain.submission;

public class SubmissionFactory {
	private static SubmissionDAO dao = new SubmissionDAOImpl();

    public static SubmissionDAO getSubmission() {
        return dao;
    }
}
