package exceptions;

public class DomainOfArcsinOutOfBoundsException extends ExpressionException 
{
  	public DomainOfArcsinOutOfBoundsException() {
		this("Error! sin ' s operand is not from -1 to 1!");
	}
	public DomainOfArcsinOutOfBoundsException(String msg) {
		super(msg);
	}
}
