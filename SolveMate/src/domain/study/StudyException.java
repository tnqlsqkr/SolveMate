package domain.study;

public class StudyException extends RuntimeException {

    public StudyException() {
    }
	
	public StudyException(String msg) {
		super(msg);
	}
    public StudyException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
