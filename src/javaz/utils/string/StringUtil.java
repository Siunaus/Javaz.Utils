/**
 * 
 */
package javaz.utils.string;

import org.apache.log4j.Logger;

import javaz.utils.math.MathUtil;


/**
 * @author Zero
 * @mail baozilaji@126.com
 *
 * Aug 9, 2014
 */
public class StringUtil {
	private static Logger logger=Logger.getLogger("Javaz::StringUtil");
	/**
	 * 判断字符串是否为空
	 * @param string
	 * @return
	 */
	public static boolean isEmpty(String string) {
		if (string == null || "".equals(string.trim())) {
			return true;
		}
		return false;
	}
	private static final String RANDOM_STR="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	/**
	 * 返回一个指定长度的随机字符串(只包括数字大小写英文字母)
	 * @param len
	 * @return
	 */
	public static String randomStr(int len) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0, strL = RANDOM_STR.length(); i < len; i++) {
			int index = MathUtil.random(strL);
			sb.append(RANDOM_STR.charAt(index));
		}
		return sb.toString();
	}
	/**
	 * 自定义格式输出文本{0} {1}
	 * @param s
	 * @param objects
	 * @return
	 */
	public static String format(String s,Object ...objects){
		if(objects!=null&&objects.length>0){
			StringBuilder sb = new StringBuilder();
			int i=0;
			sb.append("{").append(i).append("}");
			int j = s.indexOf(sb.toString());
			while(j>=0){
				if(i<objects.length){
					s=s.replace(sb.toString(), objects[i]==null?"":objects[i].toString());
				}
				i++;
				sb = new StringBuilder();
				sb.append("{").append(i).append("}");
				j = s.indexOf(sb.toString());
			}
		}
		return s;
	}

	@SuppressWarnings("unchecked")
	private static <T> T to(String string,T defaultVal){
		T rt=defaultVal;
		try {
			if(defaultVal instanceof Integer){
				rt=(T)Integer.valueOf(string);
			}else if(defaultVal instanceof Short){
				rt=(T)Short.valueOf(string);
			}else if(defaultVal instanceof Float){
				rt=(T)Float.valueOf(string);
			}else if(defaultVal instanceof Long){
				rt=(T)Long.valueOf(string);
			}else if(defaultVal instanceof Double){
				rt=(T)Double.valueOf(string);
			}else if(defaultVal instanceof Byte){
				rt=(T)Byte.valueOf(string);
			}else{
				logger.error("not supported type:"+defaultVal);
			}
		} catch (NumberFormatException e) {
			logger.warn("parse error:"+string+":", e);
		}
		return rt;
	}
	/**
	 * 字符串转int
	 * @param string
	 * @return
	 */
	public static int toInt(String string){
		return toInt(string, 0);
	}
	/**
	 * 字符串转int
	 * @param string
	 * @param defaultVal
	 * @return
	 */
	public static int toInt(String string,int defaultVal){
		if(StringUtil.isEmpty(string)){
			return defaultVal;
		}
		return to(string, defaultVal);
	}
	/**
	 * 字符串转byte
	 * @param string
	 * @return
	 */
	public static byte toByte(String string){
		return toByte(string, (byte)0);
	}
	/**
	 * 字符串转byte
	 * @param string
	 * @param defaultVal
	 * @return
	 */
	public static byte toByte(String string,byte defaultVal){
		if(StringUtil.isEmpty(string)){
			return defaultVal;
		}
		return to(string, defaultVal);
	}
	/**
	 * 字符串转short
	 * @param string
	 * @return
	 */
	public static short toShort(String string){
		return toShort(string,(short)0);
	}
	/**
	 * 字符串转short
	 * @param string
	 * @param defaultVal
	 * @return
	 */
	public static short toShort(String string,short defaultVal){
		if(StringUtil.isEmpty(string)){
			return defaultVal;
		}
		return to(string, defaultVal);
	}
	/**
	 * 字符串转long
	 * @param string
	 * @return
	 */
	public static long toLong(String string){
		return toLong(string,0l);
	}
	/**
	 * 字符串转long
	 * @param string
	 * @param defaultVal
	 * @return
	 */
	public static long toLong(String string,long defaultVal){
		if(StringUtil.isEmpty(string)){
			return defaultVal;
		}
		return to(string, defaultVal);
	}
	/**
	 * 字符串转float
	 * @param string
	 * @return
	 */
	public static float toFloat(String string){
		return toFloat(string,0f);
	}
	/**
	 * 字符串转float
	 * @param string
	 * @param defaultVal
	 * @return
	 */
	public static float toFloat(String string,float defaultVal){
		if(StringUtil.isEmpty(string)){
			return defaultVal;
		}
		return to(string, defaultVal);
	}
	/**
	 * 字符串转double
	 * @param string
	 * @return
	 */
	public static double toDouble(String string){
		return toDouble(string, 0d);
	}
	/**
	 * 字符串转double
	 * @param string
	 * @param defaultVal
	 * @return
	 */
	public static double toDouble(String string,double defaultVal){
		if(StringUtil.isEmpty(string)){
			return defaultVal;
		}
		return to(string, defaultVal);
	}
}
