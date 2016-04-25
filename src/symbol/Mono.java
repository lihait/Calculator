package symbol;

import java.math.BigDecimal;

import parser.Token;

/**
 * @author mudi
 * @version 1.0
 * pow的种别码为5
 */
public class Mono extends Token
{

  String value;

  public Mono()
  {
   type = 5;
   value = "pow";
  }
  public int getType()
  {
	  //System.out.println("1111111");
    return type;
  }

  public String getValue()
  {
	 
    return value;
  }

  public double get_value()
  {
   double d = 0.0;
   return d;
  }

  public void set_value(double t)
  {
	 
    t=t+1;
    System.out.println("t="+t);
  }
  
	public void set_value(BigDecimal t)
	{
		;
	}
}
