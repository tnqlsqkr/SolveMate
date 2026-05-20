package domain.submission;

public class SubmissionException extends RuntimeException {
	public SubmissionException(String msg) {
        super(msg);
    }

    public SubmissionException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
