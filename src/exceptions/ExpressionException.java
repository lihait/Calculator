package exceptions;


public class ExpressionException extends Exception 
{
  public ExpressionException()
  {
   this("Expression errors!");
  }
  public ExpressionException(String msg) 
	{
		super(msg);
	}
}
