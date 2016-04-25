package parser;

import java.math.BigDecimal;

import exceptions.*;

public class Calculator {

    public BigDecimal calculate(String expression) throws ExpressionException {
        Parser parser = new Parser(expression+"#");
        parser.guiyue();
     	return parser.bigResult;
    }
}
