package exception;

public class OperationException extends Exception {
    

	private static final long serialVersionUID = 1L;

	public OperationException() {}
	
	public OperationException(String msg) {
		super(msg);
	}
}
