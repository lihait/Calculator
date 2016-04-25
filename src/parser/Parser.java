package parser;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Stack;

import symbol.Plus;
import symbol.Pound;
import symbol.Value1;
import exceptions.DividedByZeroException;
import exceptions.DomainOfArccosOutOfBoundsException;
import exceptions.DomainOfArcsinOutOfBoundsException;
import exceptions.EmptyExpressionException;
import exceptions.ExpressionException;
import exceptions.LogException;
import exceptions.NotLitterThanZeroException;
import exceptions.NotLitterZero;
import exceptions.SqrtException;
import exceptions.TanException;

/**
 * @author lihaitao
 * @version 1.0 parser类主要实现语法分析和语义分析。对经过词法分析的表达式进行计算，并抛出相应异常。
 */
public class Parser {
	// 定义集合运算求平均时需要的计数的计数初值为2
	int count = 2;
	// 定义一个初值为0的变量用来在实现集合运算的嵌套时保留count原来的值
	int count1 = 0;
	private static double v1;
	// 定义两个初始变量，用于集合运算的嵌套
	private static double tt5 = 0;
	private static double tt6 = 0;
	public double result = 0;
	public BigDecimal bigResult = new BigDecimal(0);
	Token b5;
	Opp_table table = new Opp_table();
	ArrayList<Token> stack = new ArrayList<Token>();
	ArrayList<Token> stack1 = new ArrayList<Token>();
	// 生成一个动态数组，用来存储“+”号运算符，已实现“--”转变为“+”
	ArrayList<Token> stack7 = new ArrayList<Token>();
	// 创建栈存储count的值
	Stack<Integer> stack2 = new Stack<Integer>();
	// 创建栈存储在计算过程中产生的值
	Stack<Double> stack3 = new Stack<Double>();
	Stack<Double> stack5 = new Stack<Double>();
	Stack<Double> stack6 = new Stack<Double>();
	Scan scanner;
	int index = 0;
	int index1 = 0;
	int index2 = 0;
	int top;
	int size = 0;

	/**
	 * 构造函数
	 * 
	 * @param temp
	 */
	public Parser(String temp) {
		scanner = new Scan(temp);
		stack.add(new Pound());
		stack1.add(new Pound());
		stack7.add(new Plus());
	}

	/**
	 * 定义push方法，实现压栈操作
	 * 
	 * @param temp
	 */
	public void push(Token temp) {
		if (temp.getType() != 16) {
			index++;
			stack.add(temp);
		}
	}

	public void push1(Token temp) {
		if (temp.getType() != 16) {
			index1++;
			stack1.add(temp);
		}
	}

	/**
	 * 判断栈是否为空
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return top == -1;
	}

	/**
	 * 清空栈
	 */
	public void clear() {
		stack3.clear();
		size = 0;
	}

	/**
	 * 存储count1,实现压栈操作
	 * 
	 * @param count123
	 */
	public void push2(int count123) {
		stack2.push(count123);
	}

	public void push3(double a) {
		stack3.push(a);
	}

	public void push5(double c) {
		stack5.push(c);
	}

	public void push6(double d) {
		stack6.push(d);
	}

	/**
	 * double_to_int将double型数值转为int型
	 * 
	 * @param t
	 * @return
	 */
	public int double_to_int(double t) {
		String s1 = String.valueOf(t);
		String s2 = s1.substring(0, s1.indexOf("."));
		int i = Integer.parseInt(s2);
		return i;
	}

	/**
	 * 该函数实现一元操作 fact sqrt cuberoot sin cos exp tan arcsin arccos arctan sinh
	 * cosh tanh
	 * 
	 * @param temp
	 * @throws SqrtException
	 * @throws ExpressionException
	 * @throws NotLitterZero
	 */
	public void unary(Token temp) throws SqrtException, ExpressionException,
			NotLitterZero {
		Token t, s = null, q = null;
		double tt, tt1;
		int tt2;
		// 取一元运算符
		t = stack.get(index);
		stack.remove(index);
		index--;
		// 取操作数
		tt = temp.get_value();
		// 判断一元运算符左边是不是“-”
		s = stack.get(index);
		// 判断“-”左边是“#”或是“(”还是“[”
		if (index >= 1)
			q = stack.get(index - 1);

		switch (t.getType()) {
		case 1:
			tt = temp.get_value();
			break;
		case 2:
			tt = -1 * temp.get_value();
			break;
		case 6:
			if (tt % (Math.PI / 2) == 0) {
				throw new TanException();
			}
			/*
			 * 如果一元运算符左边是“+”，“+”左边是“#”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“(”，则“+”处理为正号
			 * .
			 * 或者一元运算符左边是“+”，“+”左边是“,”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“[”，则“+”处理为正号
			 * 。 计算后将“+”从栈中移除移除
			 */
			else if ((s.getType() == 1 && q.getType() == 16)
					|| (s.getType() == 1 && q.getType() == 14)
					|| (s.getType() == 1 && q.getType() == 18)
					|| (s.getType() == 1 && q.getType() == 20)) {
				tt = Math.tan(Math.toRadians(tt));
				stack.remove(index);
				index--;
			}
			/*
			 * 如果一元运算符左边是“-”，“-”左边是“#”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“(”，则“-”处理为负号
			 * .
			 * 或者一元运算符左边是“-”，“-”左边是“,”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“[”，则“-”处理为负号
			 * 。 计算后将“-”从栈中移除移除
			 */
			else if ((s.getType() == 2 && q.getType() == 16)
					|| (s.getType() == 2 && q.getType() == 14)
					|| (s.getType() == 2 && q.getType() == 18)
					|| (s.getType() == 2 && q.getType() == 20)) {
				tt = -1 * Math.tan(Math.toRadians(tt));
				stack.remove(index);
				index--;
			}
			// 否则不做任何处理，直接得出结果
			else {
				tt = Math.tan(Math.toRadians(tt));
			}
			break;
		case 7:
			/*
			 * 如果一元运算符左边是“+”，“+”左边是“#”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“(”，则“+”处理为正号
			 * .
			 * 或者一元运算符左边是“+”，“+”左边是“,”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“[”，则“+”处理为正号
			 * 。 计算后将“+”从栈中移除移除
			 */
			if ((s.getType() == 1 && q.getType() == 16)
					|| (s.getType() == 1 && q.getType() == 14)
					|| (s.getType() == 1 && q.getType() == 18)
					|| (s.getType() == 1 && q.getType() == 20)) {
				tt = Math.cbrt(tt);
				stack.remove(index);
				index--;
			}
			/*
			 * 如果一元运算符左边是“-”，“-”左边是“#”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“(”，则“-”处理为负号
			 * .
			 * 或者一元运算符左边是“-”，“-”左边是“,”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“[”，则“-”处理为负号
			 * 。 计算后将“-”从栈中移除移除
			 */
			else if ((s.getType() == 2 && q.getType() == 16)
					|| (s.getType() == 2 && q.getType() == 14)
					|| (s.getType() == 2 && q.getType() == 18)
					|| (s.getType() == 2 && q.getType() == 20)) {
				tt = -1 * Math.cbrt(tt);
				stack.remove(index);
				index--;
			}
			// 否则不做任何处理，直接得出结果
			else {
				tt = Math.cbrt(tt);
			}
			break;
		case 8:
			/*
			 * 如果一元运算符左边是“+”，“+”左边是“#”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“(”，则“+”处理为正号
			 * .
			 * 或者一元运算符左边是“+”，“+”左边是“,”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“[”，则“+”处理为正号
			 * 。 计算后将“+”从栈中移除移除
			 */
			if ((s.getType() == 1 && q.getType() == 16)
					|| (s.getType() == 1 && q.getType() == 14)
					|| (s.getType() == 1 && q.getType() == 18)
					|| (s.getType() == 1 && q.getType() == 20)) {
				tt1 = double_to_int(tt);
				tt = fact(tt1);
				stack.remove(index);
				index--;
			}
			/*
			 * 如果一元运算符左边是“-”，“-”左边是“#”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“(”，则“-”处理为负号
			 * .
			 * 或者一元运算符左边是“-”，“-”左边是“,”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“[”，则“-”处理为负号
			 * 。 计算后将“-”从栈中移除移除
			 */
			else if ((s.getType() == 2 && q.getType() == 16)
					|| (s.getType() == 2 && q.getType() == 14)
					|| (s.getType() == 2 && q.getType() == 18)
					|| (s.getType() == 2 && q.getType() == 20)) {
				tt1 = double_to_int(tt);
				tt = -1 * fact(tt1);
				stack.remove(index);
				index--;
			}
			// 否则不做任何处理，直接得出结果
			else {
				tt1 = double_to_int(tt);
				tt = fact(tt1);
			}
			break;
		case 10:
			if (tt < 0) {
				throw new SqrtException();
			}
			/*
			 * 如果一元运算符左边是“+”，“+”左边是“#”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“(”，则“+”处理为正号
			 * .
			 * 或者一元运算符左边是“+”，“+”左边是“,”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“[”，则“+”处理为正号
			 * 。 计算后将“+”从栈中移除移除
			 */
			else if ((s.getType() == 1 && q.getType() == 16)
					|| (s.getType() == 1 && q.getType() == 14)
					|| (s.getType() == 1 && q.getType() == 18)
					|| (s.getType() == 1 && q.getType() == 20)) {
				tt = Math.sqrt(tt);
				stack.remove(index);
				index--;
			}
			/*
			 * 如果一元运算符左边是“-”，“-”左边是“#”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“(”，则“-”处理为负号
			 * .
			 * 或者一元运算符左边是“-”，“-”左边是“,”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“[”，则“-”处理为负号
			 * 。 计算后将“-”从栈中移除移除
			 */
			else if ((s.getType() == 2 && q.getType() == 16)
					|| (s.getType() == 2 && q.getType() == 14)
					|| (s.getType() == 2 && q.getType() == 18)
					|| (s.getType() == 2 && q.getType() == 20)) {
				tt = -1 * Math.sqrt(tt);
				stack.remove(index);
				index--;
			}
			// 否则不做任何处理，直接得出结果
			else {
				tt = Math.sqrt(tt);
			}
			break;
		case 11:
			/*
			 * 如果一元运算符左边是“+”，“+”左边是“#”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“(”，则“+”处理为正号
			 * .
			 * 或者一元运算符左边是“+”，“+”左边是“,”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“[”，则“+”处理为正号
			 * 。 计算后将“+”从栈中移除移除
			 */
			if ((s.getType() == 1 && q.getType() == 16)
					|| (s.getType() == 1 && q.getType() == 14)
					|| (s.getType() == 1 && q.getType() == 18)
					|| (s.getType() == 1 && q.getType() == 20)) {
				tt = Math.sin(Math.toRadians(tt));
				stack.remove(index);
				index--;
			}
			/*
			 * 如果一元运算符左边是“-”，“-”左边是“#”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“(”，则“-”处理为负号
			 * .
			 * 或者一元运算符左边是“-”，“-”左边是“,”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“[”，则“-”处理为负号
			 * 。 计算后将“-”从栈中移除移除
			 */
			else if ((s.getType() == 2 && q.getType() == 16)
					|| (s.getType() == 2 && q.getType() == 14)
					|| (s.getType() == 2 && q.getType() == 18)
					|| (s.getType() == 2 && q.getType() == 20)) {
				tt = -1 * Math.sin(Math.toRadians(tt));
				stack.remove(index);
				index--;
			}
			// 否则不做任何处理，直接得出结果
			else {
				tt = Math.sin(Math.toRadians(tt));
			}
			break;
		case 12:
			/*
			 * 如果一元运算符左边是“+”，“+”左边是“#”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“(”，则“+”处理为正号
			 * .
			 * 或者一元运算符左边是“+”，“+”左边是“,”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“[”，则“+”处理为正号
			 * 。 计算后将“+”从栈中移除移除
			 */
			if ((s.getType() == 1 && q.getType() == 16)
					|| (s.getType() == 1 && q.getType() == 14)
					|| (s.getType() == 1 && q.getType() == 18)
					|| (s.getType() == 1 && q.getType() == 20)) {
				tt = Math.cos(Math.toRadians(tt));
				if (tt == 6.123233995736766E-17) {
					tt = 0;
				}
				stack.remove(index);
				index--;
			}
			/*
			 * 如果一元运算符左边是“-”，“-”左边是“#”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“(”，则“-”处理为负号
			 * .
			 * 或者一元运算符左边是“-”，“-”左边是“,”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“[”，则“-”处理为负号
			 * 。 计算后将“-”从栈中移除移除
			 */
			else if ((s.getType() == 2 && q.getType() == 16)
					|| (s.getType() == 2 && q.getType() == 14)
					|| (s.getType() == 2 && q.getType() == 18)
					|| (s.getType() == 2 && q.getType() == 20)) {
				tt = -1 * Math.cos(Math.toRadians(tt));
				if (tt == -6.123233995736766E-17) {
					tt = 0;
				}
				stack.remove(index);
				index--;
			}
			// 否则不做任何处理，直接得出结果
			else {
				tt = Math.cos(Math.toRadians(tt));
				if (tt == 6.123233995736766E-17) {
					tt = 0;
				}
			}
			break;
		// 求e的x次幂
		case 13:
			/*
			 * 如果一元运算符左边是“+”，“+”左边是“#”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“(”，则“+”处理为正号
			 * .
			 * 或者一元运算符左边是“+”，“+”左边是“,”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“[”，则“+”处理为正号
			 * 。 计算后将“+”从栈中移除移除
			 */
			if ((s.getType() == 1 && q.getType() == 16)
					|| (s.getType() == 1 && q.getType() == 14)
					|| (s.getType() == 1 && q.getType() == 18)
					|| (s.getType() == 1 && q.getType() == 20)) {
				tt = Math.exp(tt);
				stack.remove(index);
				index--;
			}
			/*
			 * 如果一元运算符左边是“-”，“-”左边是“#”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“(”，则“-”处理为负号
			 * .
			 * 或者一元运算符左边是“-”，“-”左边是“,”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“[”，则“-”处理为负号
			 * 。 计算后将“-”从栈中移除移除
			 */
			else if ((s.getType() == 2 && q.getType() == 16)
					|| (s.getType() == 2 && q.getType() == 14)
					|| (s.getType() == 2 && q.getType() == 18)
					|| (s.getType() == 2 && q.getType() == 20)) {
				tt = -1 * Math.exp(tt);
				stack.remove(index);
				index--;
			}
			// 否则不做任何处理，直接得出结果
			else {
				tt = Math.exp(tt);
			}
			break;
		case 27:
			if (tt < -1 || tt > 1) {
				throw new DomainOfArcsinOutOfBoundsException();
			}
			/*
			 * 如果一元运算符左边是“+”，“+”左边是“#”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“(”，则“+”处理为正号
			 * .
			 * 或者一元运算符左边是“+”，“+”左边是“,”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“[”，则“+”处理为正号
			 * 。 计算后将“+”从栈中移除移除
			 */
			else if ((s.getType() == 1 && q.getType() == 16)
					|| (s.getType() == 1 && q.getType() == 14)
					|| (s.getType() == 1 && q.getType() == 18)
					|| (s.getType() == 1 && q.getType() == 20)) {
				tt = Math.asin(tt);
				stack.remove(index);
				index--;
			}
			/*
			 * 如果一元运算符左边是“-”，“-”左边是“#”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“(”，则“-”处理为负号
			 * .
			 * 或者一元运算符左边是“-”，“-”左边是“,”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“[”，则“-”处理为负号
			 * 。 计算后将“-”从栈中移除移除
			 */
			else if ((s.getType() == 2 && q.getType() == 16)
					|| (s.getType() == 2 && q.getType() == 14)
					|| (s.getType() == 2 && q.getType() == 18)
					|| (s.getType() == 2 && q.getType() == 20)) {
				tt = -1 * Math.asin(tt);
				stack.remove(index);
				index--;
			} else {
				tt = Math.asin(tt);
			}
			break;
		case 28:
			if (tt < -1 || tt > 1) {
				throw new DomainOfArccosOutOfBoundsException();
			}
			/*
			 * 如果一元运算符左边是“+”，“+”左边是“#”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“(”，则“+”处理为正号
			 * .
			 * 或者一元运算符左边是“+”，“+”左边是“,”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“[”，则“+”处理为正号
			 * 。 计算后将“+”从栈中移除移除
			 */
			else if ((s.getType() == 1 && q.getType() == 16)
					|| (s.getType() == 1 && q.getType() == 14)
					|| (s.getType() == 1 && q.getType() == 18)
					|| (s.getType() == 1 && q.getType() == 20)) {
				tt = Math.acos(tt);
				stack.remove(index);
				index--;
			}
			/*
			 * 如果一元运算符左边是“-”，“-”左边是“#”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“(”，则“-”处理为负号
			 * .
			 * 或者一元运算符左边是“-”，“-”左边是“,”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“[”，则“-”处理为负号
			 * 。 计算后将“-”从栈中移除移除
			 */
			else if ((s.getType() == 2 && q.getType() == 16)
					|| (s.getType() == 2 && q.getType() == 14)
					|| (s.getType() == 2 && q.getType() == 18)
					|| (s.getType() == 2 && q.getType() == 20)) {
				tt = -1 * Math.acos(tt);
				stack.remove(index);
				index--;
			} else {
				tt = Math.acos(tt);
			}
			break;
		case 29:
			/*
			 * 如果一元运算符左边是“+”，“+”左边是“#”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“(”，则“+”处理为正号
			 * .
			 * 或者一元运算符左边是“+”，“+”左边是“,”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“[”，则“+”处理为正号
			 * 。 计算后将“+”从栈中移除移除
			 */
			if ((s.getType() == 1 && q.getType() == 16)
					|| (s.getType() == 1 && q.getType() == 14)
					|| (s.getType() == 1 && q.getType() == 18)
					|| (s.getType() == 1 && q.getType() == 20)) {
				tt = Math.atan(tt);
				stack.remove(index);
				index--;
			}
			/*
			 * 如果一元运算符左边是“-”，“-”左边是“#”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“(”，则“-”处理为负号
			 * .
			 * 或者一元运算符左边是“-”，“-”左边是“,”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“[”，则“-”处理为负号
			 * 。 计算后将“-”从栈中移除移除
			 */
			else if ((s.getType() == 2 && q.getType() == 16)
					|| (s.getType() == 2 && q.getType() == 14)
					|| (s.getType() == 2 && q.getType() == 18)
					|| (s.getType() == 2 && q.getType() == 20)) {
				tt = -1 * Math.atan(tt);
				stack.remove(index);
				index--;
			} else {
				tt = Math.atan(tt);
			}
			break;
		case 30:
			/*
			 * 如果一元运算符左边是“+”，“+”左边是“#”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“(”，则“+”处理为正号
			 * .
			 * 或者一元运算符左边是“+”，“+”左边是“,”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“[”，则“+”处理为正号
			 * 。 计算后将“+”从栈中移除移除
			 */
			if ((s.getType() == 1 && q.getType() == 16)
					|| (s.getType() == 1 && q.getType() == 14)
					|| (s.getType() == 1 && q.getType() == 18)
					|| (s.getType() == 1 && q.getType() == 20)) {
				tt = Math.sinh(Math.toRadians(tt));
				stack.remove(index);
				index--;
			}
			/*
			 * 如果一元运算符左边是“-”，“-”左边是“#”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“(”，则“-”处理为负号
			 * .
			 * 或者一元运算符左边是“-”，“-”左边是“,”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“[”，则“-”处理为负号
			 * 。 计算后将“-”从栈中移除移除
			 */
			else if ((s.getType() == 2 && q.getType() == 16)
					|| (s.getType() == 2 && q.getType() == 14)
					|| (s.getType() == 2 && q.getType() == 18)
					|| (s.getType() == 2 && q.getType() == 20)) {
				tt = -1 * Math.sinh(Math.toRadians(tt));
				stack.remove(index);
				index--;
			} else {
				tt = Math.sinh(Math.toRadians(tt));
			}
			break;
		case 31:
			/*
			 * 如果一元运算符左边是“+”，“+”左边是“#”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“(”，则“+”处理为正号
			 * .
			 * 或者一元运算符左边是“+”，“+”左边是“,”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“[”，则“+”处理为正号
			 * 。 计算后将“+”从栈中移除移除
			 */
			if ((s.getType() == 1 && q.getType() == 16)
					|| (s.getType() == 1 && q.getType() == 14)
					|| (s.getType() == 1 && q.getType() == 18)
					|| (s.getType() == 1 && q.getType() == 20)) {
				tt = Math.cosh(Math.toRadians(tt));
				stack.remove(index);
				index--;
			}
			/*
			 * 如果一元运算符左边是“-”，“-”左边是“#”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“(”，则“-”处理为负号
			 * .
			 * 或者一元运算符左边是“-”，“-”左边是“,”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“[”，则“-”处理为负号
			 * 。 计算后将“-”从栈中移除移除
			 */
			else if ((s.getType() == 2 && q.getType() == 16)
					|| (s.getType() == 2 && q.getType() == 14)
					|| (s.getType() == 2 && q.getType() == 18)
					|| (s.getType() == 2 && q.getType() == 20)) {
				tt = -1 * Math.cosh(Math.toRadians(tt));
				stack.remove(index);
				index--;
			} else {
				tt = Math.cosh(Math.toRadians(tt));
			}
			break;
		case 32:
			/*
			 * 如果一元运算符左边是“+”，“+”左边是“#”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“(”，则“+”处理为正号
			 * .
			 * 或者一元运算符左边是“+”，“+”左边是“,”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“[”，则“+”处理为正号
			 * 。 计算后将“+”从栈中移除移除
			 */
			if ((s.getType() == 1 && q.getType() == 16)
					|| (s.getType() == 1 && q.getType() == 14)
					|| (s.getType() == 1 && q.getType() == 18)
					|| (s.getType() == 1 && q.getType() == 20)) {
				tt = Math.tanh(Math.toRadians(tt));
				stack.remove(index);
				index--;
			}
			/*
			 * 如果一元运算符左边是“-”，“-”左边是“#”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“(”，则“-”处理为负号
			 * .
			 * 或者一元运算符左边是“-”，“-”左边是“,”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“[”，则“-”处理为负号
			 * 。 计算后将“-”从栈中移除移除
			 */
			else if ((s.getType() == 2 && q.getType() == 16)
					|| (s.getType() == 2 && q.getType() == 14)
					|| (s.getType() == 2 && q.getType() == 18)
					|| (s.getType() == 2 && q.getType() == 20)) {
				tt = -1 * Math.tanh(Math.toRadians(tt));
				stack.remove(index);
				index--;
			} else {
				tt = Math.tanh(Math.toRadians(tt));
			}
			break;
		// ln()运算
		case 34:
			if (tt < 0) {
				throw new NotLitterZero();
			}
			/*
			 * 如果一元运算符左边是“+”，“+”左边是“#”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“(”，则“+”处理为正号
			 * .
			 * 或者一元运算符左边是“+”，“+”左边是“,”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“[”，则“+”处理为正号
			 * 。 计算后将“+”从栈中移除移除
			 */
			else if ((s.getType() == 1 && q.getType() == 16)
					|| (s.getType() == 1 && q.getType() == 14)
					|| (s.getType() == 1 && q.getType() == 18)
					|| (s.getType() == 1 && q.getType() == 20)) {
				tt = Math.log(tt);
				stack.remove(index);
				index--;
			}
			/*
			 * 如果一元运算符左边是“-”，“-”左边是“#”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“(”，则“-”处理为负号
			 * .
			 * 或者一元运算符左边是“-”，“-”左边是“,”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“[”，则“-”处理为负号
			 * 。 计算后将“-”从栈中移除移除
			 */
			else if ((s.getType() == 2 && q.getType() == 16)
					|| (s.getType() == 2 && q.getType() == 14)
					|| (s.getType() == 2 && q.getType() == 18)
					|| (s.getType() == 2 && q.getType() == 20)) {
				tt = -1 * Math.log(tt);
				stack.remove(index);
				index--;
			} else {
				tt = Math.log(tt);
			}
			break;
		// log10()运算
		case 35:
			if (tt < 0) {
				throw new NotLitterZero();
			}
			/*
			 * 如果一元运算符左边是“+”，“+”左边是“#”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“(”，则“+”处理为正号
			 * .
			 * 或者一元运算符左边是“+”，“+”左边是“,”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“[”，则“+”处理为正号
			 * 。 计算后将“+”从栈中移除移除
			 */
			else if ((s.getType() == 1 && q.getType() == 16)
					|| (s.getType() == 1 && q.getType() == 14)
					|| (s.getType() == 1 && q.getType() == 18)
					|| (s.getType() == 1 && q.getType() == 20)) {
				tt = Math.log10(tt);
				stack.remove(index);
				index--;
			}
			/*
			 * 如果一元运算符左边是“-”，“-”左边是“#”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“(”，则“-”处理为负号
			 * .
			 * 或者一元运算符左边是“-”，“-”左边是“,”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“[”，则“-”处理为负号
			 * 。 计算后将“-”从栈中移除移除
			 */
			else if ((s.getType() == 2 && q.getType() == 16)
					|| (s.getType() == 2 && q.getType() == 14)
					|| (s.getType() == 2 && q.getType() == 18)
					|| (s.getType() == 2 && q.getType() == 20)) {
				tt = -1 * Math.log10(tt);
				stack.remove(index);
				index--;
			} else {
				tt = Math.log10(tt);
			}
			break;
		default:
			throw new ExpressionException();
		}
		temp.set_value(tt);
		push(temp);
	}

