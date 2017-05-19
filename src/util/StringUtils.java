package util;

import java.io.Reader;
import java.sql.Clob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Java表单验证工具类
 * 
 * @author jiqinlin
 * 
 */
@SuppressWarnings("all")
public class StringUtils {
	/**
	 * 空白.
	 */
	public static final String EMPTY = "";
	/**
	 * 逗号.
	 */
	public static final String COMMA = ",";
	/**
	 * 点.
	 */
	public static final String DOT = ".";
	/**
	 * 左括号.
	 */
	public static final String LEFTBRACKAET = "(";
	/**
	 * 右括号.
	 */
	public static final String RIGHTBRACKAET = ")";
	/**
	 * 单引号.
	 */
	public static final String SINGEQUOTE = "'";
	/**
	 * 空格.
	 */
	public static final String SPACE = " ";
	/**
	 * IN.
	 */
	public static final String IN = "IN";
	/**
	 * OR.
	 */
	public static final String OR = "OR";
	
    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;
    private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_YEAR_AGO = "年前";

	
	public final static boolean isNull(Object[] objs) {
		if (objs == null || objs.length == 0)
			return true;
		return false;
	}
	public final static boolean isNull(Object obj) {
		if (obj == null || isNull(obj.toString())){
			return true;
		}
		return false;
	}

	public final static boolean isNull(Integer integer) {
		if (integer == null || integer == 0)
			return true;
		return false;
	}

	public final static boolean isNull(Collection collection) {
		if (collection == null || collection.size() == 0)
			return true;
		return false;
	}

	public final static boolean isNull(Map map) {
		if (map == null || map.size() == 0)
			return true;
		return false;
	}

	public final static boolean isNull(String str) {
		return str == null || "".equals(str.trim())
				|| "null".equals(str.toLowerCase());
	}

	public final static boolean isNull(Long longs) {
		if (longs == null || longs == 0)
			return true;
		return false;
	}

	public final static boolean isNotNull(Long longs) {
		return !isNull(longs);
	}

	public final static boolean isNotNull(String str) {
		return !isNull(str);
	}

	public final static boolean isNotNull(Collection collection) {
		return !isNull(collection);
	}

	public final static boolean isNotNull(Map map) {
		return !isNull(map);
	}

	public final static boolean isNotNull(Integer integer) {
		return !isNull(integer);
	}

	public final static boolean isNotNull(Object[] objs) {
		return !isNull(objs);
	}
	public final static boolean isNotNull(Object obj) {
		return !isNull(obj);
	}

	/**
	 * 匹配URL地址
	 * 
	 * @param str
	 * @return
	 * @author jiqinlin
	 */
	public final static boolean isUrl(String str) {
		return match(str, "^http://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$");
	}

	/**
	 * 匹配密码，以字母开头，长度在6-12之间，只能包含字符、数字和下划线。
	 * 
	 * @param str
	 * @return
	 * @author jiqinlin
	 */
	public final static boolean isPwd(String str) {
		return match(str, "^[a-zA-Z]\\w{6,12}$");
	}

	/**
	 * 验证字符，只能包含中文、英文、数字、下划线等字符。
	 * 
	 * @param str
	 * @return
	 * @author jiqinlin
	 */
	public final static boolean stringCheck(String str) {
		return match(str, "^[a-zA-Z0-9\u4e00-\u9fa5-_]+$");
	}

