package exceptions;

public class SqrtException extends ExpressionException 
{
  	public SqrtException() {
		this("Sqrt ' s operand is negative!");
	}
	public SqrtException(String msg) {
		super(msg);
	}
}