	/**
	 * 递归实现求n的阶乘
	 * 
	 * @param tt
	 * @return
	 */
	private double fact(double tt) {
		// TODO Auto-generated method stub
		double value = 0;
		if (tt == 1) {
			value = 1;
		} else {
			value = tt * fact(tt - 1);
		}
		return value;
	}

	/**
	 * 该函数实现对于一元函数运算中参数为x+y*z形式的运算，如tan(x+y*z)等一元运算
	 * 
	 * @throws ExpressionException
	 */
	public void unarys() throws ExpressionException {
		Token c1, c2, c3, c4, c5, c6, c7, c8, c9, s, q = null;
		double cc1, cc3, cc5, cc7, d1, d2 = 0, d3;
		c1 = stack.get(index);// 取操作数
		stack.remove(index);
		index--;
		cc1 = c1.get_value();
		c2 = stack.get(index);// 取运算符
		stack.remove(index);
		index--;
		c3 = stack.get(index);// 取操作数
		stack.remove(index);
		index--;
		cc3 = c3.get_value();
		c4 = stack.get(index);// 取运算符
		stack.remove(index);
		index--;
		c5 = stack.get(index);// 取操作数
		stack.remove(index);
		index--;
		cc5 = c5.get_value();

		c6 = stack.get(index);// 取左括号
		stack.remove(index);
		index--;
		c7 = stack.get(index);// 取运算符
		stack.remove(index);
		index--;

		s = stack.get(index);// 取"-"或“+”
		// 判断“-”左边是“#”或是“(”还是“[”
		if (index >= 1)
			q = stack.get(index - 1);
		// 处理一元函数运算中的参数
		// 参数为x+y*z
		if (c2.getType() == 3 && c4.getType() == 1) {
			d1 = cc3 * cc1;
			d2 = cc5 + d1;
		}
		// 参数为x-y*z
		else if (c2.getType() == 3 && c4.getType() == 2) {
			d1 = cc3 * cc1;
			d2 = cc5 - d1;
		}
		// 参数为x+y/z
		else if (c2.getType() == 4 && c4.getType() == 1) {
			d1 = cc3 / cc1;
			d2 = cc5 + d1;
		}
		// 参数为x-y/z
		else if (c2.getType() == 4 && c4.getType() == 2) {
			d1 = cc3 / cc1;
			d2 = cc5 - d1;
		}
		switch (c7.getType()) {
		case 6:
			if (d2 % (Math.PI / 2) == 0) {
				throw new TanException();
			}
			/*
			 * 如果一元运算符左边是“+”，“+”左边是“#”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“(”，则“+”处理为正号
			 * .
			 * 或者一元运算符左边是“+”，“+”左边是“,”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“[”，则“+”处理为正号
			 * 。 计算后将“+”从栈中移除移除
			 */
			else if ((s.getType() == 1 && q.getType() == 16)
					|| (s.getType() == 1 && q.getType() == 14)
					|| (s.getType() == 1 && q.getType() == 18)
					|| (s.getType() == 1 && q.getType() == 20)) {
				cc7 = Math.tan(Math.toRadians(d2));
				stack.remove(index);
				index--;
			}
			/*
			 * 如果一元运算符左边是“-”，“-”左边是“#”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“(”，则“-”处理为负号
			 * .
			 * 或者一元运算符左边是“-”，“-”左边是“,”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“[”，则“-”处理为负号
			 * 。 计算后将“-”从栈中移除移除
			 */
			else if ((s.getType() == 2 && q.getType() == 16)
					|| (s.getType() == 2 && q.getType() == 14)
					|| (s.getType() == 2 && q.getType() == 18)
					|| (s.getType() == 2 && q.getType() == 20)) {
				cc7 = -1 * Math.tan(Math.toRadians(d2));
				stack.remove(index);
				index--;
			}
			// 否则不做任何处理，直接得出结果
			else {
				cc7 = Math.tan(Math.toRadians(d2));
			}
			break;
		// 开3次方根运算
		case 7:
			/*
			 * 如果一元运算符左边是“+”，“+”左边是“#”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“(”，则“+”处理为正号
			 * .
			 * 或者一元运算符左边是“+”，“+”左边是“,”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“[”，则“+”处理为正号
			 * 。 计算后将“+”从栈中移除移除
			 */
			if ((s.getType() == 1 && q.getType() == 16)
					|| (s.getType() == 1 && q.getType() == 14)
					|| (s.getType() == 1 && q.getType() == 18)
					|| (s.getType() == 1 && q.getType() == 20)) {
				cc7 = Math.cbrt(d2);
				stack.remove(index);
				index--;
			}
			/*
			 * 如果一元运算符左边是“-”，“-”左边是“#”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“(”，则“-”处理为负号
			 * .
			 * 或者一元运算符左边是“-”，“-”左边是“,”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“[”，则“-”处理为负号
			 * 。 计算后将“-”从栈中移除移除
			 */
			else if ((s.getType() == 2 && q.getType() == 16)
					|| (s.getType() == 2 && q.getType() == 14)
					|| (s.getType() == 2 && q.getType() == 18)
					|| (s.getType() == 2 && q.getType() == 20)) {
				cc7 = -1 * Math.cbrt(d2);
				stack.remove(index);
				index--;
			}
			// 否则不做任何处理，直接得出结果
			else {
				cc7 = Math.cbrt(d2);
			}
			break;
		// 阶乘运算
		case 8:
			int tt1;
			/*
			 * 如果一元运算符左边是“+”，“+”左边是“#”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“(”，则“+”处理为正号
			 * .
			 * 或者一元运算符左边是“+”，“+”左边是“,”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“[”，则“+”处理为正号
			 * 。 计算后将“+”从栈中移除移除
			 */
			if ((s.getType() == 1 && q.getType() == 16)
					|| (s.getType() == 1 && q.getType() == 14)
					|| (s.getType() == 1 && q.getType() == 18)
					|| (s.getType() == 1 && q.getType() == 20)) {
				tt1 = double_to_int(d2);
				cc7 = fact(tt1);
				stack.remove(index);
				index--;
			}
			/*
			 * 如果一元运算符左边是“-”，“-”左边是“#”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“(”，则“-”处理为负号
			 * .
			 * 或者一元运算符左边是“-”，“-”左边是“,”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“[”，则“-”处理为负号
			 * 。 计算后将“-”从栈中移除移除
			 */
			else if ((s.getType() == 2 && q.getType() == 16)
					|| (s.getType() == 2 && q.getType() == 14)
					|| (s.getType() == 2 && q.getType() == 18)
					|| (s.getType() == 2 && q.getType() == 20)) {
				tt1 = double_to_int(d2);
				cc7 = -1 * fact(tt1);
				stack.remove(index);
				index--;
			}
			// 否则不做任何处理，直接得出结果
			else {
				tt1 = double_to_int(d2);
				cc7 = fact(tt1);
			}
			break;
		case 10:
			if (d2 < 0) {
				throw new SqrtException();
			}
			/*
			 * 如果一元运算符左边是“+”，“+”左边是“#”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“(”，则“+”处理为正号
			 * .
			 * 或者一元运算符左边是“+”，“+”左边是“,”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“[”，则“+”处理为正号
			 * 。 计算后将“+”从栈中移除移除
			 */
			else if ((s.getType() == 1 && q.getType() == 16)
					|| (s.getType() == 1 && q.getType() == 14)
					|| (s.getType() == 1 && q.getType() == 18)
					|| (s.getType() == 1 && q.getType() == 20)) {
				cc7 = Math.sqrt(d2);
				stack.remove(index);
				index--;
			}
			/*
			 * 如果一元运算符左边是“-”，“-”左边是“#”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“(”，则“-”处理为负号
			 * .
			 * 或者一元运算符左边是“-”，“-”左边是“,”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“[”，则“-”处理为负号
			 * 。 计算后将“-”从栈中移除移除
			 */
			else if ((s.getType() == 2 && q.getType() == 16)
					|| (s.getType() == 2 && q.getType() == 14)
					|| (s.getType() == 2 && q.getType() == 18)
					|| (s.getType() == 2 && q.getType() == 20)) {
				cc7 = -1 * Math.sqrt(d2);
				stack.remove(index);
				index--;
			}
			// 否则不做任何处理，直接得出结果
			else {
				cc7 = Math.sqrt(d2);
			}
			break;
		case 11:
			/*
			 * 如果一元运算符左边是“+”，“+”左边是“#”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“(”，则“+”处理为正号
			 * .
			 * 或者一元运算符左边是“+”，“+”左边是“,”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“[”，则“+”处理为正号
			 * 。 计算后将“+”从栈中移除移除
			 */
			if ((s.getType() == 1 && q.getType() == 16)
					|| (s.getType() == 1 && q.getType() == 14)
					|| (s.getType() == 1 && q.getType() == 18)
					|| (s.getType() == 1 && q.getType() == 20)) {
				cc7 = Math.sin(Math.toRadians(d2));
				stack.remove(index);
				index--;
			}
			/*
			 * 如果一元运算符左边是“-”，“-”左边是“#”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“(”，则“-”处理为负号
			 * .
			 * 或者一元运算符左边是“-”，“-”左边是“,”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“[”，则“-”处理为负号
			 * 。 计算后将“-”从栈中移除移除
			 */
			else if ((s.getType() == 2 && q.getType() == 16)
					|| (s.getType() == 2 && q.getType() == 14)
					|| (s.getType() == 2 && q.getType() == 18)
					|| (s.getType() == 2 && q.getType() == 20)) {
				cc7 = -1 * Math.sin(Math.toRadians(d2));
				stack.remove(index);
				index--;
			}
			// 否则不做任何处理，直接得出结果
			else {
				cc7 = Math.sin(Math.toRadians(d2));
			}
			break;
		case 12:
			/*
			 * 如果一元运算符左边是“+”，“+”左边是“#”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“(”，则“+”处理为正号
			 * .
			 * 或者一元运算符左边是“+”，“+”左边是“,”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“[”，则“+”处理为正号
			 * 。 计算后将“+”从栈中移除移除
			 */
			if ((s.getType() == 1 && q.getType() == 16)
					|| (s.getType() == 1 && q.getType() == 14)
					|| (s.getType() == 1 && q.getType() == 18)
					|| (s.getType() == 1 && q.getType() == 20)) {
				cc7 = Math.cos(Math.toRadians(d2));
				if (cc7 == 6.123233995736766E-17) {
					cc7 = 0;
				}
				stack.remove(index);
				index--;
			}
			/*
			 * 如果一元运算符左边是“-”，“-”左边是“#”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“(”，则“-”处理为负号
			 * .
			 * 或者一元运算符左边是“-”，“-”左边是“,”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“[”，则“-”处理为负号
			 * 。 计算后将“-”从栈中移除移除
			 */
			else if ((s.getType() == 2 && q.getType() == 16)
					|| (s.getType() == 2 && q.getType() == 14)
					|| (s.getType() == 2 && q.getType() == 18)
					|| (s.getType() == 2 && q.getType() == 20)) {
				cc7 = -1 * Math.cos(Math.toRadians(d2));
				if (cc7 == -6.123233995736766E-17) {
					cc7 = 0;
				}
				stack.remove(index);
				index--;
			}
			// 否则不做任何处理，直接得出结果
			else {
				cc7 = Math.cos(Math.toRadians(d2));
				if (cc7 == 6.123233995736766E-17) {
					cc7 = 0;
				}
			}
			break;
		// 求e的x次幂
		case 13:
			/*
			 * 如果一元运算符左边是“+”，“+”左边是“#”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“(”，则“+”处理为正号
			 * .
			 * 或者一元运算符左边是“+”，“+”左边是“,”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“[”，则“+”处理为正号
			 * 。 计算后将“+”从栈中移除移除
			 */
			if ((s.getType() == 1 && q.getType() == 16)
					|| (s.getType() == 1 && q.getType() == 14)
					|| (s.getType() == 1 && q.getType() == 18)
					|| (s.getType() == 1 && q.getType() == 20)) {
				cc7 = Math.exp(d2);
				stack.remove(index);
				index--;
			}
			/*
			 * 如果一元运算符左边是“-”，“-”左边是“#”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“(”，则“-”处理为负号
			 * .
			 * 或者一元运算符左边是“-”，“-”左边是“,”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“[”，则“-”处理为负号
			 * 。 计算后将“-”从栈中移除移除
			 */
			else if ((s.getType() == 2 && q.getType() == 16)
					|| (s.getType() == 2 && q.getType() == 14)
					|| (s.getType() == 2 && q.getType() == 18)
					|| (s.getType() == 2 && q.getType() == 20)) {
				cc7 = -1 * Math.exp(d2);
				stack.remove(index);
				index--;
			}
			// 否则不做任何处理，直接得出结果
			else {
				cc7 = Math.exp(d2);
			}
			break;
		case 27:
			if (d2 < -1 || d2 > 1) {
				throw new DomainOfArcsinOutOfBoundsException();
			}
			/*
			 * 如果一元运算符左边是“+”，“+”左边是“#”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“(”，则“+”处理为正号
			 * .
			 * 或者一元运算符左边是“+”，“+”左边是“,”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“[”，则“+”处理为正号
			 * 。 计算后将“+”从栈中移除移除
			 */
			else if ((s.getType() == 1 && q.getType() == 16)
					|| (s.getType() == 1 && q.getType() == 14)
					|| (s.getType() == 1 && q.getType() == 18)
					|| (s.getType() == 1 && q.getType() == 20)) {
				cc7 = Math.asin(d2);
				stack.remove(index);
				index--;
			}
			/*
			 * 如果一元运算符左边是“-”，“-”左边是“#”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“(”，则“-”处理为负号
			 * .
			 * 或者一元运算符左边是“-”，“-”左边是“,”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“[”，则“-”处理为负号
			 * 。 计算后将“-”从栈中移除移除
			 */
			else if ((s.getType() == 2 && q.getType() == 16)
					|| (s.getType() == 2 && q.getType() == 14)
					|| (s.getType() == 2 && q.getType() == 18)
					|| (s.getType() == 2 && q.getType() == 20)) {
				cc7 = -1 * Math.asin(d2);
				stack.remove(index);
				index--;
			} else {
				cc7 = Math.asin(d2);
			}
			break;
		case 28:
			if (d2 < -1 || d2 > 1) {
				throw new DomainOfArccosOutOfBoundsException();
			}
			/*
			 * 如果一元运算符左边是“+”，“+”左边是“#”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“(”，则“+”处理为正号
			 * .
			 * 或者一元运算符左边是“+”，“+”左边是“,”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“[”，则“+”处理为正号
			 * 。 计算后将“+”从栈中移除移除
			 */
			else if ((s.getType() == 1 && q.getType() == 16)
					|| (s.getType() == 1 && q.getType() == 14)
					|| (s.getType() == 1 && q.getType() == 18)
					|| (s.getType() == 1 && q.getType() == 20)) {
				cc7 = Math.acos(d2);
				stack.remove(index);
				index--;
			}
			/*
			 * 如果一元运算符左边是“-”，“-”左边是“#”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“(”，则“-”处理为负号
			 * .
			 * 或者一元运算符左边是“-”，“-”左边是“,”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“[”，则“-”处理为负号
			 * 。 计算后将“-”从栈中移除移除
			 */
			else if ((s.getType() == 2 && q.getType() == 16)
					|| (s.getType() == 2 && q.getType() == 14)
					|| (s.getType() == 2 && q.getType() == 18)
					|| (s.getType() == 2 && q.getType() == 20)) {
				cc7 = -1 * Math.acos(d2);
				stack.remove(index);
				index--;
			} else {
				cc7 = Math.acos(d2);
			}
			break;
		case 29:
			/*
			 * 如果一元运算符左边是“+”，“+”左边是“#”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“(”，则“+”处理为正号
			 * .
			 * 或者一元运算符左边是“+”，“+”左边是“,”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“[”，则“+”处理为正号
			 * 。 计算后将“+”从栈中移除移除
			 */
			if ((s.getType() == 1 && q.getType() == 16)
					|| (s.getType() == 1 && q.getType() == 14)
					|| (s.getType() == 1 && q.getType() == 18)
					|| (s.getType() == 1 && q.getType() == 20)) {
				cc7 = Math.atan(d2);
				stack.remove(index);
				index--;
			}
			/*
			 * 如果一元运算符左边是“-”，“-”左边是“#”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“(”，则“-”处理为负号
			 * .
			 * 或者一元运算符左边是“-”，“-”左边是“,”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“[”，则“-”处理为负号
			 * 。 计算后将“-”从栈中移除移除
			 */
			else if ((s.getType() == 2 && q.getType() == 16)
					|| (s.getType() == 2 && q.getType() == 14)
					|| (s.getType() == 2 && q.getType() == 18)
					|| (s.getType() == 2 && q.getType() == 20)) {
				cc7 = -1 * Math.atan(d2);
				stack.remove(index);
				index--;
			} else {
				cc7 = Math.atan(d2);
			}
			break;
		case 30:
			/*
			 * 如果一元运算符左边是“+”，“+”左边是“#”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“(”，则“+”处理为正号
			 * .
			 * 或者一元运算符左边是“+”，“+”左边是“,”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“[”，则“+”处理为正号
			 * 。 计算后将“+”从栈中移除移除
			 */
			if ((s.getType() == 1 && q.getType() == 16)
					|| (s.getType() == 1 && q.getType() == 14)
					|| (s.getType() == 1 && q.getType() == 18)
					|| (s.getType() == 1 && q.getType() == 20)) {
				cc7 = Math.sinh(Math.toRadians(d2));
				stack.remove(index);
				index--;
			}
			/*
			 * 如果一元运算符左边是“-”，“-”左边是“#”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“(”，则“-”处理为负号
			 * .
			 * 或者一元运算符左边是“-”，“-”左边是“,”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“[”，则“-”处理为负号
			 * 。 计算后将“-”从栈中移除移除
			 */
			else if ((s.getType() == 2 && q.getType() == 16)
					|| (s.getType() == 2 && q.getType() == 14)
					|| (s.getType() == 2 && q.getType() == 18)
					|| (s.getType() == 2 && q.getType() == 20)) {
				cc7 = -1 * Math.sinh(Math.toRadians(d2));
				stack.remove(index);
				index--;
			} else {
				cc7 = Math.sinh(Math.toRadians(d2));
			}
			break;
		case 31:
			/*
			 * 如果一元运算符左边是“+”，“+”左边是“#”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“(”，则“+”处理为正号
			 * .
			 * 或者一元运算符左边是“+”，“+”左边是“,”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“[”，则“+”处理为正号
			 * 。 计算后将“+”从栈中移除移除
			 */
			if ((s.getType() == 1 && q.getType() == 16)
					|| (s.getType() == 1 && q.getType() == 14)
					|| (s.getType() == 1 && q.getType() == 18)
					|| (s.getType() == 1 && q.getType() == 20)) {
				cc7 = Math.cosh(Math.toRadians(d2));
				stack.remove(index);
				index--;
			}
			/*
			 * 如果一元运算符左边是“-”，“-”左边是“#”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“(”，则“-”处理为负号
			 * .
			 * 或者一元运算符左边是“-”，“-”左边是“,”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“[”，则“-”处理为负号
			 * 。 计算后将“-”从栈中移除移除
			 */
			else if ((s.getType() == 2 && q.getType() == 16)
					|| (s.getType() == 2 && q.getType() == 14)
					|| (s.getType() == 2 && q.getType() == 18)
					|| (s.getType() == 2 && q.getType() == 20)) {
				cc7 = -1 * Math.cosh(Math.toRadians(d2));
				stack.remove(index);
				index--;
			} else {
				cc7 = Math.cosh(Math.toRadians(d2));
			}
			break;
		case 32:
			/*
			 * 如果一元运算符左边是“+”，“+”左边是“#”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“(”，则“+”处理为正号
			 * .
			 * 或者一元运算符左边是“+”，“+”左边是“,”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“[”，则“+”处理为正号
			 * 。 计算后将“+”从栈中移除移除
			 */
			if ((s.getType() == 1 && q.getType() == 16)
					|| (s.getType() == 1 && q.getType() == 14)
					|| (s.getType() == 1 && q.getType() == 18)
					|| (s.getType() == 1 && q.getType() == 20)) {
				cc7 = Math.tanh(Math.toRadians(d2));
				stack.remove(index);
				index--;
			}
			/*
			 * 如果一元运算符左边是“-”，“-”左边是“#”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“(”，则“-”处理为负号
			 * .
			 * 或者一元运算符左边是“-”，“-”左边是“,”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“[”，则“-”处理为负号
			 * 。 计算后将“-”从栈中移除移除
			 */
			else if ((s.getType() == 2 && q.getType() == 16)
					|| (s.getType() == 2 && q.getType() == 14)
					|| (s.getType() == 2 && q.getType() == 18)
					|| (s.getType() == 2 && q.getType() == 20)) {
				cc7 = -1 * Math.tanh(Math.toRadians(d2));
				stack.remove(index);
				index--;
			} else {
				cc7 = Math.tanh(Math.toRadians(d2));
			}
			break;
		// ln()运算
		case 34:
			if (d2 < 0) {
				throw new NotLitterZero();
			}
			/*
			 * 如果一元运算符左边是“+”，“+”左边是“#”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“(”，则“+”处理为正号
			 * .
			 * 或者一元运算符左边是“+”，“+”左边是“,”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“[”，则“+”处理为正号
			 * 。 计算后将“+”从栈中移除移除
			 */
			else if ((s.getType() == 1 && q.getType() == 16)
					|| (s.getType() == 1 && q.getType() == 14)
					|| (s.getType() == 1 && q.getType() == 18)
					|| (s.getType() == 1 && q.getType() == 20)) {
				cc7 = Math.log(d2);
				stack.remove(index);
				index--;
			}
			/*
			 * 如果一元运算符左边是“-”，“-”左边是“#”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“(”，则“-”处理为负号
			 * .
			 * 或者一元运算符左边是“-”，“-”左边是“,”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“[”，则“-”处理为负号
			 * 。 计算后将“-”从栈中移除移除
			 */
			else if ((s.getType() == 2 && q.getType() == 16)
					|| (s.getType() == 2 && q.getType() == 14)
					|| (s.getType() == 2 && q.getType() == 18)
					|| (s.getType() == 2 && q.getType() == 20)) {
				cc7 = -1 * Math.log(d2);
				stack.remove(index);
				index--;
			} else {
				cc7 = Math.log(d2);
			}
			break;
		// log10()运算
		case 35:
			if (d2 < 0) {
				throw new NotLitterZero();
			}
			/*
			 * 如果一元运算符左边是“+”，“+”左边是“#”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“(”，则“+”处理为正号
			 * .
			 * 或者一元运算符左边是“+”，“+”左边是“,”，则“+”处理为正号。或者一元运算符左边是“+”，“+”左边是“[”，则“+”处理为正号
			 * 。 计算后将“+”从栈中移除移除
			 */
			else if ((s.getType() == 1 && q.getType() == 16)
					|| (s.getType() == 1 && q.getType() == 14)
					|| (s.getType() == 1 && q.getType() == 18)
					|| (s.getType() == 1 && q.getType() == 20)) {
				cc7 = Math.log10(d2);
				stack.remove(index);
				index--;
			}
			/*
			 * 如果一元运算符左边是“-”，“-”左边是“#”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“(”，则“-”处理为负号
			 * .
			 * 或者一元运算符左边是“-”，“-”左边是“,”，则“-”处理为正号。或者一元运算符左边是“-”，“-”左边是“[”，则“-”处理为负号
			 * 。 计算后将“-”从栈中移除移除
			 */
			else if ((s.getType() == 2 && q.getType() == 16)
					|| (s.getType() == 2 && q.getType() == 14)
					|| (s.getType() == 2 && q.getType() == 18)
					|| (s.getType() == 2 && q.getType() == 20)) {
				cc7 = -1 * Math.log10(d2);
				stack.remove(index);
				index--;
			} else {
				cc7 = Math.log10(d2);
			}
			break;
		default:
			throw new ExpressionException();
		}
		c1.set_value(cc7);
		push(c1);
	}

