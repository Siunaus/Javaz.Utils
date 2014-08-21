/**
 * 
 */
package javaz.utils.properties;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Properties;

import javaz.utils.PathUtil;
import javaz.utils.string.StringUtil;

import org.apache.log4j.Logger;

/**
 * @author Zero
 * @mail baozilaji@126.com
 *
 * Aug 9, 2014
 */
public abstract class AbstractProperties {
	static Logger logger=org.apache.log4j.Logger.getLogger("javaz::AbstractProperties");
	/**
	 * properties文件
	 */
	private final String fileName;
	private Properties properties;
	/**
	 * 操作锁
	 * （在reload的时候，不至于取不到数据）
	 */
	private Object lock=new Object();
	public AbstractProperties(String fileInClassPath){
		this.fileName=fileInClassPath;
		properties=new Properties();
		load();
	}
	private void load(){
		try {
			synchronized (lock) {
				properties.load(new DataInputStream(PathUtil.getResourceInputStream(fileName)));
			}
			logger.debug(StringUtil.format("{0} load success.", fileName));
		} catch (IOException e) {
			logger.error(StringUtil.format("{0} load error.", fileName), e);
		}
	}
	public void reload(){
		load();
	}
	/**
	 * 获取properites配置数据
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	public String getProperty(String key,String defaultVal){
		String rt=defaultVal;
		if(!StringUtil.isEmpty(key)){
			synchronized (lock) {
				String v = properties.getProperty(key);
				if(!StringUtil.isEmpty(v)){
					rt=v.trim();
				}
			}
		}
		return rt;
	}
	/**
	 * 获取properites配置数据
	 * @param key
	 * @return
	 */
	public String getProperty(String key){
		return getProperty(key, "");
	}
	/**
	 * 获取short数据配置
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	public short getShort(String key,int defaultVal){
		return StringUtil.toShort(getProperty(key, defaultVal+""),(short)defaultVal);
	}
	/**
	 * 获取short数据配置
	 * @param key
	 * @return
	 */
	public short getShort(String key){
		return getShort(key, 0);
	}
	/**
	 * 获得int数据配置
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	public int getInt(String key,int defaultVal){
		return StringUtil.toInt(getProperty(key,defaultVal+""),defaultVal);
	}
	/**
	 * 获得int数据配置
	 * @param key
	 * @return
	 */
	public int getInt(String key){
		return getInt(key, 0);
	}
	/**
	 * 获得byte数据配置
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	public byte getByte(String key,int defaultVal){
		return StringUtil.toByte(getProperty(key, defaultVal+""),(byte)defaultVal);
	}
	/**
	 * 获得byte数据配置
	 * @param key
	 * @return
	 */
	public byte getByte(String key){
		return getByte(key, 0);
	}
	/**
	 * 获得float数据配置
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	public float getFloat(String key,float defaultVal){
		return StringUtil.toFloat(getProperty(key, defaultVal+""),defaultVal);
	}
	/**
	 * 获得float数据配置
	 * @param key
	 * @return
	 */
	public float getFloat(String key){
		return getFloat(key, 0f);
	}
	/**
	 * 获得long数据配置
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	public long getLong(String key,long defaultVal){
		return StringUtil.toLong(getProperty(key, defaultVal+""),defaultVal);
	}
	/**
	 * 获得long数据配置
	 * @param key
	 * @return
	 */
	public long getLong(String key){
		return getLong(key, 0l);
	}
	/**
	 * 获得double数据配置
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	public double getDouble(String key,double defaultVal){
		return StringUtil.toDouble(getProperty(key, defaultVal+""),defaultVal);
	}
	/**
	 * 获得double数据配置
	 * @param key
	 * @return
	 */
	public double getDouble(String key){
		return getDouble(key, 0d);
	}
}
