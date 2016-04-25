package parser;

import exceptions.*;
import symbol.*;
import parser.*;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @author mudi
 * @version 1.0 Scan类的主要作用是扫描输入字符串，进行词法分析
 */
public class Scan {
	boolean label = false;
	int index, flag, k;

	// 存储待输入字符串
	ArrayList<String> input = new ArrayList<String>();

	public Scan(String temp) {

		// 使对大小写不敏感
		temp = temp.toLowerCase();
		for (k = 0; k < temp.length(); k++) {
			input.add(temp.substring(k, k + 1));
		}
		index = 0;
	}

	public void forward() {
		index--;
	}

	/**
	 * 
	 * @param s
	 * @return Token类的对象
	 * @throws EmptyExpressionException
	 * @throws ExpressionException
	 */
	public Token setToken(String s) throws EmptyExpressionException,
			ExpressionException {
		if (s.equalsIgnoreCase("+")) {
			return new Plus();
		} else if (s.equalsIgnoreCase("(")) {
			return new Lbracket();
		} else if (s.equalsIgnoreCase("0")) {
			return new Value1(0);
		}
		return null;
	}

	/**
	 * 词法分析 在输入流中扫描，每次返回一个单词，包含一些异常的返回
	 * 
	 * @return 各种合法的Token
	 * @throws EmptyExpressionException
	 * @throws ExpressionException
	 */
	public Token getNextToken() throws EmptyExpressionException,
			ExpressionException {
		flag = 0;// flag为0表示未得到可用单词
		if (input.size() == 1) {
			throw new EmptyExpressionException();
		}
		if (index != input.size()) {
			String mark, ex = "";
			String str = "+";
			mark = input.get(index);

			// 清除不必要的空格，对应DFA的初态S0
			while (mark.equals(" ")) {
				if (index + 1 != input.size()) {
					index++;
					mark = input.get(index);
				} else if (flag == 0) {
					throw new EmptyExpressionException();
				} else {
					break;
				}
			}

			// 遇到数字的话，DFA中状态由S0进入S1
			if (Character.isDigit(mark.charAt(0))) {
				flag = 1;
				double temp1 = 0;
				String mark2 = "flag";

				if (index + 1 != input.size()) {
					index++;
					mark2 = input.get(index);
					index--;
				}
				// 如果数字的第一个字符是0，且后面也是数字，抛出异常
				while (Character.isDigit(mark.charAt(0))) {
					ex += mark;
					if (index + 1 != input.size()) {
						index++;
						mark = input.get(index);
					} else {
						break;
					}
				}
				/*
				 * 如果数字后碰到小数点，小数点后不是数字，则要抛出表达式错误异常;
				 * 如果还是数字，则和上面的方法一样，将一串数字归约为一个整数 DFA中状态由S1转入S2
				 */
				if (mark.equals(".")) {
					flag = 2;
					ex += mark;
					index++;
					mark = input.get(index);
					if (!Character.isDigit(mark.charAt(0))) {
						throw new ExpressionException();
					}
					while (Character.isDigit(mark.charAt(0))) {
						ex += mark;
						if (index + 1 != input.size()) {
							index++;
							mark = input.get(index);
						} else {
							break;
						}
					}
				}

				Value1 tempValue = new Value1(new BigDecimal(ex).setScale(40,BigDecimal.ROUND_HALF_EVEN));
				ex = "";
				return tempValue;

			}

			// DFA中状态进入S6，返回Token +
			if (mark.equals("+")) {
				index++;
				return new Plus();
			}

			// DFA中状态进入S7，返回Token -
			if (mark.equals("-")) {
				index++;
				return new Sub();
			}

			// DFA中状态进入S8，返回Token *
			if (mark.equals("*")) {
				index++;
				return new Mult();
			}

			// DFA中状态进入S9，返回Token /
			if (mark.equals("/")) {
				index++;
				return new Divi();
			}
			// DFA中状态进入S18，返回Token ,
			if (mark.equals(",")) {
				index++;
				return new Dou();
			}
			// DFA中状态进入S10，返回Token Mon
			if (mark.equals("^")) {
				index++;
				return new Mon();
			}
			// DFA中状态进入S14，返回Token (
			if (mark.equals("(")) {
				index++;
				return new Lbracket();
			}
			// DFA中状态进入S15，返回Token )
			if (mark.equals(")")) {
				index++;
				return new Rbracket();
			}
			// DFA中状态进入S20，返回Token [
			if (mark.equals("[")) {
				index++;
				return new Lbm();
			}
			// DFA中状态进入S21，返回Token ]
			if (mark.equals("]")) {
				index++;
				return new Rbm();
			}
			// 处理sqrt sin and cos exp tan等等函数，DFA状态由S0转到S6，sqrt sin and cos exp
			// tan
			if (mark.equals("a") || mark.equals("s") || mark.equals("c")
					|| mark.equals("e") || mark.equals("p") || mark.equals("t")
					|| mark.equals("f") || mark.equals("y") || mark.equals("m")
					|| mark.equals("v") || mark.equals("l")) {
				ex = "" + mark;
				do {
					index++;
					if (index == input.size()) {
						throw new ExpressionException();
					}
					mark = input.get(index);
					if (mark.charAt(0) >= 'a' && mark.charAt(0) <= 'z') {
						ex += mark;
					}
					// log10(x)
					else if (ex.equals("log") && mark.equals("1")
							&& input.get(index + 1).equals("0")) {
						ex += mark + input.get(index + 1);
						index += 2;
						break;
					} else {
						break;
					}
				} while (true);
				if (ex.equals("sqrt")) {
					return new Sqrt();
				} else if (ex.equals("sin")) {
					return new Sin();
				} else if (ex.equals("cos")) {
					return new Cos();
				} else if (ex.equals("tan")) {
					return new Tan();
				} else if (ex.equals("arcsin")) {
					return new Asin();
				} else if (ex.equals("arccos")) {
					return new Acos();
				} else if (ex.equals("arctan")) {
					return new Atan();
				} else if (ex.equals("sinh")) {
					return new Sinh();
				} else if (ex.equals("cosh")) {
					return new Cosh();
				} else if (ex.equals("tanh")) {
					return new Tanh();
				} else if (ex.equals("exp")) {
					return new Exp();
				} else if (ex.equals("pow")) {
					return new Mono();
				} else if (ex.equals("fact")) {
					return new Fact();
				} else if (ex.equals("cuberoot")) {
					return new Cuberoot();
				} else if (ex.equals("yroot")) {
					return new Root();
				} else if (ex.equals("mod")) {
					return new Mod();
				} else if (ex.equals("sum")) {
					return new Sum();
				} else if (ex.equals("avg")) {
					return new Avg();
				} else if (ex.equals("varp")) {
					return new Varp();
				} else if (ex.equals("var")) {
					return new Var();
				} else if (ex.equals("stdevp")) {
					return new Stdevp();
				} else if (ex.equals("stdev")) {
					return new Stdev();
				} else if (ex.equals("ln")) {
					return new Ln();
				} else if (ex.equals("log10")) {
					return new Log10();
				} else if (ex.equals("log")) {
					return new Log();
				} else {
					throw new ExpressionException();
				}
			}

			// DFA中状态进入S11，返回Token #
			if (mark.equals("#")) {
				return new Pound();
			}
			// DFA中状态进入S4，返回Token Value1
			if (mark.equals(".")) {
				double temp1 = 0;
				ex = "0";
				flag = 2;
				ex += mark;
				index++;
				mark = input.get(index);
				if (!Character.isDigit(mark.charAt(0))) {
					throw new ExpressionException();
				}

				// 读完小数点后面的数字
				while (Character.isDigit(mark.charAt(0))) {
					ex += mark;
					if (index + 1 != input.size()) {
						index++;
						mark = input.get(index);
					} else {
						break;
					}
				}
				temp1 = Double.parseDouble(ex);
				ex = "";
				return new Value1(temp1);
			}
			// 前面所有操作不匹配，故是未定义的字符，所以认为表达式有误，异常DFA中状态转入S3
			throw new ExpressionException();
		}
		return null;
	}
}