	/**
	 * function()函数主要进行二元函数运算，pow(x,y);mod(x,y);yroot(x,y);log(x,nBase)
	 * 
	 * @throws ExpressionException
	 */
	public void function() throws ExpressionException {
		Token a2, a3, a4, a5, a6, a7, a8 = null;
		double aa2, aa4, aa6 = 0;
		a2 = stack.get(index);// 取第二个操作数
		stack.remove(index);
		index--;
		aa2 = a2.get_value();

		a3 = stack.get(index);// 取逗号
		stack.remove(index);
		index--;

		a4 = stack.get(index);// 取第一个操作数
		stack.remove(index);
		index--;
		aa4 = a4.get_value();

		a5 = stack.get(index);// 取左括号
		stack.remove(index);
		index--;

		a6 = stack.get(index);// 取运算符
		stack.remove(index);
		index--;
		// 取运算符右边的符号
		a7 = stack.get(index);
		// 取“-”或者“+”左边的符号
		if (index >= 1)
			a8 = stack.get(index - 1);

		switch (a6.getType()) {
		// x的y次幂,pow(x,y)
		case 5:
			// 函数开始会对“+”和“-”进行判断，判断其是运算符函数正负号，判断过程同一元运算
			if ((a7.getType() == 1 && a8.getType() == 16)
					|| (a7.getType() == 1 && a8.getType() == 14)
					|| (a7.getType() == 1 && a8.getType() == 18)
					|| (a7.getType() == 1 && a8.getType() == 20)) {
				aa6 = Math.pow(aa4, aa2);
				stack.remove(index);
				index--;
			} else if ((a7.getType() == 2 && a8.getType() == 16)
					|| (a7.getType() == 2 && a8.getType() == 14)
					|| (a7.getType() == 2 && a8.getType() == 18)
					|| (a7.getType() == 2 && a8.getType() == 20)) {
				aa6 = -1 * Math.pow(aa4, aa2);
				stack.remove(index);
				index--;
			} else {
				aa6 = Math.pow(aa4, aa2);
			}
			break;
		// 取模mod()
		case 9:
			if ((a7.getType() == 1 && a8.getType() == 16)
					|| (a7.getType() == 1 && a8.getType() == 14)
					|| (a7.getType() == 1 && a8.getType() == 18)
					|| (a7.getType() == 1 && a8.getType() == 20)) {
				aa6 = aa4 % aa2;
				stack.remove(index);
				index--;
			} else if ((a7.getType() == 2 && a8.getType() == 16)
					|| (a7.getType() == 2 && a8.getType() == 14)
					|| (a7.getType() == 2 && a8.getType() == 18)
					|| (a7.getType() == 1 && a8.getType() == 20)) {
				aa6 = -1 * aa4 % aa2;
				stack.remove(index);
				index--;
			} else {
				aa6 = aa4 % aa2;
			}
			break;
		// yroot()
		case 17:
			if (aa2 == 0) {
				throw new DividedByZeroException();
			}
			// 判断若要开偶次方根，则被开方数不能为负
			else if (aa2 % 2 == 0) {
				if (aa4 < 0) {
					throw new NotLitterThanZeroException();
				} else if ((a7.getType() == 1 && a8.getType() == 16)
						|| (a7.getType() == 1 && a8.getType() == 14)
						|| (a7.getType() == 1 && a8.getType() == 18)
						|| (a7.getType() == 1 && a8.getType() == 20)) {
					// 计算yroot(x,y)即x的y次方根
					aa6 = Math.pow(aa4, 1 / aa2);
					stack.remove(index);
					index--;
				} else if ((a7.getType() == 2 && a8.getType() == 16)
						|| (a7.getType() == 2 && a8.getType() == 14)
						|| (a7.getType() == 2 && a8.getType() == 18)
						|| (a7.getType() == 1 && a8.getType() == 20)) {
					aa6 = -1 * Math.pow(aa4, 1 / aa2);
					stack.remove(index);
					index--;
				} else {
					aa6 = Math.pow(aa4, 1 / aa2);
				}
			}
			// 判断若要开奇数次方根，则被开方数可以为负
			else if (aa2 % 2 != 0) {
				if ((a7.getType() == 1 && a8.getType() == 16)
						|| (a7.getType() == 1 && a8.getType() == 14)
						|| (a7.getType() == 1 && a8.getType() == 18)
						|| (a7.getType() == 1 && a8.getType() == 20)) {
					aa6 = Math.pow(aa4, 1 / aa2);
					stack.remove(index);
					index--;
				} else if ((a7.getType() == 2 && a8.getType() == 16)
						|| (a7.getType() == 2 && a8.getType() == 14)
						|| (a7.getType() == 2 && a8.getType() == 18)
						|| (a7.getType() == 1 && a8.getType() == 20)) {
					aa6 = -1 * Math.pow(aa4, 1 / aa2);
					stack.remove(index);
					index--;
				} else {
					aa6 = Math.pow(aa4, 1 / aa2);
				}
			}
			break;
		// log(x,nBase)
		case 36:
			if (aa2 <= 0 || aa4 <= 0 || aa2 == 1) {
				throw new LogException();
			} else if ((a7.getType() == 1 && a8.getType() == 16)
					|| (a7.getType() == 1 && a8.getType() == 14)
					|| (a7.getType() == 1 && a8.getType() == 18)
					|| (a7.getType() == 1 && a8.getType() == 20)) {
				// 计算log(x,nBase),即以nBase为底，求x的对数
				aa6 = Math.log(aa4) / Math.log(aa2);
				stack.remove(index);
				index--;
			} else if ((a7.getType() == 2 && a8.getType() == 16)
					|| (a7.getType() == 2 && a8.getType() == 14)
					|| (a7.getType() == 2 && a8.getType() == 18)
					|| (a7.getType() == 1 && a8.getType() == 20)) {
				aa6 = -1 * Math.log(aa4) / Math.log(aa2);
				stack.remove(index);
				index--;
			} else {
				aa6 = Math.log(aa4) / Math.log(aa2);
			}
			break;

		default:
			throw new ExpressionException();
		}
		a4.set_value(aa6);
		push(a4);

	}

