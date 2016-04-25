package exceptions;

public class DomainOfArccosOutOfBoundsException extends ExpressionException 
{
  	public DomainOfArccosOutOfBoundsException() {
		this("Error ! arccos ' s operand is not from -1 to 1!");
	}
	public DomainOfArccosOutOfBoundsException(String msg) {
		super(msg);
	}
}
