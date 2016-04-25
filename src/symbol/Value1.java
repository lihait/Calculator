package symbol;

import java.math.BigDecimal;

import parser.Token;


/**
 * @author mudi
 * @version 1.0
 * 数字的种别码为0
 */
public class Value1 extends Token
{
  
	BigDecimal value;


  public Value1(double t)
  {
	  value = new BigDecimal(t);
	  type = 0;
  }
  
  public Value1(BigDecimal t)
  {
	  value = t;
  }

/*  public Value1(Value1 other)
  {
   value = other.value;
  }*/

  public int getType()
  {
    return type;
  }

  public double get_value()
  {
   return value.doubleValue();
  }
  
  public BigDecimal getBigValue()
  {
	  return value;
  }
 
  public void set_value(BigDecimal bigT)
  {
	  value = bigT;
  }

  public void set_value(double t)
  {
    value = BigDecimal.valueOf(t);
  }

  public String getValue()
  {
   return ""+value;
  }

}