	/**
	 * 该函数处理二元函数运算中，第二个操作数位a+b形式的情况 如pow(x,a+b);
	 * mod(x,a+b);yroot(x,a+b);log(x,a+b)
	 * 
	 * @throws ExpressionException
	 */
	public void function2() throws ExpressionException {
		Token b1, b2, b3, b4, b8, b9, b10, b11 = null;
		double bb1, bb3, bb5, bb9 = 0, bb11, bb13;
		b1 = stack.get(index);// 取操作数
		stack.remove(index);
		index--;
		bb1 = b1.get_value();
		b2 = stack.get(index);// 取运算符
		stack.remove(index);
		index--;
		b3 = stack.get(index);// 取操作数
		stack.remove(index);
		index--;
		bb3 = b3.get_value();
		b4 = stack.get(index);// 取逗号
		stack.remove(index);
		index--;
		b5 = stack.get(index);// 取操作数
		stack.remove(index);
		index--;
		bb5 = b5.get_value();

		b8 = stack.get(index);// 取括号
		stack.remove(index);
		index--;
		b9 = stack.get(index);// 取运算符
		stack.remove(index);
		index--;
		// 取运算符左边的“-”或者“+”
		b10 = stack.get(index);
		// 取“-”或“+”左边的“#”或者“(”或者“,”，用来判断是否需要把+，-当做正负号
		if (index >= 1)
			b11 = stack.get(index - 1);

		/*
		 * 处理二元函数中第二个参数为x+y或x-y或x/y或x*y 先对他们进行计算得出结果，使逗号两边都只有一个操作数，然后带入计算
		 */
		if (b2.getType() == 1) {
			bb9 = bb1 + bb3;
		} else if (b2.getType() == 2) {
			bb9 = bb3 - bb1;
		} else if (b2.getType() == 3) {
			bb9 = bb3 * bb1;
		} else if (b2.getType() == 4) {
			bb9 = bb3 / bb1;
		}
		bb11 = bb5;
		switch (b9.getType()) {
		// pow()
		case 5:
			if ((b10.getType() == 1 && b11.getType() == 14)
					|| (b10.getType() == 1 && b11.getType() == 16)
					|| (b10.getType() == 1 && b11.getType() == 18)
					|| (b10.getType() == 1 && b11.getType() == 20)) {
				bb13 = Math.pow(bb11, bb9);
				stack.remove(index);
				index--;
			} else if ((b10.getType() == 2 && b11.getType() == 14)
					|| (b10.getType() == 2 && b11.getType() == 16)
					|| (b10.getType() == 2 && b11.getType() == 18)
					|| (b10.getType() == 2 && b11.getType() == 20)) {
				bb13 = -1 * Math.pow(bb11, bb9);
				stack.remove(index);
				index--;
			} else {
				bb13 = Math.pow(bb11, bb9);
			}
			break;
		// mod()
		case 9:
			if ((b10.getType() == 1 && b11.getType() == 14)
					|| (b10.getType() == 1 && b11.getType() == 16)
					|| (b10.getType() == 1 && b11.getType() == 18)
					|| (b10.getType() == 1 && b11.getType() == 20)) {
				bb13 = bb11 % bb9;
				stack.remove(index);
				index--;
			} else if ((b10.getType() == 2 && b11.getType() == 14)
					|| (b10.getType() == 2 && b11.getType() == 16)
					|| (b10.getType() == 2 && b11.getType() == 18)
					|| (b10.getType() == 2 && b11.getType() == 20)) {
				bb13 = bb11 % bb9;
				stack.remove(index);
				index--;
			} else {
				bb13 = bb11 % bb9;
			}
			break;
		// yroot()
		case 17:
			if (bb9 == 0) {
				throw new DividedByZeroException();
			} else if (bb9 % 2 == 0) {
				if (bb11 < 0) {
					throw new NotLitterThanZeroException();
				} else if ((b10.getType() == 1 && b11.getType() == 14)
						|| (b10.getType() == 1 && b11.getType() == 16)
						|| (b10.getType() == 1 && b11.getType() == 18)
						|| (b10.getType() == 1 && b11.getType() == 20)) {
					bb13 = Math.pow(bb11, 1 / bb9);
					stack.remove(index);
					index--;
				} else if ((b10.getType() == 2 && b11.getType() == 14)
						|| (b10.getType() == 2 && b11.getType() == 16)
						|| (b10.getType() == 2 && b11.getType() == 18)
						|| (b10.getType() == 2 && b11.getType() == 20)) {
					bb13 = Math.pow(bb11, 1 / bb9);
					stack.remove(index);
					index--;
				} else {
					bb13 = Math.pow(bb11, 1 / bb9);
				}
			} else if ((b10.getType() == 1 && b11.getType() == 14)
					|| (b10.getType() == 1 && b11.getType() == 16)
					|| (b10.getType() == 1 && b11.getType() == 18)
					|| (b10.getType() == 1 && b11.getType() == 20)) {
				bb13 = Math.pow(bb11, 1 / bb9);
				stack.remove(index);
				index--;
			} else if ((b10.getType() == 2 && b11.getType() == 14)
					|| (b10.getType() == 2 && b11.getType() == 16)
					|| (b10.getType() == 2 && b11.getType() == 18)
					|| (b10.getType() == 2 && b11.getType() == 20)) {
				bb13 = Math.pow(bb11, 1 / bb9);
				stack.remove(index);
				index--;
			} else {
				bb13 = Math.pow(bb11, 1 / bb9);
			}
			break;
		// log(x,y)
		case 36:
			if (bb9 <= 0 || bb11 <= 0 || bb9 == 1) {
				throw new LogException();
			} else if ((b10.getType() == 1 && b11.getType() == 14)
					|| (b10.getType() == 1 && b11.getType() == 16)
					|| (b10.getType() == 1 && b11.getType() == 18)
					|| (b10.getType() == 1 && b11.getType() == 20)) {
				bb13 = Math.log(bb11) / Math.log(bb9);
				stack.remove(index);
				index--;
			} else if ((b10.getType() == 2 && b11.getType() == 14)
					|| (b10.getType() == 2 && b11.getType() == 16)
					|| (b10.getType() == 2 && b11.getType() == 18)
					|| (b10.getType() == 2 && b11.getType() == 20)) {
				bb13 = Math.log(bb11) / Math.log(bb9);
				stack.remove(index);
				index--;
			} else {
				bb13 = Math.log(bb11) / Math.log(bb9);
			}
			break;

		default:
			throw new ExpressionException();
		}
		b5.set_value(bb13);
		push(b5);
	}

