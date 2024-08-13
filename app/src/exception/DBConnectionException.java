package exception;

public class DBConnectionException extends Exception{
    
	private static final long serialVersionUID = 1L;

	public DBConnectionException() {}
	
	public DBConnectionException(String msg) {
		super(msg);
	}
}
