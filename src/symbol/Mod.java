package symbol;

import java.math.BigDecimal;

import parser.Token;

/**
 * @author mudi
 * @version 1.0
 * mod的种别码为9
 */
public class Mod extends Token
{

  String value;

  public Mod()
  {
   type = 9;
   value = "mod";
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