	/**
	 * 处理二元函数运算中第二个参数为x-y*z或x+y*z或x-y/z或x+y/z的情况
	 * 
	 * @throws ExpressionException
	 */
	public void function3() throws ExpressionException {
		Token c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11 = null;
		double cc1, cc3, cc5, cc7, d1, d2 = 0, d3;
		c1 = stack.get(index);// 取操作数
		stack.remove(index);
		index--;
		cc1 = c1.get_value();
		c2 = stack.get(index);// 取运算符
		stack.remove(index);
		index--;
		c3 = stack.get(index);// 取操作数
		stack.remove(index);
		index--;
		cc3 = c3.get_value();
		c4 = stack.get(index);// 取运算符
		stack.remove(index);
		index--;
		c5 = stack.get(index);// 取操作数
		stack.remove(index);
		index--;
		cc5 = c5.get_value();

		c6 = stack.get(index);// 取,
		stack.remove(index);
		index--;
		c7 = stack.get(index);// 取操作数
		stack.remove(index);
		index--;
		cc7 = c7.get_value();
		c8 = stack.get(index);// 取左括号
		stack.remove(index);
		index--;
		c9 = stack.get(index);// 取运算符
		stack.remove(index);
		index--;

		c10 = stack.get(index);
		if (index >= 1)
			c11 = stack.get(index - 1);
		/*
		 * 处理形如pow(x,x-y*z) pow(x,x+y*z) pow(x,x-y/z) pow(x,x+y/z)的情况
		 * 处理过程为先对二元函数第二个参数x-y*z进行运算，得出结果，使逗号两边都只有一个数字，然后带入运算
		 */
		if (c2.getType() == 3 && c4.getType() == 1) {
			d1 = cc3 * cc1;
			d2 = cc5 + d1;
		} else if (c2.getType() == 3 && c4.getType() == 2) {
			d1 = cc3 * cc1;
			d2 = cc5 - d1;
		} else if (c2.getType() == 4 && c4.getType() == 1) {
			d1 = cc3 / cc1;
			d2 = cc5 + d1;
		} else if (c2.getType() == 4 && c4.getType() == 2) {
			d1 = cc3 / cc1;
			d2 = cc5 - d1;
		}
		switch (c9.getType()) {
		// pow()
		case 5:
			if ((c10.getType() == 1 && c11.getType() == 14)
					|| (c10.getType() == 1 && c11.getType() == 16)
					|| (c10.getType() == 1 && c11.getType() == 18)
					|| (c10.getType() == 1 && c11.getType() == 20)) {
				d3 = Math.pow(cc7, d2);
				stack.remove(index);
				index--;
			} else if ((c10.getType() == 2 && c11.getType() == 14)
					|| (c10.getType() == 2 && c11.getType() == 16)
					|| (c10.getType() == 2 && c11.getType() == 18)
					|| (c10.getType() == 2 && c11.getType() == 20)) {
				d3 = -1 * Math.pow(cc7, d2);
				stack.remove(index);
				index--;
			} else {
				d3 = Math.pow(cc7, d2);
			}
			break;
		// mod()
		case 9:
			if ((c10.getType() == 1 && c11.getType() == 14)
					|| (c10.getType() == 1 && c11.getType() == 16)
					|| (c10.getType() == 1 && c11.getType() == 18)
					|| (c10.getType() == 1 && c11.getType() == 20)) {
				d3 = cc7 % d2;
				stack.remove(index);
				index--;
			} else if ((c10.getType() == 2 && c11.getType() == 14)
					|| (c10.getType() == 2 && c11.getType() == 16)
					|| (c10.getType() == 2 && c11.getType() == 18)
					|| (c10.getType() == 2 && c11.getType() == 20)) {
				d3 = cc7 % d2;
				stack.remove(index);
				index--;
			} else {
				d3 = cc7 % d2;
			}
			break;
		// yroot()
		case 17:
			if (d2 == 0) {
				throw new DividedByZeroException();
			} else if (d2 % 2 == 0) {
				if (cc7 < 0) {
					throw new NotLitterThanZeroException();
				} else if ((c10.getType() == 1 && c11.getType() == 14)
						|| (c10.getType() == 1 && c11.getType() == 16)
						|| (c10.getType() == 1 && c11.getType() == 18)
						|| (c10.getType() == 1 && c11.getType() == 20)) {
					d3 = Math.pow(cc7, 1 / d2);
					stack.remove(index);
					index--;
				} else if ((c10.getType() == 2 && c11.getType() == 14)
						|| (c10.getType() == 2 && c11.getType() == 16)
						|| (c10.getType() == 2 && c11.getType() == 18)
						|| (c10.getType() == 2 && c11.getType() == 20)) {
					d3 = Math.pow(cc7, 1 / d2);
					stack.remove(index);
					index--;
				} else {
					d3 = Math.pow(cc7, 1 / d2);
				}
			} else if ((c10.getType() == 1 && c11.getType() == 14)
					|| (c10.getType() == 1 && c11.getType() == 16)
					|| (c10.getType() == 1 && c11.getType() == 18)
					|| (c10.getType() == 1 && c11.getType() == 20)) {
				d3 = Math.pow(cc7, 1 / d2);
				stack.remove(index);
				index--;
			} else if ((c10.getType() == 2 && c11.getType() == 14)
					|| (c10.getType() == 2 && c11.getType() == 16)
					|| (c10.getType() == 2 && c11.getType() == 18)
					|| (c10.getType() == 2 && c11.getType() == 20)) {
				d3 = Math.pow(cc7, 1 / d2);
				stack.remove(index);
				index--;
			} else {
				d3 = Math.pow(cc7, 1 / d2);
			}
			break;
		// log()
		case 36:
			if (cc7 <= 0 || d2 <= 0 || d2 == 1) {
				throw new LogException();
			} else if ((c10.getType() == 1 && c11.getType() == 14)
					|| (c10.getType() == 1 && c11.getType() == 16)
					|| (c10.getType() == 1 && c11.getType() == 18)
					|| (c10.getType() == 1 && c11.getType() == 20)) {
				d3 = Math.log(cc7) / Math.log(d2);
				stack.remove(index);
				index--;
			} else if ((c10.getType() == 2 && c11.getType() == 14)
					|| (c10.getType() == 2 && c11.getType() == 16)
					|| (c10.getType() == 2 && c11.getType() == 18)
					|| (c10.getType() == 2 && c11.getType() == 20)) {
				d3 = Math.log(cc7) / Math.log(d2);
				stack.remove(index);
				index--;
			} else {
				d3 = Math.log(cc7) / Math.log(d2);
			}
			break;

		default:
			throw new ExpressionException();
		}
		c7.set_value(d3);
		push(c7);
	}

