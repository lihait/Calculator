package exceptions;

public class LogException extends ExpressionException
{
	public LogException() {
		this("Error! ");
	}
	public LogException(String msg) {
		super(msg);
	}
}