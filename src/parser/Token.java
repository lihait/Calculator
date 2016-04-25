package parser;

import java.math.BigDecimal;

/**
 * @author lihaitao
 *
 */
public abstract class Token {
    protected int type;

    public abstract int getType();
    /*以下三个函数只在Value1子类中有用，其他子类全是无用，其他子类中此两函数的代码也是乱写，无任何价值*/
	public abstract double get_value(); 
    public abstract void set_value(double t);
    public abstract void set_value(BigDecimal t);
}
