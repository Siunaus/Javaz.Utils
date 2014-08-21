/**
 * 
 */
package javaz.utils;

import java.net.URL;

import javaz.utils.string.StringUtil;

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
	/**
	 * 获取资源的路径
	 * @param res
	 * @return
	 */
	public static String getResourcePath(String res){
		if(!StringUtil.isEmpty(res)){
			if(res.startsWith("//")){
				res=res.substring(1);
			}
			if(!res.startsWith("/")){
				res=StringUtil.format("/{0}", res);
			}
			URL uri=PathUtil.class.getResource(res);
			if(uri!=null){
				return uri.getPath();
			}
		}
		return StringUtil.format("{0}/{1}", getRootClassPath(),res);
	}
}
