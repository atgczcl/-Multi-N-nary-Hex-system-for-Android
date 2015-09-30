package com.atgczcl.SysConvert.tools;
import java.math.BigDecimal;
import java.math.BigInteger;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.atgczcl.sysconvertfuncalculater.R;


/**
 * @author 张成龙
 * 法律申明，版权所有，盗版必究
 * 联系：atgczcl@163.com
 */
public class NumberTools {
	Context mContext;
	public NumberTools(Context context) {
		mContext=context;
		// TODO Auto-generated constructor stub
	}
	
	boolean stringErr(String str){
		return str == null || str.equals("0") || str.equals("1") || str=="";
	}
	
	StringBuilder biString=new StringBuilder();
	/**
	 * 10进制的整数转换成任意进制的整数
	 * @param numS
	 * @param toS
	 * @return
	 */
	public String decimal2Any(String numS, String toS) {
		if (numS == null || numS=="" || stringErr(toS)) {
			System.out.println("to is 0");
			return "0";
		}
		boolean flag=true;
		BigInteger to=new BigInteger(toS);
		BigInteger num=new BigInteger(numS);
		while (flag) {
			BigInteger tempT=BigInteger.ZERO;
			if (num.compareTo(BigInteger.ZERO) < 0) {
				tempT=num.mod(to);
			}else {
				tempT=num.remainder(to);
			}
			BigInteger tempH=num.divide(to);
//			System.out.println("tempT="+tempT.intValue()+"  tempH="+tempH+"   "+(char)1024);
			if (tempT.compareTo(new BigInteger("9")) > 0) {
				tempT=tempT.add(new BigInteger("55"));
//				System.out.println("tempT.add="+tempT.intValue()+"  tempH="+tempH+"   "+(int)ch);
				biString.append((char)(tempT.intValue()));
			}else {
				biString.append(tempT);
			}
			if (tempH.compareTo(BigInteger.ZERO) != 0) {
				num=tempH;
			}else {
//			System.out.println("tempT= "+tempT+"  tempH= "+tempH+"  biString="+biString.reverse().toString());
				return biString.reverse().toString();
			}
		}
		
//		else {
//			System.out.println("tempT= "+tempT+"  tempH= "+tempH+"  biString="+biString.reverse().toString());
//		}
		return "0";
	}
	
	static int count=32;
	static StringBuilder floatString=new StringBuilder();
	/**
	 * <1的10进制的浮点数，转换成任意进制浮点数
	 * @param flt
	 * @param to
	 * @return
	 */
	public String floatDecimalToAny(String flt, String to) {
		if (flt == null || flt=="" || stringErr(to)) {
			System.out.println("to is 0");
			return "0";
		}
		BigDecimal bigDecimal=new BigDecimal(flt);
		bigDecimal=bigDecimal.multiply(new BigDecimal(to));
		BigInteger bigInteger=bigDecimal.toBigInteger();
		int temp=bigInteger.intValue();
		//set for char
		if (temp>9) {
			floatString.append((char)(temp+55));
		}else {
			floatString.append(temp);
		}
		
		if (floatString.length()<32) {
			String tempFloat=bigDecimal.toString();
//			System.out.println("浮点数="+tempFloat);
			String[] tempStrings=tempFloat.split("\\.");
//			System.out.println(tempStrings.length);
			if (tempStrings.length>1) {
				tempFloat=tempStrings[1];
			}else {
				tempFloat=tempStrings[0];
			}
//			System.out.println("小数部分="+tempFloat);
			return floatDecimalToAny("0."+tempFloat, to);
		}else {
//			System.out.println((int)'0');
//			System.out.println("0."+floatString);
			return floatString.toString();
		}
	}
	
	/**
	 * 
	 * 任意进制的 <1 的浮点数，转换成10进制浮点数
	 * @param num
	 * @param from
	 * @return
	 */
	public String floatAnyToDecimal(String num, String from, boolean isFull) {
		if (num == null || num=="" || stringErr(from)) {
			System.out.println("to is 0");
			return "0";
		}
		
		BigInteger fromInteger=new BigInteger(from);
		if (fromInteger.compareTo(new BigInteger("1")) >0 && fromInteger.compareTo(new BigInteger("11"))<0) {
			fromInteger=fromInteger.add(new BigInteger("47"));//最大位的码数
		}else {
			fromInteger=fromInteger.add(new BigInteger("54"));//最大位的码数
		}
		
		StringBuilder result=new StringBuilder();
		int len=num.length();
		BigDecimal ruesltNum=BigDecimal.ZERO;
		for (int i = 0; i < len; i++) {
			char tempCh=num.charAt(i);
//			System.out.println(tempCh+"="+(int)tempCh);
			//最小从‘A’开始
			BigInteger numIndex=new BigInteger(""+(int)tempCh);
			if (0 < maxFormat) {
				if (isFull) {
					showToast(mContext.getString(R.string.now_minHex_head)+" "+getMinSupport(num)+" "+mContext.getString(R.string.now_minHex_tail));
				}else {
					showToast(mContext.getString(R.string.now_minHex_head)+" "+getMinSupport(num.toUpperCase())+" "+mContext.getString(R.string.now_minHex_tail));
				}
				return "0";
			}
			if (numIndex.compareTo(fromInteger) > 0) {
//				System.err.println("error input! "+"tempCh="+tempCh+" i= "+i);
				if (isFull) {
					showToast(mContext.getString(R.string.now_minHex_head)+" "+getMinSupport(num)+" "+mContext.getString(R.string.now_minHex_tail));
				}else {
					showToast(mContext.getString(R.string.now_minHex_head)+" "+getMinSupport(num.toUpperCase())+" "+mContext.getString(R.string.now_minHex_tail));
				}
				return "0";
			}else {
				
			}
			
			BigDecimal baseDecimal=(BigDecimal.ONE.divide(new BigDecimal(from).pow(i+1),10,BigDecimal.ROUND_HALF_DOWN));
			if (tempCh >='0' && tempCh <= '9') {
//				System.out.println(baseDecimal);
				ruesltNum=ruesltNum.add(baseDecimal.multiply(new BigDecimal(""+tempCh)));
//				System.out.println("i="+i+" ruesltNum0="+ruesltNum);
			}else {
				ruesltNum=ruesltNum.add(baseDecimal.multiply(new BigDecimal(""+(tempCh-55))));
//				System.out.println("i="+i+" ruesltNum1="+new BigInteger(""+(tempCh-55)));
			}
			result.append(tempCh);
		}
		System.out.println("十进制数="+ruesltNum);
		return ruesltNum.toString();
	}
	