	/**
	 * 该函数主要实现集合函数 sum([]) avg([]) varp([]) var([]) stdevp([]) stdev([])
	 * 并且集合运算的参数至少为两个，对于单个参数的集合运算进行单独处理
	 * 
	 * @throws ExpressionException
	 */
	public void gather() throws ExpressionException {
		Token f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12 = null;
		double ff1, ff2, ff3 = 0, ff4, ff5, ff6, ff7, ff8;
		int ff9;
		f1 = stack.get(index);// 取第二个操作数
		stack.remove(index);
		index--;
		ff1 = f1.get_value();
		f2 = stack.get(index);// 取,
		stack.remove(index);
		index--;
		f3 = stack.get(index);// 取第一个操作数
		stack.remove(index);
		index--;
		ff2 = f3.get_value();
		f4 = stack.get(index);// 取[
		stack.remove(index);
		index--;
		f5 = stack.get(index);// 取(
		stack.remove(index);
		index--;
		f6 = stack.get(index);// 取sum,avg,varp,var,stdevp,stdev运算符
		stack.remove(index);
		index--;

		f11 = stack.get(index);
		if (index >= 1)
			f12 = stack.get(index - 1);

		/*
		 * 通过之前的归约操作使逗号两边都只剩下一个操作数，这样只需对逗号两边的两个数进行计算即可
		 */
		switch (f6.getType()) {

		// 对sum([])运算，直接将两个数相加即可
		case 19:
			if ((f11.getType() == 1 && f12.getType() == 14)
					|| (f11.getType() == 1 && f12.getType() == 16)
					|| (f11.getType() == 1 && f12.getType() == 18)
					|| (f11.getType() == 1 && f12.getType() == 20)) {
				ff3 = ff1 + ff2;
				stack.remove(index);
				index--;
			} else if ((f11.getType() == 2 && f12.getType() == 14)
					|| (f11.getType() == 2 && f12.getType() == 16)
					|| (f11.getType() == 2 && f12.getType() == 18)
					|| (f11.getType() == 2 && f12.getType() == 20)) {
				ff3 = -1 * (ff1 + ff2);
				stack.remove(index);
				index--;
			} else {
				ff3 = ff1 + ff2;
			}
			// 清空进行由于进行sum集合运算而进入stack1栈的数
			if (index1 >= 1) {
				for (int k = index1; k > 0; k--) {
					f10 = stack1.get(index1);
					stack1.remove(index1);
					index1--;
				}
			}
			// 进行一些初始化操作，以免影响之后的计算结果
			count = 2;
			tt5 = 0;
			push3(tt5);
			break;

		// avg([]) 求出两个数的和然后除以之前统计的总个数即可
		case 22:
			if ((f11.getType() == 1 && f12.getType() == 14)
					|| (f11.getType() == 1 && f12.getType() == 16)
					|| (f11.getType() == 1 && f12.getType() == 18)
					|| (f11.getType() == 1 && f12.getType() == 20)) {
				ff3 = (ff1 + ff2) / count;
				stack.remove(index);
				index--;
			} else if ((f11.getType() == 2 && f12.getType() == 14)
					|| (f11.getType() == 2 && f12.getType() == 16)
					|| (f11.getType() == 2 && f12.getType() == 18)
					|| (f11.getType() == 2 && f12.getType() == 20)) {
				ff3 = -1 * ((ff1 + ff2) / count);
				stack.remove(index);
				index--;
			} else {
				ff3 = (ff1 + ff2) / count;
			}
			// 清空进行集合运算进stack1栈的数
			if (index1 >= 1) {
				for (int k = index1; k > 0; k--) {
					f10 = stack1.get(index1);
					stack1.remove(index1);
					index1--;
				}
			}
			tt5 = 0;
			push3(tt5);
			/*
			 * 对于嵌套运算，每次内层计算结束后，将之前存在stack2的count值取出，给它的上一个集合运算，这样能保证
			 * 内层集合运算结束后上一个集合运算对应的count值是正确的，对于多层嵌套进行递归调用即可
			 */
			if (stack2.isEmpty() == false) {
				ff9 = stack2.pop();
				count = ff9;
				System.out.println();
			} else {
				count = 2;
			}

			break;
		/*
		 * varp([])运算 对于varp运算 使用的公式是：varp = (x1*x1+x2*x2+...+xn*xn - 2*sum*avg
		 * + n * avg * avg)/n 使用该公式时需要计算得到逗号两边归约后每个数的平方和，以及他们的平均值和总和。
		 */
		case 23:
			if (count > 2) {
				// 由于每次进行归约操作会计算除了两边数字的平方和，并把他们存在stack3中，如varp([1,2,3,4,5]),栈顶存储的即为：2*2+3*3+4*4
				// 因此取得栈顶元素即为中间数的平方和
				if (stack3.isEmpty() == false) {
					tt5 = stack3.pop();
				} else {
					break;
				}
				// 通过for循环将stack1中除index1等于1和0的其他元素都清空
				for (int j = index1; j > 1; j--) {
					f9 = stack1.get(index1);
					stack1.remove(index1);
					index1--;
				}
				// 每次计算时在归约操作时会把集合运算中第一个数存储在stack1中，如varp([1,2,3,4,5])即将
				// 1存在stack1中，对应的index1等于1.
				f7 = stack1.get(index1);
				stack1.remove(index1);
				index1--;
				// 取得集合运算中的第一个操作数
				tt6 = f7.get_value();
				// 计算得到各个数的平方和
				ff4 = tt5 + tt6 * tt6 + ff1 * ff1;
				ff5 = ff1 + ff2;// sum
				ff6 = (ff1 + ff2) / count;// avg
				// 带入计算方差的公式，得出结果
				if ((f11.getType() == 1 && f12.getType() == 14)
						|| (f11.getType() == 1 && f12.getType() == 16)
						|| (f11.getType() == 1 && f12.getType() == 18)
						|| (f11.getType() == 1 && f12.getType() == 20)) {
					// 通过公式varp = (x1*x1+x2*x2+...+xn*xn - 2*sum*avg + n * avg *
					// avg)/n
					// 计算方差
					ff3 = (ff4 - 2 * ff5 * ff6 + count * ff6 * ff6) / count;
					stack.remove(index);
					index--;
				} else if ((f11.getType() == 2 && f12.getType() == 14)
						|| (f11.getType() == 2 && f12.getType() == 16)
						|| (f11.getType() == 2 && f12.getType() == 18)
						|| (f11.getType() == 2 && f12.getType() == 20)) {
					ff3 = -1
							* ((ff4 - 2 * ff5 * ff6 + count * ff6 * ff6) / count);
					stack.remove(index);
					index--;
				} else {
					ff3 = (ff4 - 2 * ff5 * ff6 + count * ff6 * ff6) / count;
				}
				if (stack2.isEmpty() == false) {
					ff9 = stack2.pop();
					count = ff9;
					System.out.println();
				} else {
					count = 2;
				}
				/*
				 * 处理集合嵌套运算时，内层运算结束后将上一层的之前计算过的中间的数的平方和从stack5中取出，并放到
				 * stack3中，以保证内层运算结束后他的上一层运算能得到正确的中间的数的平方和，而不受内层嵌套的影响
				 * 如varp([1,2,3
				 * ,4,varp([1,2,3])])内层的varp([1,2,3])计算完成后将2*2+3*3+4*
				 * 4还给外层的varp运算
				 */
				if (stack5.isEmpty() == false) {
					tt5 = stack5.pop();
					push3(tt5);
					System.out.println();
				} else {
					tt5 = 0;
					push3(tt5);
				}
				/*
				 * 把存在stack6中的第一个数取出，因为嵌套运算时都会把它从stack1中取出存储到stack6中
				 * 所以内层嵌套运算结束后把它重新赋给外层运算
				 * ，如varp([1,2,3,4,varp([1,2,3])])内层的varp([1,2,3])
				 * 把操作数1先存在stack1中
				 * ，然后在遇到varp([1,2,3])时把它取出存在stack6中，待varp([1,2,3])计算
				 * 完成后取出放到stack1中
				 */
				if (stack6.isEmpty() == false) {
					tt6 = stack6.pop();
					f1.set_value(tt6);
					push1(f1);
					System.out.println();
				} else {
					break;
				}
			} else {
				/*
				 * 如果集合运算中操作数的个数小于等于2，则执行以下操作，因为只有两个操作数时将不进行规约操作，而直接将两个
				 * 操作数进行运算即可
				 */
				ff6 = (ff1 + ff2) / count;// avg
				if ((f11.getType() == 1 && f12.getType() == 14)
						|| (f11.getType() == 1 && f12.getType() == 16)
						|| (f11.getType() == 1 && f12.getType() == 18)
						|| (f11.getType() == 1 && f12.getType() == 20)) {
					ff3 = ((ff1 - ff6) * (ff1 - ff6) + (ff2 - ff6)
							* (ff2 - ff6))
							/ count;
					stack.remove(index);
					index--;
				} else if ((f11.getType() == 2 && f12.getType() == 14)
						|| (f11.getType() == 2 && f12.getType() == 16)
						|| (f11.getType() == 2 && f12.getType() == 18)
						|| (f11.getType() == 2 && f12.getType() == 20)) {
					ff3 = -1
							* (((ff1 - ff6) * (ff1 - ff6) + (ff2 - ff6)
									* (ff2 - ff6)) / count);
					stack.remove(index);
					index--;
				} else {
					ff3 = ((ff1 - ff6) * (ff1 - ff6) + (ff2 - ff6)
							* (ff2 - ff6))
							/ count;
				}
				// 对于嵌套运算，取出count
				if (stack2.isEmpty() == false) {
					ff9 = stack2.pop();
					count = ff9;
					System.out.println();
				} else {
					// 还原为初值
					count = 2;
				}
				// 对于嵌套运算取出中间数的平方和
				if (stack5.isEmpty() == false) {
					tt5 = stack5.pop();
					push3(tt5);
					System.out.println();
				} else {
					// 初始化操作
					tt5 = 0;
					push3(tt5);
				}
				// 对于嵌套运算，取出第一个数的值
				if (stack6.isEmpty() == false) {
					tt6 = stack6.pop();
					f1.set_value(tt6);
					push1(f1);
					System.out.println();
				} else {
					break;
				}
			}
			break;
		/*
		 * var([])运算
		 */
		case 24:
			if (count > 2) {
				// 获取栈顶的中间值得平方和
				if (stack3.isEmpty() == false) {
					tt5 = stack3.pop();
					System.out.println();
				} else {
					break;
				}
				for (int j = index1; j > 1; j--) {
					f9 = stack1.get(index1);
					stack1.remove(index1);
					index1--;
				}
				// 获取第一个数的值
				f7 = stack1.get(index1);
				stack1.remove(index1);
				index1--;
				tt6 = f7.get_value();
				ff4 = tt5 + tt6 * tt6 + ff1 * ff1;
				ff5 = ff1 + ff2;// sum
				ff6 = (ff1 + ff2) / count;// avg
				if ((f11.getType() == 1 && f12.getType() == 14)
						|| (f11.getType() == 1 && f12.getType() == 16)
						|| (f11.getType() == 1 && f12.getType() == 18)
						|| (f11.getType() == 1 && f12.getType() == 20)) {
					ff3 = (ff4 - 2 * ff5 * ff6 + count * ff6 * ff6)
							/ (count - 1);
					stack.remove(index);
					index--;
				} else if ((f11.getType() == 2 && f12.getType() == 14)
						|| (f11.getType() == 2 && f12.getType() == 16)
						|| (f11.getType() == 2 && f12.getType() == 18)
						|| (f11.getType() == 2 && f12.getType() == 20)) {
					ff3 = -1
							* ((ff4 - 2 * ff5 * ff6 + count * ff6 * ff6) / (count - 1));
					stack.remove(index);
					index--;
				} else {
					// var([])的计算公式为：var = (x1*x1+x2*x2+...+xn*xn - 2*sum*avg +
					// n * avg * avg)/n-1
					ff3 = (ff4 - 2 * ff5 * ff6 + count * ff6 * ff6)
							/ (count - 1);
				}
				// 对于嵌套运算取出count
				if (stack2.isEmpty() == false) {
					ff9 = stack2.pop();
					count = ff9;
					System.out.println();
				} else {
					// 初始化操作
					count = 2;
				}
				// 对于嵌套运算取出中间数的平方和
				if (stack5.isEmpty() == false) {
					tt5 = stack5.pop();
					push3(tt5);
					System.out.println();
				} else {
					// 如果没有进行嵌套运算则每次运算结束后要进行初始化操作
					tt5 = 0;
					push3(tt5);
				}
				// 对于嵌套运算取出第一个数的值
				if (stack6.isEmpty() == false) {
					tt6 = stack6.pop();
					f1.set_value(tt6);
					push1(f1);
					System.out.println();
				} else {
					break;
				}
			} else
			// 如果集合运算中只有小于两个操作数时则进行如下操作
			{
				ff6 = (ff1 + ff2) / count;// avg
				if ((f11.getType() == 1 && f12.getType() == 14)
						|| (f11.getType() == 1 && f12.getType() == 16)
						|| (f11.getType() == 1 && f12.getType() == 18)
						|| (f11.getType() == 1 && f12.getType() == 20)) {
					ff3 = ((ff1 - ff6) * (ff1 - ff6) + (ff2 - ff6)
							* (ff2 - ff6))
							/ (count - 1);
					stack.remove(index);
					index--;
				} else if ((f11.getType() == 2 && f12.getType() == 14)
						|| (f11.getType() == 2 && f12.getType() == 16)
						|| (f11.getType() == 2 && f12.getType() == 18)
						|| (f11.getType() == 2 && f12.getType() == 20)) {
					ff3 = -1
							* (((ff1 - ff6) * (ff1 - ff6) + (ff2 - ff6)
									* (ff2 - ff6)) / (count - 1));
					stack.remove(index);
					index--;
				} else {
					ff3 = ((ff1 - ff6) * (ff1 - ff6) + (ff2 - ff6)
							* (ff2 - ff6))
							/ (count - 1);
				}
				// 对于嵌套运算取出之前的count值
				if (stack2.isEmpty() == false) {
					ff9 = stack2.pop();
					count = ff9;
					System.out.println();
				} else {
					// 对于非嵌套运算每次运算结束后进行初始化操作
					count = 2;
				}
				// 对于嵌套运算取出位于栈顶的中间数的平方和
				if (stack5.isEmpty() == false) {
					tt5 = stack5.pop();
					push3(tt5);
					System.out.println();
				} else {
					// 对于非嵌套运算每次运算结束后进行初始化操作
					tt5 = 0;
					push3(tt5);
				}
				// 对于嵌套运算取出位于栈顶的第一个数
				if (stack6.isEmpty() == false) {
					tt6 = stack6.pop();
					f1.set_value(tt6);
					push1(f1);
					System.out.println();
				} else {
					break;
				}
			}
			break;
		/*
		 * stdvep([])运算
		 */
		case 25:
			if (count > 2) {
				if (stack3.isEmpty() == false) {
					tt5 = stack3.pop();
					System.out.println();
				} else {
					break;
				}
				for (int j = index1; j > 1; j--) {
					f9 = stack1.get(index1);
					stack1.remove(index1);
					index1--;
				}
				// 获取第一个数的值
				f7 = stack1.get(index1);
				stack1.remove(index1);
				index1--;
				tt6 = f7.get_value();
				ff4 = tt5 + tt6 * tt6 + ff1 * ff1;
				ff5 = ff1 + ff2;// sum
				ff6 = (ff1 + ff2) / count;// avg
				// stdevp的计算公式为：stdevp = sqrt((x1*x1+x2*x2+...+xn*xn - 2*sum*avg
				// + n * avg * avg)/n)
				// 所以只需按照前面计算varp时取出对应的值进行运算，然后整体开根即可
				ff7 = (ff4 - 2 * ff5 * ff6 + count * ff6 * ff6) / count;
				// 由于要开平方根所以被开根数不能为0
				if (ff7 < 0) {
					throw new SqrtException();
				} else {
					if ((f11.getType() == 1 && f12.getType() == 14)
							|| (f11.getType() == 1 && f12.getType() == 16)
							|| (f11.getType() == 1 && f12.getType() == 18)
							|| (f11.getType() == 1 && f12.getType() == 20)) {
						// 计算stdevp([])
						ff3 = Math.sqrt(ff7);
						stack.remove(index);
						index--;
					} else if ((f11.getType() == 2 && f12.getType() == 14)
							|| (f11.getType() == 2 && f12.getType() == 16)
							|| (f11.getType() == 2 && f12.getType() == 18)
							|| (f11.getType() == 2 && f12.getType() == 20)) {
						ff3 = -1 * Math.sqrt(ff7);
						stack.remove(index);
						index--;
					} else {
						ff3 = Math.sqrt(ff7);
					}
					// 对于嵌套运算取出位于栈顶的count
					if (stack2.isEmpty() == false) {
						ff9 = stack2.pop();
						count = ff9;
						System.out.println();
					} else {
						// 非嵌套运算初始化操作
						count = 2;
					}
					// 对于嵌套运算取出位于栈顶的中间数的平方和
					if (stack5.isEmpty() == false) {
						tt5 = stack5.pop();
						push3(tt5);
						System.out.println();
					} else {
						// 非嵌套运算初始化操作
						tt5 = 0;
						push3(tt5);
					}
					// 对于嵌套运算取出位于栈顶的第一个数
					if (stack6.isEmpty() == false) {
						tt6 = stack6.pop();
						f1.set_value(tt6);
						push1(f1);
						System.out.println();
					} else {
						break;
					}
				}
			} else
			// 若操作数等于或低于两个则执行以下操作
			{
				ff6 = (ff1 + ff2) / count;// avg
				ff7 = ((ff1 - ff6) * (ff1 - ff6) + (ff2 - ff6) * (ff2 - ff6))
						/ count;
				if (ff7 < 0) {
					throw new SqrtException();
				} else {
					if ((f11.getType() == 1 && f12.getType() == 14)
							|| (f11.getType() == 1 && f12.getType() == 16)
							|| (f11.getType() == 1 && f12.getType() == 18)
							|| (f11.getType() == 1 && f12.getType() == 20)) {
						ff3 = Math.sqrt(ff7);
						stack.remove(index);
						index--;
					} else if ((f11.getType() == 2 && f12.getType() == 14)
							|| (f11.getType() == 2 && f12.getType() == 16)
							|| (f11.getType() == 2 && f12.getType() == 18)
							|| (f11.getType() == 2 && f12.getType() == 20)) {
						ff3 = -1 * Math.sqrt(ff7);
						stack.remove(index);
						index--;
					} else {
						// 计算stdevp([])
						ff3 = Math.sqrt(ff7);
					}
					// 对于嵌套运算取出位于栈顶的count
					if (stack2.isEmpty() == false) {
						ff9 = stack2.pop();
						count = ff9;
						System.out.println();
					} else {
						// 非嵌套运算初始化操作
						count = 2;
					}
					// 对于嵌套运算取出位于栈顶的中间数的平方和
					if (stack5.isEmpty() == false) {
						tt5 = stack5.pop();
						push3(tt5);
						System.out.println();
					} else {
						// 非嵌套运算初始化操作
						tt5 = 0;
						push3(tt5);
					}
					// 对于嵌套运算取出位于栈顶的第一个操作数
					if (stack6.isEmpty() == false) {
						tt6 = stack6.pop();
						f1.set_value(tt6);
						push1(f1);
						System.out.println();
					} else {
						break;
					}
				}
			}
			break;
		/*
		 * stdve([])运算
		 */
		case 26:
			if (count > 2) {
				if (stack3.isEmpty() == false) {
					// 获取中间数的平方和
					tt5 = stack3.pop();
				} else {
					break;
				}
				for (int j = index1; j > 1; j--) {
					f9 = stack1.get(index1);
					stack1.remove(index1);
					index1--;
				}
				// 获取第一个数的值
				f7 = stack1.get(index1);
				stack1.remove(index1);
				index1--;
				tt6 = f7.get_value();
				ff4 = tt5 + tt6 * tt6 + ff1 * ff1;
				ff5 = ff1 + ff2;// sum
				ff6 = (ff1 + ff2) / count;// avg
				// stdev([])的计算公式为：stdev = sqrt((x1*x1+x2*x2+...+xn*xn -
				// 2*sum*avg + n * avg * avg)/n-1)
				// 所以计算出var后直接进行开方即可
				ff7 = (ff4 - 2 * ff5 * ff6 + count * ff6 * ff6) / (count - 1);

				if (ff7 < 0) {
					throw new SqrtException();
				} else if ((f11.getType() == 1 && f12.getType() == 14)
						|| (f11.getType() == 1 && f12.getType() == 16)
						|| (f11.getType() == 1 && f12.getType() == 18)
						|| (f11.getType() == 1 && f12.getType() == 20)) {
					// 计算stdev([])
					ff3 = Math.sqrt(ff7);
					stack.remove(index);
					index--;
				} else if ((f11.getType() == 2 && f12.getType() == 14)
						|| (f11.getType() == 2 && f12.getType() == 16)
						|| (f11.getType() == 2 && f12.getType() == 18)
						|| (f11.getType() == 2 && f12.getType() == 20)) {
					ff3 = -1 * Math.sqrt(ff7);
					stack.remove(index);
					index--;
				} else {
					ff3 = Math.sqrt(ff7);
				}
				// 对于嵌套运算取出位于栈顶的count
				if (stack2.isEmpty() == false) {
					ff9 = stack2.pop();
					count = ff9;
				} else {
					// 非嵌套运算初始化操作
					count = 2;
				}
				// 对于嵌套运算取出位于栈顶的中间数的平方和
				if (stack5.isEmpty() == false) {
					tt5 = stack5.pop();
					push3(tt5);
				} else {
					// 非嵌套运算初始化操作
					tt5 = 0;
					push3(tt5);
				}
				// 对于嵌套运算取出位于栈顶的第一个操作数
				if (stack6.isEmpty() == false) {
					tt6 = stack6.pop();
					f1.set_value(tt6);
					push1(f1);
				} else {
					break;
				}
			} else
			// 若操作数等于或低于两个则执行以下操作
			{
				ff6 = (ff1 + ff2) / count;// avg
				ff7 = ((ff1 - ff6) * (ff1 - ff6) + (ff2 - ff6) * (ff2 - ff6))
						/ (count - 1);

				if (ff7 < 0) {
					throw new SqrtException();
				} else if ((f11.getType() == 1 && f12.getType() == 14)
						|| (f11.getType() == 1 && f12.getType() == 16)
						|| (f11.getType() == 1 && f12.getType() == 18)
						|| (f11.getType() == 1 && f12.getType() == 20)) {
					// 计算stdev([])
					ff3 = Math.sqrt(ff7);
					stack.remove(index);
					index--;
				} else if ((f11.getType() == 2 && f12.getType() == 14)
						|| (f11.getType() == 2 && f12.getType() == 16)
						|| (f11.getType() == 2 && f12.getType() == 18)
						|| (f11.getType() == 2 && f12.getType() == 20)) {
					ff3 = -1 * Math.sqrt(ff7);
					stack.remove(index);
					index--;
				} else {
					ff3 = Math.sqrt(ff7);
				}
				// 对于嵌套运算取出位于栈顶的count
				if (stack2.isEmpty() == false) {
					ff9 = stack2.pop();
					count = ff9;
				} else {
					// 非嵌套运算初始化操作
					count = 2;
				}
				// 对于嵌套运算取出位于栈顶的中间数的平方和
				if (stack5.isEmpty() == false) {
					tt5 = stack5.pop();
					push3(tt5);
				} else {
					// 非嵌套运算初始化操作
					tt5 = 0;
					push3(tt5);
				}
				// 对于嵌套运算取出位于栈顶的第一个操作数
				if (stack6.isEmpty() == false) {
					tt6 = stack6.pop();
					f1.set_value(tt6);
					push1(f1);
				} else {
					break;
				}
			}
			break;
		default:
			throw new ExpressionException();
		}
		f3.set_value(ff3);
		push(f3);
	}

