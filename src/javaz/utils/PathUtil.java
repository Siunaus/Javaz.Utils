/**
 * 
 */
package javaz.utils;

import java.net.URL;

/**
 * @author Zero
 * @mail baozilaji@126.com
 *
 * Aug 9, 2014
 */
public class PathUtil {
	/**
	 * 获取classpath的路径
	 * @return
	 */
	public static String getRootClassPath(){
		URL url=PathUtil.class.getResource("/");
		return url.getPath();
	}
	/**
	 * 获取class的路径
	 * @param clazz
	 * @return
	 */
	public static String getClassPath(Class<?> clazz){
		return clazz.getResource("").getPath();
	}
}
