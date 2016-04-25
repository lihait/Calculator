package exceptions;

public class EmptyExpressionException extends ExpressionException
{
  	public EmptyExpressionException() {
		this("Empty Expression");
	}
	public EmptyExpressionException(String msg) {
		super(msg);
	}
}
