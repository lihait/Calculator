package symbol;
import java.math.BigDecimal;

import parser.Token;
/**
 * @author mudi
 * @version 1.0
 * 逗号的种别码为18
 */
public class Dou extends Token{
String value;
public Dou()
{

	type = 18;
    value = ",";

}
	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return type;
	}
	 public String getValue()
	  {
	    return value;
	  }

	@Override
	public double get_value() {
		// TODO Auto-generated method stub
		  double d = 0.0;
		   return d;
	}

	@Override
	public void set_value(double t) {
		// TODO Auto-generated method stub
		t = t +1;
	}
	
	public void set_value(BigDecimal t)
	{
		;
	}

}
