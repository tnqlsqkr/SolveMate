package domain.member;

public class MemberException extends RuntimeException {

    public MemberException() {
    }
	
	public MemberException(String msg) {
		super(msg);
	}
    public MemberException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
