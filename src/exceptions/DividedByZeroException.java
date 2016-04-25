package exceptions;

public class DividedByZeroException extends ExpressionException
{
	public DividedByZeroException() {
		this("Divisor is zero!");
	}
	public DividedByZeroException(String msg) {
		super(msg);
	}
}