	/**
	 * 任意进制整数转换成10进制整数
	 * @param num
	 * @param from
	 * @param to
	 * @param isFull
	 * @return
	 */
	public String anyToDecimal(String num, String from, boolean isFull, boolean hasFloat){
		if (num == null || num=="" || stringErr(from)) {
			System.out.println("to is 0");
			return "0";
		}
		BigInteger fromInteger=new BigInteger(from);
		if (fromInteger.compareTo(new BigInteger("1")) >0 && fromInteger.compareTo(new BigInteger("11"))<0) {
			fromInteger=fromInteger.add(new BigInteger("47"));//最大位的码数
		}else {
			fromInteger=fromInteger.add(new BigInteger("54"));//最大位的码数
		}
		
		StringBuilder result=new StringBuilder();
		int len=num.length();
		BigInteger ruesltNum=BigInteger.ZERO;
		for (int i = 0; i < len; i++) {
			char tempCh=num.charAt(i);
//			System.out.println(tempCh+"="+(int)tempCh);
			//最小从‘A’开始
			BigInteger numIndex=new BigInteger(""+(int)tempCh);
			if (numIndex.compareTo(fromInteger) > 0) {
//				System.err.println("error input! "+"tempCh="+tempCh+" i= "+i);
				String maxsString="";
				if (isFull) {
					maxsString=getMinSupport(num);
				}else {
					maxsString=getMinSupport(num.toUpperCase());
				}
				if (!hasFloat) {
					showToast(mContext.getString(R.string.now_minHex_head)+" "+maxsString+" "+mContext.getString(R.string.now_minHex_tail));
				}
				return "0";
			}else {
				
			}
			
			if (tempCh >='0' && tempCh <= '9') {
				ruesltNum=ruesltNum.add(new BigInteger(from).pow(len-i-1).multiply(new BigInteger(""+tempCh)));
//				System.out.println("i="+i+" ruesltNum0="+ruesltNum);
			}else {
				ruesltNum=ruesltNum.add(new BigInteger(from).pow(len-i-1).multiply(new BigInteger(""+(tempCh-55))));
//				System.out.println("i="+i+" ruesltNum1="+new BigInteger(""+(tempCh-55)));
			}
			result.append(tempCh);
		}
		System.out.println("十进制数="+ruesltNum);
		return ruesltNum.toString().trim();
	}
	
	int maxFormat=0;
	public String getMinSupport(String num) {
		int len=num.length();
		int max=0;
		for (int i = 0; i < len; i++) {
			char ch=num.charAt(i);
			if (ch>max) {
				max=ch;
			}
		}
		int maxString=0;
		if (max > 64) {
			maxString=(max-54);
		}else {
			maxString=(max-47);
		}
		if (maxString < maxFormat) {
			return maxFormat+"";
		}else {
			maxFormat=maxString;
			return maxString+"";
		}
		
	}
	
	public void clean() {
		biString=new StringBuilder();
		floatString=new StringBuilder();
	}
	
	public int genNumber(String string) {
		if (string == null) {
			return -1;
		}
//		return Integer.parseInt(string.split(mContext.getString(R.string.pice))[0]);
		return Integer.parseInt(string);
	}
	
	private void showToast(final String text) {
		((Activity)mContext).runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	public static String getMaxChar(String num){
		if (num == null || num.length()<=0|| num =="0"|| num =="\0"|| num =="1"||num ==" "||num =="|"||num =="" ) {
			return "null";
		}
		System.out.println(num);
		BigInteger bigInteger=new BigInteger(num);
//		int temp=Integer.parseInt(num != null && num!= "" && num.length()>0?num:"0");
		int temp=bigInteger.intValue();
		if (temp>=0 && temp <= 10) {
			return temp+"";
		}else {
			return (char)(temp+54)+"";
		}
	}

	
}
