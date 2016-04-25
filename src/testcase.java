import parser.*;

import java.math.BigDecimal;
import java.text.*;

public class testcase
{

    static BigDecimal result;
    
	public static void main(String[] args) 
	{
	 String str[] = {"6+2*3/4-3","1/3","Pow(2,1000)/pow(2,500)","3mod2^4",
	   "(.1+1)^.2","+123.5+.234/.12*2","sin(45)+cos(45)+tan(45)","arcsin(0.4)/arccos(0.2)*arctan(0.6)","sinh(60)+cosh(30)-tanh(50)",
	 "SIN(90)+ArCsin(0.3)-tANH(30)","arccos(.3)+3","log(.64,.2)","log(.25,.5)","log10(10*10*10*log(100,10))","ln(9*.2)","pow(3,4)","Pow(.3+2,.3)",
	 "exp(4)","fact(4/2+3*2)","mod(sin(45),2+cos(30))","Mod(8,3)","sqrt(mod(8,3))","cuberoot(8)","yroot(3,cuberoot(8))","sum([1,1,1,1,1024])",
	 "avg([205,205,205,205])","varp([1024,1024,1024,1024])","avg([1,1,1,avg([1,1,avg([avg([1,avg([1,1,1,avg([1,1,1,1])])])])]),avg([1,1])])",
	 "var([var([1,1]),var([1,1])])","-.12345+54321*123/321^2mod(sum([-1+10^2*sin(cos(tan(100))),arcsin(-1+1-1),log(100,10)*log10(1000)/ln(exp(1)),cuberoot(sqrt(fact(10)*mod(5,3)))*yroot(5,2),avg([3,4,5,6]),sum([987,253])*var([1,11,111,1111])/stdev([123,321,456,654,789,987])]))",
	 "stdevp([0,0,0,stdev([1,1,1]),stdevp([1,1,1])])","fact(mod(4mod3,avg([3,3,avg([3,3,avg([3,3,3])]),3,3,3,avg([3,3,3,3])])))","(avg([1,1,1])*var([1,1,1,varp([1,1,stdevp([1,2,0])])]))mod(varp([1,0,2,pow(2,1)]))+mod(2,1)/var([1,2,1,yroot(8,3)])","-tan(45)--.3/pow(2,sinh(30))",
	 "pow(sqrt(sin(exp(cuberoot(log10(ln(fact(2))))))),sum([1,1,avg([1,1,varp([200,102,201]),stdevp([sqrt(4)])])]))","sqrt(sin(exp(cuberoot(log10(ln(fact(2)))))))","STDeVp([pow(sum([1,log(.25,.5)]),sin(90)),VArp([EXP(2)])])","102mod11^12-----------++++++++-----------++++++sqrt(64)",
	 "-pow(log(2,8),-sin(45)+tan(45))/sqrt(1.333)","fact(sum([2,2]))  +    mod(12345678901234566,1233456.71234523101)","log(stdevp([sinh(30),cosh(60),tanh(45)]),avg([arcsin(0.3),arccos(0.4),arctan(0.5)]))","var([sum([avg([varp([stdevp([exp(sqrt(cuberoot(log10(10))))])])])])])"
	 };
	 Parser parser;
     try {	    
           parser = new Parser(str[0]+"#");
           parser.guiyue();
           result = parser.bigResult;
		   System.out.println("\n测试用例1："+str[0]+"="+result);
	
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例1："+str[0]+"  "+ex.getMessage());
	     }
     try {
           parser = new Parser(str[1]+"#");
           parser.guiyue();
           result = parser.bigResult;
		   System.out.println("\n测试用例2："+str[1]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例2："+str[1]+"  "+ex.getMessage());
	     }
     try {
           parser = new Parser(str[2]+"#");
           parser.guiyue();
           result = parser.bigResult;
		   System.out.println("\n测试用例3："+str[2]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例3："+str[2]+"  "+ex.getMessage());
	     }
     try {
           parser = new Parser(str[3]+"#");
           parser.guiyue();
           result = parser.bigResult;
		   System.out.println("\n测试用例4："+str[3]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例4："+str[3]+"  "+ex.getMessage());
	     }
     try {
           parser = new Parser(str[4]+"#");
           parser.guiyue();
           result = parser.bigResult;
		   System.out.println("\n测试用例5："+str[4]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例5："+str[4]+"  "+ex.getMessage());
	     }
     try {
           parser = new Parser(str[5]+"#");
           parser.guiyue();
           result = parser.bigResult;
		   System.out.println("\n测试用例6："+str[5]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例6："+str[5]+"  "+ex.getMessage());
	     }
     try {
           parser = new Parser(str[6]+"#");
           parser.guiyue();
           result = parser.bigResult;
		   System.out.println("\n测试用例7："+str[6]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例7："+str[6]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[7]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例8："+str[7]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例8："+str[7]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[8]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例9："+str[8]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例9："+str[8]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[9]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例10："+str[9]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例10："+str[9]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[10]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例11："+str[10]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例11："+str[10]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[11]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例12："+str[11]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例12："+str[11]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[12]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例13："+str[12]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例13："+str[12]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[13]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例14："+str[13]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例14："+str[13]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[14]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例15："+str[14]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例15："+str[14]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[15]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例16："+str[15]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例16："+str[15]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[16]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例17："+str[16]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例17："+str[16]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[17]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例18："+str[17]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例18："+str[17]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[18]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例19："+str[18]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例19："+str[18]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[19]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例20："+str[19]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例20："+str[19]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[20]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例21："+str[20]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例21："+str[20]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[21]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例22："+str[21]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例22："+str[21]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[22]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例23："+str[22]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例23："+str[22]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[23]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例24："+str[23]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例24："+str[23]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[24]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例25："+str[24]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例25："+str[24]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[25]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例26："+str[25]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例26："+str[25]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[26]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例27："+str[26]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例27："+str[26]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[27]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例28："+str[27]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例28："+str[27]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[28]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例29："+str[28]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例29："+str[28]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[29]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例30："+str[29]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例30："+str[29]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[30]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例31："+str[30]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例31："+str[30]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[31]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例32："+str[31]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例32："+str[31]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[32]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例33："+str[32]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例33："+str[32]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[33]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例34："+str[33]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例34："+str[33]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[34]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例35："+str[34]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例35："+str[34]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[35]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例36："+str[35]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例36："+str[35]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[36]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例37："+str[36]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例37："+str[36]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[37]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例38："+str[37]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例38："+str[37]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[38]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例39："+str[38]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例39："+str[38]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[39]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例40："+str[39]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例40："+str[39]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[40]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例41："+str[40]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例41："+str[40]+"  "+ex.getMessage());
	     }
     try {
         parser = new Parser(str[41]+"#");
         parser.guiyue();
         result = parser.bigResult;
		   System.out.println("\n测试用例42："+str[41]+"="+result);
		 } 
	 catch (Exception ex)
	     {
				System.out.println("\n测试用例42："+str[41]+"  "+ex.getMessage());
	     }
	}
}