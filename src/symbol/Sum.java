package symbol;
import java.math.BigDecimal;

import parser.Token;
/**
 * @author mudi
 * @version 1.0
 * +的种别码为19
 */
public class Sum extends Token{
      
	String value;
	public Sum()
	{
		value = "sum";
		type = 19;
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
		t = t + 1;
	}
	
	public void set_value(BigDecimal t)
	{
		;
	}

	
}
