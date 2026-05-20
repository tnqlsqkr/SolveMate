package domain.problem;

public class ProblemException extends RuntimeException {

    public ProblemException() {
    }
	
	public ProblemException(String msg) {
		super(msg);
	}
    public ProblemException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
