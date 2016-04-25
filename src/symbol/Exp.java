package symbol;
import java.math.BigDecimal;
import parser.Token;
/**
 * @author mudi
 * @version 1.0
 * exp的种别码为13
 */
public class Exp extends Token {
	
	String value;
	public Exp(){
		type = 13;
		value = "exp";
	}
	public int getType() {
		// TODO Auto-generated method stub
		return type;
	}
	public String getValue()
	  {
	    return value;
	  }
	public double get_value() {
		// TODO Auto-generated method stub
		double d = 0.0;
		return d;
	}

	public void set_value(double t) {
		// TODO Auto-generated method stub
		t = t + 1;
	}
	
	public void set_value(BigDecimal t)
	{
		;
	}

}