	/**
	 * binary()函数实现二元操作 + - * / mod pow log的运算
	 * 
	 * @throws DividedByZeroException
	 * @throws NotLitterThanZeroException
	 * @throws ExpressionException
	 */
	public void binary() throws DividedByZeroException,
			NotLitterThanZeroException, ExpressionException {
		Token t1, t2, t3, t4, t5, t6;
		double tt1, tt2, tt3, tt4;
		int ttt1, ttt2, ttt3;
		BigDecimal b1, b2, b3, b4;
		t3 = stack.get(index);// 取第二个操作数
		stack.remove(index);
		index--;
		tt3 = t3.get_value();
		ttt3 = double_to_int(tt3);

		t2 = stack.get(index);// 取操作符
		stack.remove(index);
		index--;
		// 如果第二个操作数左边为逗号则将其平方结果进stack3,并使用递归算式得出符合条件所有操作数的平方和
		// 该步操作是为了完成集合运算varp,var,stdevp,stdev
		if (t2.getType() == 18) {
			tt5 = tt5 + tt3 * tt3;
			push3(tt5);
		}
		t1 = stack.get(index);// 取第一个操作数
		stack.remove(index);
		index--;
		tt1 = t1.get_value();
		// 取括号左边的运算符
		t4 = stack.get(index);
		// 如果括号左边为“[”，并且数字右边为“,”,则将该数进stack1,如varp([1,2,3]),则将操作数1进stack1.
		// 该步操作是为了完成集合运算varp,var,stdevp,stdev
		if (t4.getType() == 20 && t2.getType() == 18) {
			t3.set_value(tt1);
			push1(t3);
		}

		ttt1 = double_to_int(tt1);

		b1 = ((Value1) t1).getBigValue();
		b1.setScale(40, BigDecimal.ROUND_HALF_EVEN);

		b2 = ((Value1) t3).getBigValue();
		b2.setScale(40, BigDecimal.ROUND_HALF_EVEN);

		switch (t2.getType()) {
		case 1:
			// 计算两个数的和
			b1 = b1.add(b2);
			break;
		case 2:
			// 计算两个数的差
			b1 = b1.subtract(b2);
			break;
		case 3:
			// 计算两个数的积
			b1 = b1.multiply(b2);
			break;
		case 4:
			// 除数不能为零
			if (b2.equals(0)) {
				throw new DividedByZeroException();
			}
			// 计算两个数的商
			b1 = b1.divide(b2, 40, BigDecimal.ROUND_HALF_EVEN);
			break;
		case 9:
			if (tt3 == 0) {
				throw new DividedByZeroException();
			}
			// 取模运算
			tt2 = (b1.doubleValue()) % (b2.doubleValue());
			b1 = b1.valueOf(tt2);
			break;
		// 求集合运算时，数字遇到“，”时要进行规约，即将“，”两边的数字相加，规约成一个数
		case 18:
			b1 = b1.valueOf(tt1 + tt3);
			// 统计集合运算时操作数的个数
			count++;
			break;
		case 33:
			// 求x的y次方运算
			b1 = b1.valueOf(Math.pow(b1.doubleValue(), b2.doubleValue()));
			break;
		default:
			throw new ExpressionException();
		}
		t1.set_value(b1);
		push(t1);
	}

	/*
	 * 实现对集合运算中只有一个元素的情况的处理，如：sum([x])
	 */
	public void sum() throws ExpressionException {
		Token s1, s2, s3, s4, s5, s6 = null;
		double ss1, ss2;
		s1 = stack.get(index);// 取 ]
		stack.remove(index);
		index--;
		s2 = stack.get(index);// 取操作数
		stack.remove(index);
		index--;
		ss2 = s2.get_value();

		s3 = stack.get(index);// 取 [
		stack.remove(index);
		index--;

		s4 = stack.get(index);// 取运算符
		stack.remove(index);
		index--;
		// 取“-”或“+”
		s5 = stack.get(index);
		if (index >= 1)
			s6 = stack.get(index - 1);
		switch (s4.getType()) {
		case 19:
			if ((s5.getType() == 1 && s6.getType() == 14)
					|| (s5.getType() == 1 && s6.getType() == 16)
					|| (s5.getType() == 1 && s6.getType() == 18)
					|| (s5.getType() == 1 && s6.getType() == 20)) {
				// 对于只有一个元素的情况，+sum([x]) = x
				ss1 = ss2;
				stack.remove(index);
				index--;
			} else if ((s5.getType() == 2 && s6.getType() == 14)
					|| (s5.getType() == 2 && s6.getType() == 16)
					|| (s5.getType() == 2 && s6.getType() == 18)
					|| (s5.getType() == 2 && s6.getType() == 20)) {
				// 对于只有一个元素的情况，-sum([x]) = -x
				ss1 = -1 * ss2;
				stack.remove(index);
				index--;
			} else {
				// 对于只有一个元素的情况，sum([x]) = x
				ss1 = ss2;
			}
			break;
		case 22:
			if ((s5.getType() == 1 && s6.getType() == 14)
					|| (s5.getType() == 1 && s6.getType() == 16)
					|| (s5.getType() == 1 && s6.getType() == 18)
					|| (s5.getType() == 1 && s6.getType() == 20)) {
				// 对于只有一个元素的情况，+avg([x]) = x
				ss1 = ss2;
				stack.remove(index);
				index--;
			} else if ((s5.getType() == 2 && s6.getType() == 14)
					|| (s5.getType() == 2 && s6.getType() == 16)
					|| (s5.getType() == 2 && s6.getType() == 18)
					|| (s5.getType() == 2 && s6.getType() == 20)) {
				// 对于只有一个元素的情况，-avg([x]) = -x
				ss1 = -1 * ss2;
				stack.remove(index);
				index--;
			} else {
				// 对于只有一个元素的情况，avg([x]) = x
				ss1 = ss2;
			}
			break;
		case 23:
			if ((s5.getType() == 1 && s6.getType() == 14)
					|| (s5.getType() == 1 && s6.getType() == 16)
					|| (s5.getType() == 1 && s6.getType() == 18)
					|| (s5.getType() == 1 && s6.getType() == 20)) {
				// 对于只有一个元素的情况，+varp([x]) = 0
				ss1 = 0;
				stack.remove(index);
				index--;
			} else if ((s5.getType() == 2 && s6.getType() == 14)
					|| (s5.getType() == 2 && s6.getType() == 16)
					|| (s5.getType() == 2 && s6.getType() == 18)
					|| (s5.getType() == 2 && s6.getType() == 20)) {
				// 对于只有一个元素的情况，-varp([x]) = 0
				ss1 = 0;
				stack.remove(index);
				index--;
			} else {
				// 对于只有一个元素的情况，varp([x]) = 0
				ss1 = 0;
			}
			break;
		case 24:
			if ((s5.getType() == 1 && s6.getType() == 14)
					|| (s5.getType() == 1 && s6.getType() == 16)
					|| (s5.getType() == 1 && s6.getType() == 18)
					|| (s5.getType() == 1 && s6.getType() == 20)) {
				// 对于只有一个元素的情况，+var([x]) = 0
				ss1 = 0;
				stack.remove(index);
				index--;
			} else if ((s5.getType() == 2 && s6.getType() == 14)
					|| (s5.getType() == 2 && s6.getType() == 16)
					|| (s5.getType() == 2 && s6.getType() == 18)
					|| (s5.getType() == 2 && s6.getType() == 20)) {
				// 对于只有一个元素的情况，-var([x]) = 0
				ss1 = 0;
				stack.remove(index);
				index--;
			} else {
				// 对于只有一个元素的情况，var([x]) = 0
				ss1 = 0;
			}

			break;
		case 25:
			if ((s5.getType() == 1 && s6.getType() == 14)
					|| (s5.getType() == 1 && s6.getType() == 16)
					|| (s5.getType() == 1 && s6.getType() == 18)
					|| (s5.getType() == 1 && s6.getType() == 20)) {
				// 对于只有一个元素的情况，+stdevp([x]) = 0
				ss1 = 0;
				stack.remove(index);
				index--;
			} else if ((s5.getType() == 2 && s6.getType() == 14)
					|| (s5.getType() == 2 && s6.getType() == 16)
					|| (s5.getType() == 2 && s6.getType() == 18)
					|| (s5.getType() == 2 && s6.getType() == 20)) {
				// 对于只有一个元素的情况，-stdevp([x]) = 0
				ss1 = 0;
				stack.remove(index);
				index--;
			} else {
				// 对于只有一个元素的情况，stdevp([x]) = 0
				ss1 = 0;
			}

			break;
		case 26:
			if ((s5.getType() == 1 && s6.getType() == 14)
					|| (s5.getType() == 1 && s6.getType() == 16)
					|| (s5.getType() == 1 && s6.getType() == 18)
					|| (s5.getType() == 1 && s6.getType() == 20)) {
				// 对于只有一个元素的情况，+stdev([x]) = 0
				ss1 = 0;
				stack.remove(index);
				index--;
			} else if ((s5.getType() == 2 && s6.getType() == 14)
					|| (s5.getType() == 2 && s6.getType() == 16)
					|| (s5.getType() == 2 && s6.getType() == 18)
					|| (s5.getType() == 2 && s6.getType() == 20)) {
				// 对于只有一个元素的情况，-stdev([x]) = 0
				ss1 = 0;
				stack.remove(index);
				index--;
			} else {
				// 对于只有一个元素的情况，stdev([x]) = 0
				ss1 = 0;
			}
			break;
		default:
			throw new ExpressionException();
		}
		s2.set_value(ss1);
		push(s2);
	}

