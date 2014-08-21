/**
 * 
 */
package javaz.utils;

import java.util.HashMap;
import java.util.Map;

import javaz.utils.string.StringUtil;

import org.apache.log4j.Logger;

/**
 * @author Zero
 * @mail baozilaji@126.com
 * 参数解析工具类
 * 参数格式必须为（--key=value）这种形式
 * Aug 21, 2014
 */
public class ArgsUtil {
	static Logger logger=Logger.getLogger(ArgsUtil.class);
	private Map<String, String> map=new HashMap<String, String>();
	private static ArgsUtil instance;
	private ArgsUtil(){
	}
	public static ArgsUtil getInstance(){
		if(instance==null){
			instance=new ArgsUtil();
		}
		return instance;
	}
	/**
	 * 参数初始化
	 * @param args
	 */
	public void init(String[] args){
		if(args!=null&&args.length>0){
			for(String s:args){
				try {
					String tt[] = s.split("=");
					map.put(tt[0].substring(2).trim(), tt[1].trim());
				} catch (Exception e) {
					logger.warn(StringUtil.format("［{0}］参数异常", s), e);
				}
			}
		}
		if(logger.isInfoEnabled()){
			logger.info(map);
		}
	}
	/**
	 * 获取字符串参数
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	public String getArg(String key,String defaultVal){
		String rt=map.get(key);
		if(rt!=null){
			return rt;
		}
		return defaultVal;
	}
	/**
	 * 获取字符串参数
	 * @param key
	 * @return
	 */
	public String getArg(String key){
		return getArg(key, "");
	}
	/**
	 * 获取整型参数
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	public int getIntArg(String key,int defaultVal){
		return StringUtil.toInt(getArg(key, defaultVal+""), defaultVal);
	}
	/**
	 * 获取整型参数
	 * @param key
	 * @return
	 */
	public int getIntArg(String key){
		return getIntArg(key, 0);
	}
}
