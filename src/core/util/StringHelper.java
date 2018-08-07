package core.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public final class StringHelper {
	private static final char[] CHARS = "abcdehjkmnpqrsuvwxyzhs0123456789ABCDEFGHIJKMLNOPQRSTUVWXYZ"
			.toCharArray();

	public static String null2String(String strIn) {
		return strIn == null ? "" : strIn;
	}
	
	public static boolean isNumeric(String str){  
	    Pattern pattern = Pattern.compile("[0-9]*");  
	    return pattern.matcher(str).matches();     
	}  

	/**
	 * 根据长度返回随机数
	 * 
	 * @param length
	 *            随机数长度
	 * @return
	 */
	public static String getRand(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(CHARS[new Random().nextInt(CHARS.length)]);
		}
		return sb.toString();
	}

	public static String[] split(String srcString, String regex)
			throws Exception {
		if (!(regex != null && regex.length() > 0)) {
			throw new Exception("请输入分隔符！");
		}
		StringTokenizer stk = null;
		if (srcString.length() == 0) {
			return new String[] { srcString };
		} else {
			if (srcString.lastIndexOf(regex) > 0) {
				if (srcString.lastIndexOf(regex) == (srcString.length() - 1)) {
					stk = new StringTokenizer(srcString + " ", regex);
				} else {
					stk = new StringTokenizer(srcString, regex);
				}
			} else {
				return new String[] { srcString };
			}
		}
		String[] array = new String[stk.countTokens()];
		int index = 0;
		while (stk.hasMoreTokens()) {
			array[index++] = stk.nextToken();
		}
		if (srcString.lastIndexOf(regex) > 0) {
			if (srcString.lastIndexOf(regex) == (srcString.length() - 1)) {
				array[array.length - 1] = "";
			}
		}
		try {
			return array;
		} finally {
			stk = null;
			array = null;
		}
	}

	/**
	 * 格式化字符串，
	 * 
	 * @param temp
	 *            要格式化的原字符串
	 * @param format
	 *            格式代码 如#,##0.00
	 * @return
	 */
	public static String format(String temp, String format) {
		if (StringHelper.isEmpty(temp) || StringHelper.isEmpty(format)) {
			return "";
		}
		return new java.text.DecimalFormat(format).format(
				Double.parseDouble(temp)).replace(",", ".");
	}

	/**
	 * 格式化字符串，产生10位流水号 00.000.000
	 * 
	 * @param temp
	 *            要格式化的原字符串
	 * @return
	 */
	public static String format(String temp) {
		if (StringHelper.isEmpty(temp)) {
			return "";
		} else if (temp.length() == 8) {
			return temp.substring(0, 2) + "." + temp.substring(2, 5) + "."
					+ temp.substring(5, 8);
		} else {
			return temp;
		}
	}

	public static String null2String(Object strIn) {
		return strIn == null ? "" : "" + strIn;
	}
	
	public static String null2ZeroString(Object strIn) {
		return strIn == null ? "0" : "" + strIn;
	}

	public static String null2String(Object strIn, String defstr) {
		return strIn == null ? defstr : "" + strIn;
	}

	public static String GBK2ISO(String s) {
		try {
			return new String(s.getBytes("GBK"), "ISO8859_1");
		} catch (UnsupportedEncodingException ex) {
			return null;
		}
	}

	public static String ISO2GBK(String s) {
		try {
			return new String(s.getBytes("ISO8859_1"), "GBK");
		} catch (UnsupportedEncodingException ex) {
			return null;
		}
	}

	public static ArrayList string2ArrayList2(String strIn, String strDim) {
		strIn = null2String(strIn);
		strDim = null2String(strDim);
		ArrayList strList = new ArrayList();

		String[] sr2 = strIn.split(strDim);
		for (int i = 0; i < sr2.length; i++) {
			strList.add(null2String(sr2[i]));
		}
		if (strIn.endsWith(strDim))
			strList.add("");

		return strList;
	}

	public static ArrayList string2ArrayList(String strIn, String strDim) {
		return string2ArrayList(strIn, strDim, false);
	}

	public static ArrayList string2ArrayList(String strIn, String strDim,
			boolean bReturndim) {
		strIn = null2String(strIn);
		strDim = null2String(strDim);
		ArrayList strList = new ArrayList();
		StringTokenizer strtoken = new StringTokenizer(strIn, strDim,
				bReturndim);
		while (strtoken.hasMoreTokens()) {
			strList.add(strtoken.nextToken());
		}
		return strList;
	}

	public static String[] string2Array(String strIn, String strDim) {
		return string2Array(strIn, strDim, false);
	}

	public static String[] string2Array(String strIn, String strDim,
			boolean bReturndim) {
		ArrayList strlist = string2ArrayList(strIn, strDim, bReturndim);
		int strcount = strlist.size();
		String[] strarray = new String[strcount];
		for (int i = 0; i < strcount; i++) {
			strarray[i] = (String) strlist.get(i);
		}
		return strarray;
	}

	public static boolean contains(Object a[], Object s) {
		if (a == null || s == null) {
			return false;
		}
		for (int i = 0; i < a.length; i++) {
			if (a[i] != null && a[i].equals(s)) {
				return true;
			}
		}
		return false;
	}

	public static String replaceChar(String s, char c1, char c2) {
		if (s == null) {
			return s;
		}

		char buf[] = s.toCharArray();
		for (int i = 0; i < buf.length; i++) {
			if (buf[i] == c1) {
				buf[i] = c2;
			}
		}
		return String.valueOf(buf);
	}

	public static String replaceString(String strSource, String strFrom,
			String strTo) {
		if (strSource == null) {
			return null;
		}
		int i = 0;
		if ((i = strSource.indexOf(strFrom, i)) >= 0) {
			char[] cSrc = strSource.toCharArray();
			char[] cTo = strTo.toCharArray();
			int len = strFrom.length();
			StringBuffer buf = new StringBuffer(cSrc.length);
			buf.append(cSrc, 0, i).append(cTo);
			i += len;
			int j = i;
			while ((i = strSource.indexOf(strFrom, i)) > 0) {
				buf.append(cSrc, j, i - j).append(cTo);
				i += len;
				j = i;
			}
			buf.append(cSrc, j, cSrc.length - j);
			return buf.toString();
		}
		return strSource;
	}

	public static String replaceStringFirst(String sou, String s1, String s2) {
		int idx = sou.indexOf(s1);
		if (idx < 0) {
			return sou;
		}
		return sou.substring(0, idx) + s2 + sou.substring(idx + s1.length());
	}

	public static String replaceRange(String sentence, String oStart,
			String oEnd, String rWord, boolean matchCase) {
		int sIndex = -1;
		int eIndex = -1;
		if (matchCase) {
			sIndex = sentence.indexOf(oStart);
		} else {
			sIndex = sentence.toLowerCase().indexOf(oStart.toLowerCase());
		}
		if (sIndex == -1 || sentence == null || oStart == null || oEnd == null
				|| rWord == null) {
			return sentence;
		} else {
			if (matchCase) {
				eIndex = sentence.indexOf(oEnd, sIndex);
			} else {
				eIndex = sentence.toLowerCase().indexOf(oEnd.toLowerCase(),
						sIndex);
			}
			String newStr = null;
			if (eIndex > -1) {
				newStr = sentence.substring(0, sIndex) + rWord
						+ sentence.substring(eIndex + oEnd.length());
			} else {
				newStr = sentence.substring(0, sIndex) + rWord
						+ sentence.substring(sIndex + oStart.length());
			}
			return replaceRange(newStr, oStart, oEnd, rWord, matchCase);
		}
	}

	// add from org.apache.commons.lang.StringUtils by steven.yu
	// Empty checks
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Checks if a String is empty ("") or null.
	 * </p>
	 * 
	 * <pre>
	 *  StringUtils.isEmpty(null)      = true
	 *  StringUtils.isEmpty(&quot;&quot;)        = true
	 *  StringUtils.isEmpty(&quot; &quot;)       = false
	 *  StringUtils.isEmpty(&quot;bob&quot;)     = false
	 *  StringUtils.isEmpty(&quot;  bob  &quot;) = false
	 * </pre>
	 * 
	 * <p>
	 * NOTE: This method changed in Lang version 2.0. It no longer trims the
	 * String. That functionality is available in isBlank().
	 * </p>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is empty or null
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.equalsIgnoreCase("null") || str.length() == 0;
	}

	public static boolean isNotEmpty(String str) {
	    return !isEmpty(str);
	}
	public static boolean isID(String str) {
		return !isEmpty(str) && str.length() == 32;
	}

	/**
	 * <p>
	 * Removes control characters (char &lt;= 32) from both ends of this String
	 * returning <code>null</code> if the String is empty ("") after the trim or
	 * if it is <code>null</code>.
	 * 
	 * <p>
	 * The String is trimmed using {@link String#trim()}. Trim removes start and
	 * end characters &lt;= 32. To strip whitespace use
	 * {@link #stripToNull(String)}.
	 * </p>
	 * 
	 * <pre>
	 *  StringUtils.trimToNull(null)          = null
	 *  StringUtils.trimToNull(&quot;&quot;)            = null
	 *  StringUtils.trimToNull(&quot;     &quot;)       = null
	 *  StringUtils.trimToNull(&quot;abc&quot;)         = &quot;abc&quot;
	 *  StringUtils.trimToNull(&quot;    abc    &quot;) = &quot;abc&quot;
	 * </pre>
	 * 
	 * @param str
	 *            the String to be trimmed, may be null
	 * @return the trimmed String, <code>null</code> if only chars &lt;= 32,
	 *         empty or null String input
	 * @since 2.0
	 */
	public static String trimToNull(String str) {
		String ts = trim(str);
		return isEmpty(ts) ? null : ts;
	}

	/**
	 * <p>
	 * Removes control characters (char &lt;= 32) from both ends of this String,
	 * handling <code>null</code> by returning <code>null</code>.
	 * </p>
	 * 
	 * <p>
	 * The String is trimmed using {@link String#trim()}. Trim removes start and
	 * end characters &lt;= 32. To strip whitespace use {@link #strip(String)}.
	 * </p>
	 * 
	 * <p>
	 * To trim your choice of characters, use the {@link #strip(String, String)}
	 * methods.
	 * </p>
	 * 
	 * <pre>
	 *  StringUtils.trim(null)          = null
	 *  StringUtils.trim(&quot;&quot;)            = &quot;&quot;
	 *  StringUtils.trim(&quot;     &quot;)       = &quot;&quot;
	 *  StringUtils.trim(&quot;abc&quot;)         = &quot;abc&quot;
	 *  StringUtils.trim(&quot;    abc    &quot;) = &quot;abc&quot;
	 * </pre>
	 * 
	 * @param str
	 *            the String to be trimmed, may be null
	 * @return the trimmed string, <code>null</code> if null String input
	 */
	public static String trim(String str) {
		return str == null ? null : str.trim();
	}

	public static boolean parseBoolean(String param) {
		if (isEmpty(param)) {
			return false;
		}
		switch (param.charAt(0)) {
		case '1':
		case 'y':
		case 'Y':
		case 't':
		case 'T':
			return true;
		}
		return false;
	}

	public static String getRandomStr(int length) {
		String psd = "";
		char c;
		int i;
		int isnum = 0;
		for (int j = 0; j < length; j++) {
			if (isnum == 0) {
				isnum = 1;
				c = (char) (Math.random() * 26 + 'a');
				psd += c;
			} else {
				isnum = 0;
				c = (char) (Math.random() * 10 + '0');
				psd += c;
			}
		}
		return psd;
	}

	public static String fromDB(String s) {
		char c[] = s.toCharArray();
		char ch;
		int i = 0;
		StringBuffer buf = new StringBuffer();

		while (i < c.length) {
			ch = c[i++];
			if (ch == '\"') {
				buf.append("\\\"");
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	public static String Array2String(String[] strIn, String strDim) {
		StringBuffer strOut = new StringBuffer();
		for (String tempStr : strIn) {
			strOut.append(tempStr).append(strDim);
		}
		if (strOut.length() > 0)
			strOut.delete(strOut.lastIndexOf(strDim), strOut.length());
		return strOut.toString();
	}

	public static String Array2String(Object[] strIn, String strDim) {

		StringBuffer strOut = new StringBuffer();
		for (Object tempStr : strIn) {
			strOut.append(tempStr).append(strDim);
		}
		if (strOut.length() > 0)
			strOut.delete(strOut.lastIndexOf(strDim), strOut.length());
		return strOut.toString();
	}

	public static String ArrayList2String(ArrayList strIn, String strDim) {
		StringBuffer strOut = new StringBuffer();
		for (Object o : strIn) {
			strOut.append(o.toString()).append(strDim);
		}
		if (strOut.length() > 0)
			strOut.delete(strOut.lastIndexOf(strDim), strOut.length());
		return strOut.toString();
	}
	
	public static String List2String(List strIn, String strDim) {
		StringBuffer strOut = new StringBuffer();
		Map tempMap;
		for(int i=0;i<strIn.size();i++){
			tempMap=(Map) strIn.get(i);
			
			strOut.append(StringHelper.null2String(tempMap.get("objname"))).append(strDim);
		}
		if (strOut.length() > 0)
			strOut.delete(strOut.lastIndexOf(strDim), strOut.length());
		return strOut.toString();
	}

	public static String fillValuesToString(String str, Hashtable ht) {
		return fillValuesToString(str, ht, '$');
	}

	public static String fillValuesToString(String str, Hashtable ht,
			char VARIABLE_PREFIX) {
		String replacement="\\"+String.valueOf(VARIABLE_PREFIX)+"\\w{32}\\"+String.valueOf(VARIABLE_PREFIX)+"";
		Pattern p = Pattern.compile(replacement);
        Matcher m = p.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
        		String key=m.group().replace("$","");
        		if(ht.containsKey(key)){
        			m.appendReplacement(sb,((String)ht.get(key)).replace("\\", "\\\\").replace(
                            "$", "\\$"));
        			continue;
        		}
        		m.appendReplacement(sb, "$0");
        }
        m.appendTail(sb);
		return sb.toString();
	}

	public static String formatMutiIDs(String ids) {
		StringBuffer ret = new StringBuffer("''");
		ArrayList arrayids = string2ArrayList(ids, ",");
		for (int i = 0; i < arrayids.size(); i++) {
			String _id = null2String(String.valueOf(arrayids.get(i))).trim();
			ret.append(",'").append(_id).append("'");
		}
		return ret.toString();
	}

	public static String lift(String arg, int length) {
		int sLength = arg.trim().length();
		if (length < 1 || length > sLength) {
			return arg.trim();
		} else {
			return arg.trim().substring(0, length);
		}

	}

	public static String getDecodeStr(String strIn) {
		if (isEmpty(strIn))
			return "";
		String strTemp = "";
		for (int i = 0; i < strIn.length(); i++) {
			char charTemp = strIn.charAt(i);
			switch (charTemp) {
			case 124: // '~'
				String strTemp2 = strIn.substring(i + 1, i + 3);
				strTemp = strTemp + (char) Integer.parseInt(strTemp2, 16);
				i += 2;
				break;

			case 94: // '^'
				String strTemp3 = strIn.substring(i + 1, i + 5);
				strTemp = strTemp + (char) Integer.parseInt(strTemp3, 16);
				i += 4;
				break;

			default:
				strTemp = strTemp + charTemp;
				break;
			}
		}

		return strTemp;
	}

	public static String getEncodeStr(String strIn) {
		if (isEmpty(strIn))
			return "";
		String strOut = "";
		for (int i = 0; i < strIn.length(); i++) {
			int iTemp = strIn.charAt(i);
			if (iTemp > 255) {
				String strTemp2 = Integer.toString(iTemp, 16);
				for (int iTemp2 = strTemp2.length(); iTemp2 < 4; iTemp2++)
					strTemp2 = "0" + strTemp2;

				strOut = strOut + "^" + strTemp2;
			} else {
				if (iTemp < 48 || iTemp > 57 && iTemp < 65 || iTemp > 90
						&& iTemp < 97 || iTemp > 122) {
					String strTemp2 = Integer.toString(iTemp, 16);
					for (int iTemp2 = strTemp2.length(); iTemp2 < 2; iTemp2++)
						strTemp2 = "0" + strTemp2;

					strOut = strOut + "|" + strTemp2;
				} else {
					strOut = strOut + strIn.charAt(i);
				}
			}
		}

		return strOut;
	}
	
	public static String getfloatToString(String value) {
		int index = value.indexOf("E");
		if (index == -1) {
			return value;
		}

		int num = Integer.parseInt(value.substring(index + 1, value.length()));
		value = value.substring(0, index);
		index = value.indexOf(".");
		value = value.substring(0, index)
				+ value.substring(index + 1, value.length());
		String number = value;
		if (value.length() <= num) {
			for (int i = 0; i < num - value.length(); i++) {
				number += "0";
			}
		} else {
			number = number.substring(0, num + 1) + "."
					+ number.substring(num + 1) + "0";
		}
		return number;
	}

	public static String getfloatToString2(String value) { // 保留两位小数
		value = getfloatToString(value);
		int index = value.indexOf(".");
		if (index == -1)
			return value;
		String value1 = value.substring(0, index);
		String value2 = value.substring(index + 1, value.length()) + "00";
		if (Integer.parseInt(value2.substring(0, 2)) == 0)
			return value1;
		else
			return value1 + "." + value2.substring(0, 2);
	}

	public static String numberFormat2(String value) { // 保留两位小数
		double num = NumberHelper.string2Double(
				StringHelper.null2String(value), 0.0);
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(num);
	}

	public static String numberFormat2(Double value) { // 保留两位小数
		double num = NumberHelper.string2Double(
				StringHelper.null2String(value), 0.0);
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(num);
	}

	public static String numberFormat2(double value) { // 保留两位小数
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(value);
	}

	public static String getRequestHost(HttpServletRequest request) {
		if (request == null) {
			return "";
		} else {
			return (null2String(request.getHeader("Host"))).trim();
		}
	}

	// 以 java 实现 escape unescape
	private final static String[] hex = { "00", "01", "02", "03", "04", "05",
			"06", "07", "08", "09", "0A", "0B", "0C", "0D", "0E", "0F", "10",
			"11", "12", "13", "14", "15", "16", "17", "18", "19", "1A", "1B",
			"1C", "1D", "1E", "1F", "20", "21", "22", "23", "24", "25", "26",
			"27", "28", "29", "2A", "2B", "2C", "2D", "2E", "2F", "30", "31",
			"32", "33", "34", "35", "36", "37", "38", "39", "3A", "3B", "3C",
			"3D", "3E", "3F", "40", "41", "42", "43", "44", "45", "46", "47",
			"48", "49", "4A", "4B", "4C", "4D", "4E", "4F", "50", "51", "52",
			"53", "54", "55", "56", "57", "58", "59", "5A", "5B", "5C", "5D",
			"5E", "5F", "60", "61", "62", "63", "64", "65", "66", "67", "68",
			"69", "6A", "6B", "6C", "6D", "6E", "6F", "70", "71", "72", "73",
			"74", "75", "76", "77", "78", "79", "7A", "7B", "7C", "7D", "7E",
			"7F", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89",
			"8A", "8B", "8C", "8D", "8E", "8F", "90", "91", "92", "93", "94",
			"95", "96", "97", "98", "99", "9A", "9B", "9C", "9D", "9E", "9F",
			"A0", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "AA",
			"AB", "AC", "AD", "AE", "AF", "B0", "B1", "B2", "B3", "B4", "B5",
			"B6", "B7", "B8", "B9", "BA", "BB", "BC", "BD", "BE", "BF", "C0",
			"C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "CA", "CB",
			"CC", "CD", "CE", "CF", "D0", "D1", "D2", "D3", "D4", "D5", "D6",
			"D7", "D8", "D9", "DA", "DB", "DC", "DD", "DE", "DF", "E0", "E1",
			"E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9", "EA", "EB", "EC",
			"ED", "EE", "EF", "F0", "F1", "F2", "F3", "F4", "F5", "F6", "F7",
			"F8", "F9", "FA", "FB", "FC", "FD", "FE", "FF" };
	private final static byte[] val = { 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x00, 0x01,
			0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F };

	public static String escape(String s) {
		if (isEmpty(s))
			return "";
		StringBuffer sbuf = new StringBuffer();
		int len = s.length();
		for (int i = 0; i < len; i++) {
			int ch = s.charAt(i);
			if (ch == ' ') { // space : map to '+'
				sbuf.append('+');
			} else if ('A' <= ch && ch <= 'Z') { // 'A'..'Z' : as it was
				sbuf.append((char) ch);
			} else if ('a' <= ch && ch <= 'z') { // 'a'..'z' : as it was
				sbuf.append((char) ch);
			} else if ('0' <= ch && ch <= '9') { // '0'..'9' : as it was
				sbuf.append((char) ch);
			} else if (ch == '-'
					|| ch == '_' // unreserved : as it was
					|| ch == '.' || ch == '!' || ch == '~' || ch == '*'
					|| ch == '\'' || ch == '(' || ch == ')') {
				sbuf.append((char) ch);
			} else if (ch <= 0x007F) { // other ASCII : map to %XX
				sbuf.append('%');
				sbuf.append(hex[ch]);
			} else { // unicode : map to %uXXXX
				sbuf.append('%');
				sbuf.append('u');
				sbuf.append(hex[(ch >>> 8)]);
				sbuf.append(hex[(0x00FF & ch)]);
			}
		}
		return sbuf.toString();
	}

	public static String unescape(String s) {
		if (isEmpty(s))
			return "";
		StringBuffer sbuf = new StringBuffer();
		int i = 0;
		int len = s.length();
		while (i < len) {
			int ch = s.charAt(i);
			if (ch == '+') { // + : map to ' '
				sbuf.append(' ');
			} else if ('A' <= ch && ch <= 'Z') { // 'A'..'Z' : as it was
				sbuf.append((char) ch);
			} else if ('a' <= ch && ch <= 'z') { // 'a'..'z' : as it was
				sbuf.append((char) ch);
			} else if ('0' <= ch && ch <= '9') { // '0'..'9' : as it was
				sbuf.append((char) ch);
			} else if (ch == '-'
					|| ch == '_' // unreserved : as it was
					|| ch == '.' || ch == '!' || ch == '~' || ch == '*'
					|| ch == '\'' || ch == '(' || ch == ')') {
				sbuf.append((char) ch);
			} else if (ch == '%') {
				int cint = 0;
				if ('u' != s.charAt(i + 1)) { // %XX : map to ascii(XX)
					cint = (cint << 4) | val[s.charAt(i + 1)];
					cint = (cint << 4) | val[s.charAt(i + 2)];
					i += 2;
				} else { // %uXXXX : map to unicode(XXXX)
					cint = (cint << 4) | val[s.charAt(i + 2)];
					cint = (cint << 4) | val[s.charAt(i + 3)];
					cint = (cint << 4) | val[s.charAt(i + 4)];
					cint = (cint << 4) | val[s.charAt(i + 5)];
					i += 5;
				}
				sbuf.append((char) cint);
			}
			i++;
		}
		return sbuf.toString();
	}

	/**
	 * 转换第一个字母为大写
	 * */
	public static String changeFirstLetter(String string) {
		if (string == null) {
			return null;
		} else {
			String c = string.substring(0, 1);
			String d = c.toUpperCase();
			String returnString = string.replaceFirst(c, d);
			return returnString;
		}

	}

	/**
	 * Converts some important chars (int) to the corresponding html string
	 */
	public static String conv2Html(int i) {
		if (i == '&')
			return "&amp;";
		else if (i == '<')
			return "&lt;";
		else if (i == '>')
			return "&gt;";
		else if (i == '"')
			return "&quot;";
		else
			return "" + (char) i;
	}

	/**
	 * Converts a normal string to a html conform string
	 */
	public static String conv2Html(String st) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < st.length(); i++) {
			buf.append(conv2Html(st.charAt(i)));
		}
		return buf.toString();
	}

	/**
	 * 移除开头，结尾，或中间的逗号
	 * 
	 * @param oldstationids
	 * @return
	 */
	public static String removeComma(String oldstationids) {
		if (!isEmpty(oldstationids)) {

			oldstationids = oldstationids.replaceAll(",+", ",");

			if (oldstationids.lastIndexOf(",") == oldstationids.length() - 1) {
				oldstationids = oldstationids.substring(0, oldstationids
						.lastIndexOf(","));
			}
			if (oldstationids.indexOf(",") == 0) {
				oldstationids = oldstationids.substring(1);
			}
		}
		return oldstationids;
	}

	/**
	 * 生成令牌
	 * 
	 * @param request
	 * @return
	 */
	public static String generateToken(HttpServletRequest request) {
		HttpSession session = request.getSession();
		try {
			byte id[] = session.getId().getBytes();
			byte now[] = new Long(System.currentTimeMillis()).toString()
					.getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(id);
			md.update(now);
			return (md.digest().toString());
		} catch (Exception e) {
			return (null);
		}
	}

	/**
	 * 将string中的回车、换行、空格转化为html代码
	 * 
	 * @param sStr
	 * @return
	 */
	public static String convertHtmlString(String sStr) {
		if (isEmpty(sStr)) {
			return "";
		}

		StringBuffer sTmp = new StringBuffer();
		int i = 0;
		while (i <= sStr.length() - 1) {
			// if (sStr.charAt(i) == '\n' || sStr.charAt(i) == '\r') {
			if (sStr.charAt(i) == '\n') {
				sTmp = sTmp.append("<br>");
			} else if (sStr.charAt(i) == ' ') {
				sTmp = sTmp.append(" ");
			} else {
				sTmp = sTmp.append(sStr.substring(i, i + 1));
			}
			i++;
		}
		return sTmp.toString();
	}

	// 打印信息
	public static void p(String printname, String printvalue) {
		System.out.println(StringHelper.null2String(printname) + "======="
				+ StringHelper.null2String(printvalue));
	}

	public static void p(String msg) {
		System.out.println(StringHelper.null2String(msg));
	}


	public static String getRandString(int length) {
		char[] CHARS = "abcdehjkmnpqrsuvwxyzhs0123456789ABCDEFGHIJKMLNOPQRSTUVWXYZ"
				.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(CHARS[new Random().nextInt(CHARS.length)]);
		}
		return sb.toString();
	}

	/**
	 * @param str
	 * @return 返回格式化的输入sql的字符，防止sql注入攻击。
	 */
	public static String FormatInputString(String str) {
		String strReturn = str;
		String word = "and|exec|insert|select|delete|update|chr|mid|master|or|truncate|char|drop|create|declare|join|'|<|>|--|Or|OR|oR";
		String[] keys = word.split("|");
		for (int i = 0; i < keys.length; i++) {
			if (str.indexOf(keys[i].toString()) > -1) {
				strReturn = strReturn.replace(keys[i].toString(), "");
			}
		}
		return strReturn;
	}

	/**
	 * 截取字符串长度
	 * 用于中文标题显示截取长度和英文显示相同长度，需支持字符gb2312
	 * @param s :待截取的字符串
	 * @param length :截取的长度(等同于全英文长度,一个汉字长度为2,英文和数字为1)
	 * @param ends:如果字节长度超出指定长度，将添加的后缀
	 */
	public  static String subLength(String s, int length,String ends) {
		if(s==null){
			return "";
		} 
		if(s.getBytes().length<length){
			return s;
		}
		String compilestr="[\\x00-\\xff]";
		Pattern p=Pattern.compile(compilestr);
		StringBuffer sb=new StringBuffer();
		int max=length;
		int n=0;
		for(int i=0;i<s.length();i++){
			String str=String.valueOf(s.charAt(i));
			if(p.matcher(str).matches()){
				n++;
			}else{
				n+=2;
			}
			if(n>max){
				break;
			}
			sb.append(str);
		} 
	    return sb.toString()+ends;
	}
	

	/**
	 * 将文件名转换为utf8字符串，用于文件下载时文件名称报头
	 * 
	 * @param s
	 * @return
	 */

	public static String toUTF8String(String s) {
		String strTitle = s.substring(0, s.lastIndexOf("."));
		String strExct = s.substring(s.lastIndexOf("."), s.length());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strTitle.length(); i++) {
			char c = strTitle.charAt(i);
			if (c >= 0 && c <= '\377') {
				if (sb.toString().length() < 147) {
					sb.append(c);
					continue;
				}
				sb.append("[1]");
				break;
			}
			byte b[];
			try {
				b = (new Character(c)).toString().getBytes("UTF-8");
			} catch (Exception ex) {
				System.out.println(ex);
				b = new byte[0];
			}
			if (sb.toString().length() < 140) {
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}

				continue;
			}
			sb.append("[1]");
			break;
		}

		return sb.toString() + strExct;

	}

	/**
	 * 获取
	 * 
	 * @param str
	 * @param prefix
	 * @param suffix
	 * @return
	 */
	public static List getContainStr(String str, String prefix, String suffix)
			throws ArrayIndexOutOfBoundsException {
		int pre = 0;
		int suf = 0;
		List list = new ArrayList();
		try {
			while ((pre = str.indexOf(prefix)) != -1
					&& (suf = str.indexOf(suffix)) != -1) {
				list.add(str.substring(pre + 1, suf));
				str = str.substring(suf + 1);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw e;
		}
		return list;
	}
	/**
	 * 用,分割字符串数组
	 * @param str
	 * @return
	 */
	public static String jion(String[] str){
		StringBuffer sb=new StringBuffer();
		for(String s:str){
			if(s==null){
				continue;
			}
			sb.append(s).append(",");
		}
		return sb.toString().replaceAll(",$", "");
	}
	
	/**
	 * 给UUID串加上单引号
	 * @param s
	 * @return
	 */
	public static String transform(String s) {
		StringBuffer sb = new StringBuffer("");
		Pattern pattern = Pattern.compile("[a-zA-Z0-9]{32}");
		Matcher matcher = pattern.matcher(s);
		while(matcher.find()) {
			sb.append(",'" + matcher.group() + "'");
		}
		return sb.toString().substring(1);
	}

    /**
     * 将数组转换成's1','s2'..，用于sql查询
     * 
     * @param strs
     * @return
     */
    public static String formatForSql(String[] strs) {
        StringBuffer temp = new StringBuffer("");
        if (strs != null) {
            for (int i = 0; i < strs.length; i++) {
                temp.append("'").append(strs[i]).append("'");
                if (i < strs.length - 1) {
                    temp.append(",");
                }
            }
        }
        return temp.toString();
    }
    
    /**
     * 此方法专为捷成项目设计，用于售后服务信息(地区)报表
     * 
     * @param diqu
     * @param isfinished 流程是否完成1：完成，0：未完成，2：都有
     * @param mark 是否需要子表参与
     * @return
     */
    public static String getFormDataSql(String date,String diqu,int isfinished,boolean mark){
    	if(isfinished==1&&mark){
    		return "(select * from ufz1j0i11355986713864 where requestid in (select w.id from workflowbase w,ufe5w7b31355984380074 u where w.createdate like'%"+date+"%' and w.pipeid='402881823bb629fb013bb7284dd103a4' and w.isdelete=0 and w.id=u.requestid and w.isfinished=1 and u.field020='"+diqu+"')) t";
    	}else if(isfinished==1&&!mark){
    		return "(select w.id,u.field018,u.field019,u.field027,u.field028,u.field030 from workflowbase w,ufe5w7b31355984380074 u where w.createdate like'%"+date+"%' and w.pipeid='402881823bb629fb013bb7284dd103a4' and w.isdelete=0 and w.id=u.requestid and w.isfinished=1 and u.field020='"+diqu+"') t";
    	}else if(isfinished==0&&mark){
    		return "(select * from ufz1j0i11355986713864 where requestid in (select w.id from workflowbase w,ufe5w7b31355984380074 u where w.createdate like'%"+date+"%' and w.pipeid='402881823bb629fb013bb7284dd103a4' and w.isdelete=0 and w.id=u.requestid and w.isfinished=0 and u.field020='"+diqu+"')) t";
    	}else if(isfinished==0&&!mark){
    		return "(select w.id,u.field018,u.field019,u.field027,u.field028,u.field030 from workflowbase w,ufe5w7b31355984380074 u where w.createdate like'%"+date+"%' and w.pipeid='402881823bb629fb013bb7284dd103a4' and w.isdelete=0 and w.id=u.requestid and w.isfinished=0 and u.field020='"+diqu+"') t";
    	}else if(isfinished==2&&mark){
    		return "(select * from ufz1j0i11355986713864 where requestid in (select w.id from workflowbase w,ufe5w7b31355984380074 u where w.createdate like'%"+date+"%' and w.pipeid='402881823bb629fb013bb7284dd103a4' and w.isdelete=0 and w.id=u.requestid and u.field020='"+diqu+"')) t";
    	}else{
    		return "(select w.id ,u.field018,u.field019,u.field027,u.field028,u.field030 from workflowbase w,ufe5w7b31355984380074 u where w.createdate like'%"+date+"%' and w.pipeid='402881823bb629fb013bb7284dd103a4' and w.isdelete=0 and w.id=u.requestid and u.field020='"+diqu+"') t";
    	}
    }
    
    /**
     * 此方法专为捷成项目设计，用于售后服务信息（服务人员）报表
     * 
     * @param diqu
     * @param isfinished 流程是否完成1：完成，0：未完成，2：都有
     * @param mark 是否需要子表参与
     * @return
     */
    public static String getFormDataSql1(String date, String humresid,int isfinished,boolean mark){
    	if(isfinished==1&&mark){
    		return "(select * from ufz1j0i11355986713864 where requestid in (select w.id from workflowbase w,ufe5w7b31355984380074 u where w.createdate like'%"+date+"%' and w.pipeid='402881823bb629fb013bb7284dd103a4' and w.isdelete=0 and w.id=u.requestid and w.isfinished=1)) t";
    	}else if(isfinished==1&&!mark){
    		return "(select w.id,u.field018,u.field019,u.field027,u.field028,u.field030 from workflowbase w,ufe5w7b31355984380074 u where w.createdate like'%"+date+"%' and w.pipeid='402881823bb629fb013bb7284dd103a4' and w.isdelete=0 and w.id=u.requestid and w.isfinished=1) t";
    	}else if(isfinished==0&&mark){
    		return "(select * from ufz1j0i11355986713864 where requestid in (select w.id from workflowbase w,ufe5w7b31355984380074 u where w.createdate like'%"+date+"%' and w.pipeid='402881823bb629fb013bb7284dd103a4' and w.isdelete=0 and w.id=u.requestid and w.isfinished=0)) t";
    	}else if(isfinished==0&&!mark){
    		return "(select w.id,u.field018,u.field019,u.field027,u.field028,u.field030 from workflowbase w,ufe5w7b31355984380074 u where w.createdate like'%"+date+"%' and w.pipeid='402881823bb629fb013bb7284dd103a4' and w.isdelete=0 and w.id=u.requestid and w.isfinished=0) t";
    	}else if(isfinished==2&&mark){
    		return "(select * from ufz1j0i11355986713864 where requestid in (select w.id from workflowbase w,ufe5w7b31355984380074 u where w.createdate like'%"+date+"%' and w.pipeid='402881823bb629fb013bb7284dd103a4' and w.isdelete=0 and w.id=u.requestid)) t";
    	}else{
    		return "(select w.id ,u.field018,u.field019,u.field027,u.field028,u.field030 from workflowbase w,ufe5w7b31355984380074 u where w.createdate like'%"+date+"%' and w.pipeid='402881823bb629fb013bb7284dd103a4' and w.isdelete=0 and w.id=u.requestid) t";
    	}
    }
    /**
     * 用于获取百分比
     * 
     * @param a
     * @param b
     * @return
     */
    public static String getBaifenbi(int a,int b){
    	String temp="0.0000";
    	String res="00.00%";
    	float mark=0;
    	if(b!=0){
    		float c=(float)a/(float)b;
    		if(c+"".length()<6){
    			temp= c+"000";
    		}else if(c+"".length()>6){
    			temp= c+"".substring(0,6);
    		}else{
    			temp= c+"";
    		}
    		mark=c;
    	}
    	if(mark==1){
    		res="1"+temp.substring(2,4)+"."+temp.substring(4,6)+"%";
    	}else if(mark<0.1){
    		res=temp.substring(3,4)+"."+temp.substring(4,6)+"%";
    	}else{
    		res=temp.substring(2,4)+"."+temp.substring(4,6)+"%";
    	}
    	return res;
    }
}
