package symbol;

import java.math.BigDecimal;

import parser.Token;

/**
 * @author mudi
 * @version 1.0
 * #的种别码为5
 */
public class Pound extends Token
{

  String value;

  public Pound()
  {
   type = 16;
   value = "#";
  }

  public int getType()
  {
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
  }
  
	public void set_value(BigDecimal t)
	{
		;
	}
}