	/**
	 * bracket()函数对带有括号的操作进行处理，如 () (i) (2+6) (x,y) (x+y,a+b)等
	 * 
	 * @throws SqrtException
	 * @throws DividedByZeroException
	 * @throws ExpressionException
	 */
	public void bracket() throws SqrtException, DividedByZeroException,
			ExpressionException {
		int w, q, e;
		Token t1, t2, t3, a1, a2, a3, a4, a5;
		double tt2 = 0;
		// 取stack顶部元素
		t1 = stack.get(index);
		stack.remove(index);
		index--;
		// 若为()则直接返回空
		if (t1.getType() == 14) {
			return;
		}
		// 取stack下一个元素
		t2 = stack.get(index);
		stack.remove(index);
		index--;
		/*
		 * 使用index的值来判断区分类似sin(x)等和其它多元运算，因为对于sin(x)这样的表达式，取出“x”,“(”后对应的
		 * index小于等于1，而对于其他的表达式则对应的index大于1，这样可以将他们区分开而分别进行处理
		 */
		if (index > 1) {
			/*
			 * 再依次出栈两个元素，用于判断其它的表达式，以便进行单独处理，如区分pow(x,x+y)和sin(x+y)，
			 * 若只出栈两个元素，则两个元素是一样的，无法进行区分，从而无法分开处理，若再出栈两个元素则可以将其区分而单独处理
			 * 从而跳转到不同的处理函数
			 */
			a1 = stack.get(index);
			stack.remove(index);
			index--;
			a2 = stack.get(index);
			stack.remove(index);
			index--;

			// 处理pow(x,y)，mod(x,y),log(x,y),yroot(x,y)

			if (t1.getType() == 0 && t2.getType() == 18) {
				// 若是pow(x,y)，mod(x,y),log(x,y),yroot(x,y)则全部进栈
				push(a2);
				push(a1);
				push(t2);
				push(t1);
				// 取运算符
				t3 = stack.get(index - 4);
				w = t3.getType();
				// 若为二元运算符则执行function()函数，完成运算
				if (w == 5 || w == 9 || w == 17 || w == 36) {
					function();
				}
			}
			// 处理类似x+(y)的操作，对“(”进行处理
			else if
			// 判断表达式是否符合x+(y)这样的格式，若符合则执行该处理
			((t1.getType() == 0 && t2.getType() == 14 && a1.getType() == 1 && a2
					.getType() == 0)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a1.getType() == 2 && a2.getType() == 0)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a1.getType() == 3 && a2.getType() == 0)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a1.getType() == 4 && a2.getType() == 0)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a1.getType() == 9 && a2.getType() == 0)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a1.getType() == 33 && a2.getType() == 0)) {
				// 只把x,+,y进stack，而"("丢弃，然后执行binary()，完成运算
				push(a2);
				push(a1);
				push(t1);
				binary();
			}
			// 处理类似(x+y)的表达式，对"("进行处理
			else if
			// 判断表达式是否符合(x+y)这样的格式，若符合则执行该处理
			(t1.getType() == 0 && t2.getType() != 14 && t2.getType() != 18
					&& a2.getType() == 14 && a1.getType() != 11) {
				push(a2);
				push(a1);
				push(t2);
				push(t1);
				do {
					binary();
					t2 = stack.get(index - 1);
					if (t2.getType() == 14) {
						break;
					}
				} while (true);
				t1 = stack.get(index);
				stack.remove(index);
				index--;
				stack.remove(index);
				index--;
				push(t1);
				// 处理形如表达式tan(x+y)的运算
				do {
					t1 = stack.get(index);
					t2 = stack.get(index - 1);
					w = t2.getType();
					if (w == 1 || w == 2 || w == 6 || w == 7 || w == 8
							|| w == 10 || w == 11 || w == 12 || w == 13
							|| w == 27 || w == 28 || w == 29 || w == 30
							|| w == 31 || w == 32 || w == 34 || w == 35) {
						stack.remove(index);
						index--;
						unary(t1);
					} else {
						break;
					}
				} while (true);
			}
			/*
			 * 处理形如sin(sin())的单元素嵌套运算
			 */
			else if
			// 判断是否满足形如sin(sin())的嵌套形式
			((t1.getType() == 0 && t2.getType() == 14 && t2.getType() != 18
					&& a2.getType() == 14 && a1.getType() == 11)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& t2.getType() != 18 && a2.getType() == 14 && a1
							.getType() == 6)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& t2.getType() != 18 && a2.getType() == 14 && a1
							.getType() == 7)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& t2.getType() != 18 && a2.getType() == 14 && a1
							.getType() == 8)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& t2.getType() != 18 && a2.getType() == 14 && a1
							.getType() == 10)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& t2.getType() != 18 && a2.getType() == 14 && a1
							.getType() == 12)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& t2.getType() != 18 && a2.getType() == 14 && a1
							.getType() == 13)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& t2.getType() != 18 && a2.getType() == 14 && a1
							.getType() == 27)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& t2.getType() != 18 && a2.getType() == 14 && a1
							.getType() == 28)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& t2.getType() != 18 && a2.getType() == 14 && a1
							.getType() == 29)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& t2.getType() != 18 && a2.getType() == 14 && a1
							.getType() == 30)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& t2.getType() != 18 && a2.getType() == 14 && a1
							.getType() == 31)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& t2.getType() != 18 && a2.getType() == 14 && a1
							.getType() == 32)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& t2.getType() != 18 && a2.getType() == 14 && a1
							.getType() == 34)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& t2.getType() != 18 && a2.getType() == 14 && a1
							.getType() == 35)) {
				// 除“(”外其它元素都进栈
				push(a2);
				push(a1);
				push(t1);
				// 运用递归操作对单元素函数嵌套表达式进行处理
				do {
					// 取出操作数
					t1 = stack.get(index);
					// 取运算符
					t2 = stack.get(index - 1);
					w = t2.getType();
					if (w == 6 || w == 7 || w == 8 || w == 10 || w == 11
							|| w == 12 || w == 13 || w == 27 || w == 28
							|| w == 29 || w == 30 || w == 31 || w == 32
							|| w == 34 || w == 35) {
						// 移除操作数，只剩下运算符
						stack.remove(index);
						index--;
						// 执行单元素操作
						unary(t1);
					} else {
						break;
					}
				} while (true);
			}
			// 处理形如-tan(1)的表达式
			else if
			// 判断表达式形式是否形如-tan(1)
			((t1.getType() == 0 && t2.getType() == 14 && t2.getType() != 18
					&& a2.getType() == 2 && a1.getType() == 11)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& t2.getType() != 18 && a2.getType() == 2 && a1
							.getType() == 6)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& t2.getType() != 18 && a2.getType() == 2 && a1
							.getType() == 7)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& t2.getType() != 18 && a2.getType() == 2 && a1
							.getType() == 8)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& t2.getType() != 18 && a2.getType() == 2 && a1
							.getType() == 10)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& t2.getType() != 18 && a2.getType() == 2 && a1
							.getType() == 12)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& t2.getType() != 18 && a2.getType() == 2 && a1
							.getType() == 13)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& t2.getType() != 18 && a2.getType() == 2 && a1
							.getType() == 27)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& t2.getType() != 18 && a2.getType() == 2 && a1
							.getType() == 28)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& t2.getType() != 18 && a2.getType() == 2 && a1
							.getType() == 29)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& t2.getType() != 18 && a2.getType() == 2 && a1
							.getType() == 30)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& t2.getType() != 18 && a2.getType() == 2 && a1
							.getType() == 31)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& t2.getType() != 18 && a2.getType() == 2 && a1
							.getType() == 32)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& t2.getType() != 18 && a2.getType() == 2 && a1
							.getType() == 34)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& t2.getType() != 18 && a2.getType() == 2 && a1
							.getType() == 35)) {
				// 除“(”外其它元素都进栈
				push(a2);
				push(a1);
				push(t1);
				do {
					// 取操作数
					t1 = stack.get(index);
					// 取运算符
					t2 = stack.get(index - 1);
					w = t2.getType();
					if (w == 6 || w == 7 || w == 8 || w == 10 || w == 11
							|| w == 12 || w == 13 || w == 27 || w == 28
							|| w == 29 || w == 30 || w == 31 || w == 32
							|| w == 34 || w == 35) {
						// 移除操作数
						stack.remove(index);
						index--;
						// 执行单元素运算
						unary(t1);
					} else {
						break;
					}
				} while (true);
			}
			// 处理对形如表达式pow(1,sin(1)+1) mod(1,sin(1)+1) yroot(1,sin(1)+1)
			// log(1,sin(1)+1) 处理其中的sin(1)
			else if
			// 因为有很多单元运算符，所以每个都要进行判断
			((t1.getType() == 0 && t2.getType() == 14 && a2.getType() == 18 && a1
					.getType() == 11)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 18 && a1.getType() == 6)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 18 && a1.getType() == 7)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 18 && a1.getType() == 8)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 18 && a1.getType() == 10)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 18 && a1.getType() == 12)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 18 && a1.getType() == 13)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 18 && a1.getType() == 27)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 18 && a1.getType() == 28)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 18 && a1.getType() == 29)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 18 && a1.getType() == 30)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 18 && a1.getType() == 31)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 18 && a1.getType() == 32)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 18 && a1.getType() == 34)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 18 && a1.getType() == 35)) {
				// 除“(”外其它元素都进栈
				push(a2);
				push(a1);
				push(t1);
				do {
					// 取操作数
					t1 = stack.get(index);
					// 取运算符
					t2 = stack.get(index - 1);
					w = t2.getType();
					if (w == 6 || w == 7 || w == 8 || w == 10 || w == 11
							|| w == 12 || w == 13 || w == 27 || w == 28
							|| w == 29 || w == 30 || w == 31 || w == 32
							|| w == 34 || w == 35) {
						// 移除操作数
						stack.remove(index);
						index--;
						// 执行单元素运算
						unary(t1);
					} else {
						break;
					}
				} while (true);
			}
			// 对于形如表达式pow(2,2+sin(1.57)) mod(2,2+sin(1.57)) yroot(2,2+sin(1.57))
			// log(2,2+sin(1.57))
			// 处理其中的sin(1.57)
			else if ((t1.getType() == 0 && t2.getType() == 14
					&& a2.getType() == 1 && a1.getType() == 11)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 1 && a1.getType() == 6)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 1 && a1.getType() == 7)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 1 && a1.getType() == 8)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 1 && a1.getType() == 10)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 1 && a1.getType() == 12)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 1 && a1.getType() == 13)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 1 && a1.getType() == 27)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 1 && a1.getType() == 28)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 1 && a1.getType() == 29)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 1 && a1.getType() == 30)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 1 && a1.getType() == 31)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 1 && a1.getType() == 32)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 1 && a1.getType() == 34)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 1 && a1.getType() == 35)) {
				// 除“(”外其它元素都进栈
				push(a2);
				push(a1);
				push(t1);
				do {
					// 取操作数
					t1 = stack.get(index);
					// 取运算符
					t2 = stack.get(index - 1);
					w = t2.getType();
					// 判断是否是一元运算符，若是则进行一元运算
					if (w == 6 || w == 7 || w == 8 || w == 10 || w == 11
							|| w == 12 || w == 13 || w == 27 || w == 28
							|| w == 29 || w == 30 || w == 31 || w == 32
							|| w == 34 || w == 35) {
						// 移除操作数
						stack.remove(index);
						index--;
						// 执行一元运算
						unary(t1);
					} else {
						break;
					}
				} while (true);
			}

			// 处理形如表达式sum([x,y]) avg([x,y]) varp([x,y]) var([x,y]) stdevp([x,y])
			// stdev([x,y])的集合运算
			else if (t1.getType() == 21 && a1.getType() != 20) {
				// 除左括号外其它元素进入stack
				push(a2);
				push(a1);
				push(t2);
				// 取运算符
				a5 = stack.get(index - 5);
				w = a5.getType();
				// 若是集合运算则执行gather()完成集合运算
				if (w == 19 || w == 22 || w == 23 || w == 24 || w == 25
						|| w == 26) {
					gather();
				}
			}
			// 处理形如表达式sum([x]) avg([x]) varp([x]) var([x]) stdevp([x])
			// stdev([x])的集合运算
			else if (t1.getType() == 21 && a1.getType() == 20) {
				push(a1);
				push(t2);
				push(t1);
				t1 = stack.get(index - 3);
				w = t1.getType();
				if (w == 19 || w == 22 || w == 23 || w == 24 || w == 25
						|| w == 26) {
					sum();
				}
			}
			// 处理形如表达式sum([sin(x),y]) avg([sin(x),y]) varp([sin(x),y])
			// var([sin(x),y])
			// stdevp([sin(x),y]) stdev([sin(x),y])中的sin(x)表达式
			else if
			// 判断是否属于以上表达式的一种
			((t1.getType() == 0 && t2.getType() == 14 && a2.getType() == 20 && a1
					.getType() == 11)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 20 && a1.getType() == 6)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 20 && a1.getType() == 7)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 20 && a1.getType() == 8)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 20 && a1.getType() == 10)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 20 && a1.getType() == 12)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 20 && a1.getType() == 13)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 20 && a1.getType() == 27)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 20 && a1.getType() == 28)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 20 && a1.getType() == 29)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 20 && a1.getType() == 30)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 20 && a1.getType() == 31)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 20 && a1.getType() == 32)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 20 && a1.getType() == 34)
					|| (t1.getType() == 0 && t2.getType() == 14
							&& a2.getType() == 20 && a1.getType() == 35)) {
				// 对于单元素的表达式的处理同上
				push(a2);
				push(a1);
				push(t1);
				do {
					t1 = stack.get(index);
					t2 = stack.get(index - 1);
					w = t2.getType();
					if (w == 6 || w == 7 || w == 8 || w == 10 || w == 11
							|| w == 12 || w == 13 || w == 27 || w == 28
							|| w == 29 || w == 30 || w == 31 || w == 32
							|| w == 34 || w == 35) {
						stack.remove(index);
						index--;
						unary(t1);
					} else {
						break;
					}
				} while (true);
			}
			// 处理形如pow(x+y,a+b),mod(x+y,a+b),yroot(x+y,a+b),log(x+y,a+b)的表达式
			else if (t1.getType() == 0 && t2.getType() != 14) {
				push(a2);
				push(a1);
				push(t2);
				push(t1);
				t3 = stack.get(index - 6);
				w = t3.getType();
				if (w == 5 || w == 9 || w == 17) {
					function2();
				} else if (w == 0) {
					function3();
				} else if (w == 6 || w == 7 || w == 8 || w == 10 || w == 11
						|| w == 12 || w == 13 || w == 27 || w == 28 || w == 29
						|| w == 30 || w == 31 || w == 32 || w == 34 || w == 35) {
					unarys();
				}
			}
			// 处理tan(((x)))等括号的无限嵌套
			else if (t1.getType() == 0 && t2.getType() == 14
					&& a1.getType() == 14) {
				push(a2);
				push(a1);
				push(t1);
				do {
					t1 = stack.get(index);
					t2 = stack.get(index - 1);
					w = t2.getType();
					// 处理-(sin(x))
					if (w == 1 || w == 2) {
						if (index - 2 >= 0) {
							Token t31 = stack.get(index - 2);
							if (t31.getType() == 0) {
								binary();
							} else {
								stack.remove(index);
								index--;
								unary(t1);
							}

						}
					} else if (w == 6 || w == 7 || w == 8 || w == 10 || w == 11
							|| w == 12 || w == 13 || w == 27 || w == 28
							|| w == 29 || w == 30 || w == 31 || w == 32
							|| w == 34 || w == 35) {
						// 移除数字
						stack.remove(index);
						index--;
						unary(t1);
					} else {
						break;
					}
				} while (true);
			}
		}
		// 处理形如表达式tan(x)的运算
		else if (t1.getType() == 0 && t2.getType() == 14) {
			push(t1);
			do {
				t1 = stack.get(index);
				t2 = stack.get(index - 1);
				w = t2.getType();
				// 处理-(sin(x))
				if (w == 1 || w == 2) {
					if (index - 2 >= 0) {
						Token t31 = stack.get(index - 2);
						if (t31.getType() == 0) {
							binary();
						} else {
							stack.remove(index);
							index--;
							unary(t1);
						}

					}
				} else if (w == 6 || w == 7 || w == 8 || w == 10 || w == 11
						|| w == 12 || w == 13 || w == 27 || w == 28 || w == 29
						|| w == 30 || w == 31 || w == 32 || w == 34 || w == 35) {
					// 移除数字
					stack.remove(index);
					index--;
					unary(t1);
				} else {
					break;
				}
			} while (true);
		}
		// 处理形如sin2)的异常情况
		else if (t1.getType() == 0 && t2.getType() != 14) {
			throw new ExpressionException();
		}
	}

	/**
	 * 该函数通过table2主要实现对运算符的优先级的比较，并完成规约操作
	 * 返回1表示规约结束成功，返回2表示已在栈中的优先级高，并且已经规约。返回3表示已在栈中的优先级低，压栈
	 * 
	 * @param temp
	 * @return
	 * @throws DividedByZeroException
	 * @throws ExpressionException
	 */
	public int accept(Token temp) throws DividedByZeroException,
			ExpressionException {
		Token t1, t2;
		int i, num1, num2, num3;
		i = index - 1;
		t1 = stack.get(i);
		// 获取要比较的左侧的已经入栈的运算符的种别码
		num1 = t1.getType();
		// 获得将要入栈的运算符的种别码
		num2 = temp.getType();
		// 对应到table2，获取优先级比较结果
		i = table.table2[num1][num2];
		if (i == 6) {
			// 返回1表示规约结束成功
			return 1;
		} else if (i == -1) {
			binary();
			// 返回2表示已在栈中的优先级高，并且已经规约
			return 2;
		} else if (i == 1) {
			// 返回3表示已在栈中的优先级低，直接压栈
			return 3;
		}
		// 如果负号后面紧跟一个数字，直接将其规约为负数，把负号移除，将负数压栈。
		else if (i == 2) {
			Token t = scanner.setToken("0");
			if (stack.get(index).getType() == 1) {
				t.set_value(temp.get_value());
			} else if (stack.get(index).getType() == 2) {
				t.set_value(-1 * temp.get_value());
			}
			stack.remove(index);
			index--;
			push(t);
			return 4;
		} else {
			throw new ExpressionException();
		}
	}

	/**
	 * guiyue函数实现规约操作，通过比较已读入的元素和要读入的元素的优先关系进行对应于表的规约操作
	 * 
	 * @throws SqrtException
	 * @throws EmptyExpressionException
	 * @throws DividedByZeroException
	 * @throws ExpressionException
	 */
	public void guiyue() throws SqrtException, EmptyExpressionException,
			DividedByZeroException, ExpressionException {
		int num1, num2, i, tt, flag1;
		Token t1, t2, t3, t4, t5, t6, t7, t8, t9;
		flag1 = 0;
		double tt1, tt2, tt3, tt4;
		while (true) {
			// t2依次取得下一个输入的字符
			t2 = scanner.getNextToken();
			// 取下一字符的种别码
			num2 = t2.getType();
			// t1取已经入栈的字符
			t1 = stack.get(index);
			// 取已经入栈的字符的种别码
			num1 = t1.getType();
			// 对应于table，取得i执行相应的操作
			i = table.table[num1][num2];

			switch (i) {
			// 若两个字符在table表中对应为0，则直接将t2压栈
			case 0:
				push(t2);
				break;
			// 若两个字符在table表中对应为1，则执行一元操作
			case 1:
				unary(t2);
				break;
			// 若两个字符在table表中对应为3，则执行处理括号操作
			case 3:
				bracket();
				break;
			// 若两个字符在table表中对应为4，则执行accept函数
			case 4:
				tt = 2;
				while (tt == 2) {
					tt = accept(t2);
				}
				if (tt == 1) {
					flag1 = 1; // 标志规约成功
				} else if (tt == 4) {
					break;
				} else {
					push(t2);
				}
				break;
			// 若两个字符在table表中对应为5，则执行以下操作
			case 5:
				// 获取每次第一层的count值
				count1 = count;
				// 将每次获取的第一层的count值保存起来，压入栈中
				push2(count1);
				// 将count置为初值，以让下一个集合进行运算
				count = 2;
				if (stack3.isEmpty() == false) {
					tt3 = stack3.pop();
					push5(tt3);
				}
				// 当index1大于或等于1时，此时保存index1=1对应的栈中的值
				if (index1 >= 1) {
					// 使用for循环将stack1清空到index1=1
					for (int j = index1; j > 1; j--) {
						t5 = stack1.get(index1);
						stack1.remove(index1);
						index1--;
					}
					t4 = stack1.get(index1);
					stack1.remove(index1);
					index1--;
					tt4 = t4.get_value();
					// 将index1=1对应的数组中的值存到stack6中，以便之后的集合运算结束后将该值取出，赋给之前的运算，使用递归来完成
					push6(tt4);
				}
				// tt5 清空
				tt5 = 0;
				push3(tt5);
				// 将运算符压入栈中
				push(t2);
				break;
			// 若两个字符在table表中对应为7，则执行以下操作
			case 7:
				// 如果是遇到“++”，后面来的“+”不进stack，直接遍历后一个词，实现“++”得“+”
				if (t1.getType() == 1 && t2.getType() == 1) {
					break;
				}
				// 如果遇到“+-”，前面的“+”出stack，而将后面的“-”进stack，实现“+-”得“-”
				else if (t1.getType() == 1 && t2.getType() == 2) {
					t7 = stack.get(index);
					stack.remove(index);
					index--;
					push(t2);
				}
				// 遇到“--”，将存于stack7的“+”运算符取出来放到stack中，实现“--”得“+”
				else if (t1.getType() == 2 && t2.getType() == 2) {
					t8 = stack7.get(index2);
					t9 = stack.get(index);
					stack.remove(index);
					index--;
					push(t8);
				}
				// 遇到“-+”，后面的“+”不进stack，直接遍历后面的词，实现“-+”得“-”
				else if (t1.getType() == 2 && t2.getType() == 1) {
					break;
				}
				break;

			default:
				throw new ExpressionException();
			}// switch
			if (flag1 == 1) {
				t1 = stack.get(index);
				// count 还原为初值
				count = 2;
				// 每次计算结束后将tt5，tt6还原为初值
				tt6 = 0;
				tt5 = 0;
				// 得出结果
				Value1 finalToken = (Value1) t1;
				bigResult = finalToken.getBigValue();
				//System.out.println(bigResult);
				break;
			}
		}// while (true)
	}// guiyue
}// 类Parser