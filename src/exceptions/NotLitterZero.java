package exceptions;

public class NotLitterZero  extends ExpressionException{
     public NotLitterZero()
     {
    	 this("it can't be litter than zero!");
     }
     public NotLitterZero(String msg)
     {
    	 super(msg);
     }
}
