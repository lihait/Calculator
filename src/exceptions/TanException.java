package exceptions;

public class TanException extends ExpressionException 
{
  	public TanException() {
		this("Error ! Tan ' s operand is 90 * k!");
	}
	public TanException(String msg) {
		super(msg);
	}
}
