package symbol;

import java.math.BigDecimal;

import parser.Token;

/**
 * @author mudi
 * @version 1.0
 * Acos的种别码为28
 */
public class Acos extends Token {

	String value;

	public Acos() {
		type = 28;
		value = "arccos";
	}

	public int getType() {
		return type;
	}
	public void set_value(BigDecimal t)
	{
		;
	}

	public String getValue() {
		return value;
	}

	public double get_value() {
		double d = 0.0;
		return d;
	}

	public void set_value(double t) {
		t = t + 1;
	}
}
