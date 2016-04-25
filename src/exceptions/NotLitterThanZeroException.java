package exceptions;

public class NotLitterThanZeroException  extends ExpressionException{
     public NotLitterThanZeroException()
     {
    	 this("it can't be zero!");
     }
     public NotLitterThanZeroException(String msg)
     {
    	 super(msg);
     }
}
