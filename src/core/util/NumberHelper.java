/**
 * <p>Title: 数字常用工具方法类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 源天软件</p>
 * @author Rockey 2006-4-10
 * @version 1.0
 */
package core.util;

import java.util.Random;

/**
 * 数字常用工具方法类
 */
public final class NumberHelper {

	/**
	 * 将输入的整数转成指定长度的字符串,不足的用0填充
	 * 
	 * @param iIn
	 *            需要填充0的整数
	 * @param iLength
	 *            转换后的字符串的长度
	 * @return 用0填充过的指定长度的字符串
	 */
	public static String add0(int iIn, int iLength) {
		long lv = (long) Math.pow(10, iLength);
		if (lv < iIn)
			return String.valueOf(iIn);
		return String.valueOf(lv + iIn).substring(1);
	}

	/**
	 * 
	 * @param strValue
	 * @return
	 */
	public static int string2Int(String strValue) {
		return string2Int(strValue, -1);
	}

	/**
	 * 
	 * @param strValue
	 * @param defValue
	 * @return
	 */
	public static int string2Int(String strValue, int defValue) {
		try {
			return Integer.parseInt(strValue);
		} catch (Exception ex) {
			return defValue;
		}
	}
	
	/**
	 * 
	 * @param strValue
	 * @param defValue
	 * @return
	 */
	public static long string2Long(String strValue, int defValue) {
		try {
			return Long.valueOf(strValue);
		} catch (Exception ex) {
			return defValue;
		}
	}

	public static int string2Int(Object obj, int defValue) {
		if (obj == null) {
			return defValue;
		}
		try {
			return Integer.parseInt(obj.toString());
		} catch (Exception ex) {
			return defValue;
		}
	}

	/**
	 * 判断一个字符串（11.0）是否为整数，是则返回整数，否则返回-1
	 * 
	 * @param strValue
	 * @return
	 */
	public static int string2Int2(String strValue) {
		try {
			if (StringHelper.isEmpty(strValue)) {
				return 0;
			}
			Double dvalue = Double.valueOf(strValue);
			int ivalue = dvalue.intValue();
			if (dvalue == ivalue) {
				return ivalue;
			}
		} catch (Exception ex) {
			return -1;
		}
		return -1;
	}

	/**
	 * 判断一个字符串（11.0）是否为整数，是则返回整数，否则返回值
	 * 
	 * @param strValue
	 * @return
	 */
	public static int string2Int2(String strValue, int defValue) {
		try {
			if (strValue == null) {
				return 0;
			}
			Double dvalue = Double.valueOf(strValue);
			int ivalue = dvalue.intValue();
			if (dvalue == ivalue) {
				return ivalue;
			}
		} catch (Exception ex) {
			return defValue;
		}

		return defValue;
	}

	public static Integer getIntegerValue(Object strValue, int defValue) {
		try {
			return Integer.valueOf(StringHelper.null2String(strValue));
		} catch (Exception ex) {
			return new Integer(defValue);
		}
	}

	public static Integer getIntegerValue(Object strValue) {
		return getIntegerValue(strValue, 0);
	}

	/**
	 * 根据boolean值返回 0和1
	 * 
	 * @param bstr
	 *            boolean 型的字符串
	 * @param defValue
	 *            当为true时返回的short值 0或者1
	 * @return
	 */
	public static short getShortbyboolean(String bstr, int defValue) {
		if ("true".equalsIgnoreCase(bstr)) {
			return (short) defValue;
		} else {
			return (short) (1 - defValue);
		}
	}

	/**
	 * 根据String值的0和1返回Short的 0和1
	 * 
	 * @param bstr
	 *            0和1
	 * @param defValue
	 *            当不为数字时返回的Short
	 * @return
	 */
	public static Short getShortbyStr(String bstr, String defValue) {
		try {
			return Short.valueOf(bstr);
		} catch (NumberFormatException e) {
			return Short.valueOf(defValue);
		}
	}

	/**
	 * 
	 * @param strValue
	 * @return
	 */
	public static float string2Float(String strValue) {
		return string2Float(strValue, -1);
	}

	/**
	 * 
	 * @param strValue
	 * @param defValue
	 * @return
	 */
	public static float string2Float(String strValue, float defValue) {
		try {
			return Float.parseFloat(strValue);
		} catch (Exception ex) {
			return defValue;
		}
	}

	/**
	 * 
	 * @param strValue
	 * @return
	 */
	public static double string2Double(String strValue) {
		return string2Double(strValue, -1);
	}

	// 金额每3位加逗号
	public static String moneyAddComma(double money) {
		return new java.text.DecimalFormat("#,##0.00").format(money);
	}

	// 金额每3位加逗号
	public static String moneyAddComma(String money) {
		double tempmoney = string2Double(money, 0);
		return new java.text.DecimalFormat("#,##0.00").format(tempmoney);
	}

	/**
	 * 
	 * @param strValue
	 * @param defValue
	 * @return
	 */
	public static double string2Double(String strValue, double defValue) {
		try {
			return Double.parseDouble(strValue);
		} catch (Exception ex) {
			return defValue;
		}
	}

	public static double string2Double(Object obj, double defValue) {
		if (obj == null) {
			return defValue;
		}
		try {
			return Double.parseDouble(obj.toString());
		} catch (Exception ex) {
			return defValue;
		}
	}

	public static int getRandomInt(int min, int max) {

		Random random = new Random();
		return Math.abs(random.nextInt()) % (max - min) + min;
	}

	/**
	 * 浮点数四舍五入为整型
	 * */
	public static int float2int(float fValue) {
		int intValue = (int) fValue;
		float tFloat = fValue - intValue;
		intValue = (int) (tFloat + fValue);
		return intValue;
	}
}
