package symbol;
import java.math.BigDecimal;

import parser.Token;
/**
 * @author mudi
 * @version 1.0
 * yroot的种别码为17
 */
public class Root extends Token{
      String value;
      public Root()
      {
    	  type = 17;
    	  value = "yroot";   	  
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