	/**
	 * 匹配Email地址
	 * 
	 * @param str
	 * @return
	 * @author jiqinlin
	 */
	public final static boolean isEmail(String str) {
		return match(str,
				"^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
	}

	/**
	 * 匹配非负整数（正整数+0）
	 * 
	 * @param str
	 * @return 匹配 ^[1-9]d*|0$
	 * @author jiqinlin
	 */
	public final static boolean isInteger(String str) {
		return match(str, "^\\d+$");
	}

	/**
	 * 匹配非负整数（正整数）
	 * 
	 * @param str
	 * @return
	 * @author jiqinlin
	 */
	public final static boolean isIntegers(String str) {
		return match(str, "^[1-9]d*$");
	}

	/**
	 * 判断数值类型，包括整数和浮点数
	 * 
	 * @param str
	 * @return
	 * @author jiqinlin
	 */
	public final static boolean isNumeric(String str) {
		if (isFloat(str) || isInteger(str))
			return true;
		return false;
	}

	/**
	 * 只能输入数字
	 * 
	 * @param str
	 * @return
	 * @author jiqinlin
	 */
	public final static boolean isDigits(String str) {
		return match(str, "^[0-9]*$");
	}

	/**
	 * 匹配正浮点数
	 * 
	 * @param str
	 * @return
	 * @author jiqinlin
	 */
	public final static boolean isFloat(String str) {
		return match(str, "^[-\\+]?\\d+(\\.\\d+)?$");
	}

	/**
	 * 联系电话(手机/电话皆可)验证
	 * 
	 * @param text
	 * @return
	 * @author jiqinlin
	 */
	public final static boolean isTel(String text) {
		if (isMobile(text) || isPhone(text))
			return true;
		return false;
	}

	/**
	 * 电话号码验证
	 * 
	 * @param text
	 * @return
	 * @author jiqinlin
	 */
	public final static boolean isPhone(String text) {
		return match(text, "^(\\d{3,4}-?)?\\d{7,9}$");
	}

	/**
	 * 手机号码验证
	 * 
	 * @param text
	 * @return
	 * @author jiqinlin
	 */
	public final static boolean isMobile(String text) {
		if (text.length() != 11)
			return false;
		return match(text,
				"^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\\d{8})$");
	}

	/**
	 * 身份证号码验证
	 * 
	 * @param text
	 * @return
	 * @author jiqinlin
	 */
	public final static boolean isIdCardNo(String text) {
		return match(text, "^(\\d{6})()?(\\d{4})(\\d{2})(\\d{2})(\\d{3})(\\w)$");
	}

	/**
	 * 邮政编码验证
	 * 
	 * @param text
	 * @return
	 * @author jiqinlin
	 */
	public final static boolean isZipCode(String text) {
		return match(text, "^[0-9]{6}$");
	}

	/**
	 * 判断整数num是否等于0
	 * 
	 * @param num
	 * @return
	 * @author jiqinlin
	 */
	public final static boolean isIntEqZero(int num) {
		return num == 0;
	}

	/**
	 * 判断整数num是否大于0
	 * 
	 * @param num
	 * @return
	 * @author jiqinlin
	 */
	public final static boolean isIntGtZero(int num) {
		return num > 0;
	}

	/**
	 * 判断整数num是否大于或等于0
	 * 
	 * @param num
	 * @return
	 * @author jiqinlin
	 */
	public final static boolean isIntGteZero(int num) {
		return num >= 0;
	}

	/**
	 * 判断浮点数num是否等于0
	 * 
	 * @param num
	 *            浮点数
	 * @return
	 * @author jiqinlin
	 */
	public final static boolean isFloatEqZero(float num) {
		return num == 0f;
	}

	/**
	 * 判断浮点数num是否大于0
	 * 
	 * @param num
	 *            浮点数
	 * @return
	 * @author jiqinlin
	 */
	public final static boolean isFloatGtZero(float num) {
		return num > 0f;
	}

	/**
	 * 判断浮点数num是否大于或等于0
	 * 
	 * @param num
	 *            浮点数
	 * @return
	 * @author jiqinlin
	 */
	public final static boolean isFloatGteZero(float num) {
		return num >= 0f;
	}

	/**
	 * 判断是否为合法字符(a-zA-Z0-9-_)
	 * 
	 * @param text
	 * @return
	 * @author jiqinlin
	 */
	public final static boolean isRightfulString(String text) {
		return match(text, "^[A-Za-z0-9_-]+$");
	}

	/**
	 * 判断英文字符(a-zA-Z)
	 * 
	 * @param text
	 * @return
	 * @author jiqinlin
	 */
	public final static boolean isEnglish(String text) {
		return match(text, "^[A-Za-z]+$");
	}

	/**
	 * 判断中文字符(包括汉字和符号)
	 * 
	 * @param text
	 * @return
	 * @author jiqinlin
	 */
	public final static boolean isChineseChar(String text) {
		return match(text, "^[\u0391-\uFFE5]+$");
	}

	/**
	 * 匹配汉字
	 * 
	 * @param text
	 * @return
	 * @author jiqinlin
	 */
	public final static boolean isChinese(String text) {
		return match(text, "^[\u4e00-\u9fa5]+$");
	}

	/**
	 * 是否包含中英文特殊字符，除英文"-_"字符外
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isContainsSpecialChar(String text) {
		if (StringUtils.isBlank(text))
			return false;
		String[] chars = { "[", "`", "~", "!", "@", "#", "$", "%", "^", "&",
				"*", "(", ")", "+", "=", "|", "{", "}", "'", ":", ";", "'",
				",", "[", "]", ".", "<", ">", "/", "?", "~", "！", "@", "#",
				"￥", "%", "…", "&", "*", "（", "）", "—", "+", "|", "{", "}",
				"【", "】", "‘", "；", "：", "”", "“", "’", "。", "，", "、", "？", "]" };
		for (String ch : chars) {
			if (text.contains(ch))
				return true;
		}
		return false;
	}

	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 过滤中英文特殊字符，除英文"-_"字符外
	 * 
	 * @param text
	 * @return
	 */
	public static String stringFilter(String text) {
		String regExpr = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regExpr);
		Matcher m = p.matcher(text);
		return m.replaceAll("").trim();
	}

	/**
	 * 过滤html代码
	 * 
	 * @param inputString
	 *            含html标签的字符串
	 * @return
	 */
	public static String htmlFilter(String inputString) {
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;
		java.util.regex.Pattern p_ba;
		java.util.regex.Matcher m_ba;

		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
			// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
			// }
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
			String patternStr = "\\s+";

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			p_ba = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
			m_ba = p_ba.matcher(htmlStr);
			htmlStr = m_ba.replaceAll(""); // 过滤空格

			textStr = htmlStr;

		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}
		return textStr;// 返回文本字符串
	}

	/**
	 * 正则表达式匹配
	 * 
	 * @param text
	 *            待匹配的文本
	 * @param reg
	 *            正则表达式
	 * @return
	 * @author jiqinlin
	 */
	private final static boolean match(String text, String reg) {
		if (StringUtils.isBlank(text) || StringUtils.isBlank(reg))
			return false;
		return Pattern.compile(reg).matcher(text).matches();
	}

	/**
	 * 将String转成Clob ,静态方法
	 * 
	 * @param str
	 *            字段
	 * @return clob对象，如果出现错误，返回 null
	 */
	public static Clob stringToClob(String str) {
		if (null == str)
			return null;
		else {
			try {
				java.sql.Clob c = new javax.sql.rowset.serial.SerialClob(
						str.toCharArray());
				return c;
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * 将Clob转成String ,静态方法
	 * 
	 * @param clob
	 *            字段
	 * @return 内容字串，如果出现错误，返回 null
	 */
	public static String clobToString(Clob clob) {
		if (clob == null)
			return null;
		StringBuffer sb = new StringBuffer();
		Reader clobStream = null;
		try {
			clobStream = clob.getCharacterStream();
			char[] b = new char[60000];// 每次获取60K
			int i = 0;
			while ((i = clobStream.read(b)) != -1) {
				sb.append(b, 0, i);
			}
		} catch (Exception ex) {
			sb = null;
		} finally {
			try {
				if (clobStream != null) {
					clobStream.close();
				}
			} catch (Exception e) {
			}
		}
		if (sb == null)
			return null;
		else
			return sb.toString();
	}

	/**
	 * 将字符串转换为utf-8
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static String stringToUtf(String str) {
		try {
			str = new String(str.trim().getBytes("ISO-8859-1"), "utf-8");
			return str;
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * 将用逗号隔开的字符串转换为可插入数据库的字符串 "1,2,3" --> "'1','2','3'"
	 * 
	 * @return
	 */
	public static String idsToSQLIds(String ids) {
		StringBuffer tempStr = new StringBuffer();
		String sqlStr = "";
		if (null != ids && !"".equals(ids)) {
			String[] idArr = ids.split(",");
			for (String s : idArr) {
				tempStr.append("'" + s + "',");
			}
			if (tempStr.toString().endsWith(",")) {
				sqlStr = tempStr.substring(0, tempStr.length() - 1);
			}
		}
		return sqlStr;
	}
	// 附 ： 常用的正则表达式：
	// 匹配特定数字：
	// ^[1-9]d*$　 　 //匹配正整数
	// ^-[1-9]d*$ 　 //匹配负整数
	// ^-?[1-9]d*$　　 //匹配整数
	// ^[1-9]d*|0$　 //匹配非负整数（正整数 + 0）
	// ^-[1-9]d*|0$　　 //匹配非正整数（负整数 + 0）
	// ^[1-9]d*.d*|0.d*[1-9]d*$　　 //匹配正浮点数
	// ^-([1-9]d*.d*|0.d*[1-9]d*)$　 //匹配负浮点数
	// ^-?([1-9]d*.d*|0.d*[1-9]d*|0?.0+|0)$　 //匹配浮点数
	// ^[1-9]d*.d*|0.d*[1-9]d*|0?.0+|0$　　 //匹配非负浮点数（正浮点数 + 0）
	// ^(-([1-9]d*.d*|0.d*[1-9]d*))|0?.0+|0$　　//匹配非正浮点数（负浮点数 + 0）
	// 评注：处理大量数据时有用，具体应用时注意修正
	//
	// 匹配特定字符串：
	// ^[A-Za-z]+$　　//匹配由26个英文字母组成的字符串
	// ^[A-Z]+$　　//匹配由26个英文字母的大写组成的字符串
	// ^[a-z]+$　　//匹配由26个英文字母的小写组成的字符串
	// ^[A-Za-z0-9]+$　　//匹配由数字和26个英文字母组成的字符串
	// ^w+$　　//匹配由数字、26个英文字母或者下划线组成的字符串
	//
	// 在使用RegularExpressionValidator验证控件时的验证功能及其验证表达式介绍如下:
	//
	// 只能输入数字：“^[0-9]*$”
	// 只能输入n位的数字：“^d{n}$”
	// 只能输入至少n位数字：“^d{n,}$”
	// 只能输入m-n位的数字：“^d{m,n}$”
	// 只能输入零和非零开头的数字：“^(0|[1-9][0-9]*)$”
	// 只能输入有两位小数的正实数：“^[0-9]+(.[0-9]{2})?$”
	// 只能输入有1-3位小数的正实数：“^[0-9]+(.[0-9]{1,3})?$”
	// 只能输入非零的正整数：“^+?[1-9][0-9]*$”
	// 只能输入非零的负整数：“^-[1-9][0-9]*$”
	// 只能输入长度为3的字符：“^.{3}$”
	// 只能输入由26个英文字母组成的字符串：“^[A-Za-z]+$”
	// 只能输入由26个大写英文字母组成的字符串：“^[A-Z]+$”
	// 只能输入由26个小写英文字母组成的字符串：“^[a-z]+$”
	// 只能输入由数字和26个英文字母组成的字符串：“^[A-Za-z0-9]+$”
	// 只能输入由数字、26个英文字母或者下划线组成的字符串：“^w+$”
	// 验证用户密码:“^[a-zA-Z]\\w{5,17}$”正确格式为：以字母开头，长度在6-18之间，
	//
	// 只能包含字符、数字和下划线。
	// 验证是否含有^%&’,;=?$”等字符：“[^%&’,;=?$x22]+”
	// 只能输入汉字：“^[u4e00-u9fa5],{0,}$”
	// 验证Email地址：“^w+[-+.]w+)*@w+([-.]w+)*.w+([-.]w+)*$”
	// 验证InternetURL：“^http://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$”
	// 验证电话号码：“^((d{3,4})|d{3,4}-)?d{7,8}$”
	//
	// 正确格式为：“XXXX-XXXXXXX”，“XXXX-XXXXXXXX”，“XXX-XXXXXXX”，
	//
	// “XXX-XXXXXXXX”，“XXXXXXX”，“XXXXXXXX”。
	// 验证身份证号（15位或18位数字）：“^d{15}|d{}18$”
	// 验证一年的12个月：“^(0?[1-9]|1[0-2])$”正确格式为：“01”-“09”和“1”“12”
	// 验证一个月的31天：“^((0?[1-9])|((1|2)[0-9])|30|31)$” 正确格式为：“01”“09”和“1”“31”。
	//
	// 匹配中文字符的正则表达式： [u4e00-u9fa5]
	// 匹配双字节字符(包括汉字在内)：[^x00-xff]
	// 匹配空行的正则表达式：n[s| ]*r
	// 匹配HTML标记的正则表达式：/< (.*)>.*|< (.*) />/
	// 匹配首尾空格的正则表达式：(^s*)|(s*$)
	// 匹配Email地址的正则表达式：w+([-+.]w+)*@w+([-.]w+)*.w+([-.]w+)*
	// 匹配网址URL的正则表达式：^http://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$
	/**
	 * 将字符串转换为16进制字符串
	 * @param s
	 * @return
	 */
	public static String toHexString(String s) {  
		   String str = "";  
		   for (int i = 0; i < s.length(); i++) {  
		    int ch = (int) s.charAt(i);  
		    String s2 = Integer.toHexString(ch);  
		    str = str + s2;  
		   }  
		   return str;  
		}  
	/**
	 * 将16进制字符串转换成普通字符串
	 * @param s
	 * @return
	 */
	public static String toStringHex(String s) {  
		   byte[] baKeyword = new byte[s.length() / 2];  
		   for (int i = 0; i < baKeyword.length; i++) {  
		    try {  
		     baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(  
		       i * 2, i * 2 + 2), 16));  
		    } catch (Exception e) {  
		     e.printStackTrace();  
		    }  
		   }  
		   try {  
		    s = new String(baKeyword, "gbk");// UTF-16le:Not  
		   } catch (Exception e1) {  
		    e1.printStackTrace();  
		   }  
		   return s;  
		}  
	
	/**
	 * list转换为字符串 并加入分隔字符
	 * @param list
	 * @param separator
	 * @return
	 */
	public static String listToString(List<String> list, String separator) {    
		StringBuilder sb = new StringBuilder();    
		for (int i = 0; i < list.size(); i++) {        
			sb.append(list.get(i)).append(separator);    
		}    
		return sb.toString().substring(0,sb.toString().length()-1);
	}
	
	/**
	 * String根据指定的分隔符， 转为List<String>
	 * @param str
	 * @param separator
	 * @return
	 */
	public static List<String> stringToList(String str, String separator) {
		
		str = str.replace("，", ",");
		StringBuilder sb = new StringBuilder(str); 
		List<String> list = new ArrayList<String>();
        int offset = 0;
        int start = 0;
        while((offset = sb.indexOf(separator, offset)) != -1){
        	list.add(str.substring(start, offset));
        	start = offset + separator.length();
            offset = offset + separator.length();
        }
        list.add(sb.substring(start));
		return list;
	}
	/**
	 * 字符串转set集合
	 * @param str
	 * @param separator
	 * @return
	 */
	public static Set<String> stringToSet(String str, String separator) {
		str = str.replace("，", ",");
		StringBuilder sb = new StringBuilder(str); 
		Set<String> set = new HashSet<String>();
        int offset = 0;
        int start = 0;
        while((offset = sb.indexOf(separator, offset)) != -1){
        	set.add(str.substring(start, offset));
        	start = offset + separator.length();
            offset = offset + separator.length();
        }
        set.add(sb.substring(start));
		return set;
	}
	
	/**
	 * set转换为字符串 并加入分隔字符
	 * @param list
	 * @param separator
	 * @return
	 */
	public static String setToString(Set<String> set, String separator) {    
		StringBuilder sb = new StringBuilder();    
		for (String item : set) {        
			sb.append(item).append(separator);    
		}    
		return sb.toString().substring(0,sb.toString().length()-1);
	}
	
	/**
	 * 去除字符串中相同的字段
	 * @param separator
	 * @return
	 */
	public static String distinctStringByString(String str,String separator){
		List<String> codeList = new LinkedList<String>();
		codeList=stringToList(str,separator); 
		List<String> listWithoutDup = new ArrayList<String>(new HashSet<String>(codeList));
		return listToString(listWithoutDup, separator);
	} 
	
	/**
	 * 将时间转化成距离当前时间多少
	 * 时间（刚刚、？分钟前、？小时前、？天前）
	 * T≤5分钟，显示为：刚刚
	   30分钟＜T＜1小时，显示为：？分钟前
		1小时≤T＜1天，显示为：？小时前
		T≥1天是，显示为：？天前
	 */
	public static String dateFormatterString(Date date){
		String returnString = "";
		if(StringUtils.isNull(date)){
			return null;
		}else{
			long delta  = new Date().getTime() - date.getTime();
			 if (delta < 1L * ONE_MINUTE *30) {
	             long seconds = toSeconds(delta);
	             return seconds < 0 ? "时间错误" : "刚刚" ;
	         }
	         if (delta < 30L * ONE_MINUTE) {
	             long minutes = toMinutes(delta);
	             return (minutes <= 5 ? 5 : minutes) + ONE_MINUTE_AGO;
	         }
	         if (delta < 24L * ONE_HOUR) {
	             long hours = toHours(delta);
	             return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
	         }
	         if (delta < 48L * ONE_HOUR) {
	             return "昨天";
	         }
	         if (delta < 30L * ONE_DAY) {
	             long days = toDays(delta);
	             return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
	         }
	         if (delta < 12L * 4L * ONE_WEEK) {
	             long months = toMonths(delta);
	             return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
	         } else {
	             long years = toYears(delta);
	             return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
	         }
		}
	}
	
    private static long toSeconds(long date) {
    	return date / 1000L;
	}
    
	private static long toMinutes(long date) {
	    return toSeconds(date) / 60L;
	}
	
	private static long toHours(long date) {
	    return toMinutes(date) / 60L;
	}
	
	private static long toDays(long date) {
	    return toHours(date) / 24L;
	}
	
	private static long toMonths(long date) {
	    return toDays(date) / 30L;
	}
	
	private static long toYears(long date) {
	    return toMonths(date) / 365L;
	}
	
	
	//通过算法分割in
	public static String getOracleSQLIn(String ids, String field){
		if(StringUtils.isNull(ids)){
			return "";
		}else{
			String[] idArr = ids.split(",");
			List<String> idlist = Arrays.asList(idArr);
			int count = ids.length();
			count = Math.min(count, 1000);  
		    int len = ids.length();   
		    int size = len % count;  
		    if (size == 0) {  
		        size = len / count;  
		    } else {  
		        size = (len / count) + 1;  
		    }  
		    StringBuilder builder = new StringBuilder();  
		    for (int i = 0; i < size; i++) {  
		        int fromIndex = i * count;  
		        int toIndex = Math.min(fromIndex + count, len);  
		        String productId = StringUtils.listToString(idlist.subList(fromIndex, toIndex), "','");
		        if (i != 0) {  
		            builder.append(" or ");  
		        }  
		        builder.append(field).append(" in ('").append(productId).append("')");  
		    }  
		     
		    if(StringUtils.isNull(builder.toString())){
		    	return field + " in ('')";
		    }else{
		    	return builder.toString();
		    }
		}
	}  
}