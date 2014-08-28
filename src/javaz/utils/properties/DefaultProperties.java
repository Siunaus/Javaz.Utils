/**
 * 
 */
package javaz.utils.properties;

/**
 * @author Zero
 * @mail baozilaji@126.com
 *
 * Aug 9, 2014
 */
public class DefaultProperties extends AbstractProperties {
	private static DefaultProperties instance=new DefaultProperties();
	public DefaultProperties() {
		super("default.properties");
	}
	public static DefaultProperties getInstance(){
		return instance;
	}
	private static final String KEY_VERSION_NAME="version_name";
	private static final String KEY_VERSION_CODE="version_code";
	/**
	 * 获取版本名
	 * @return
	 */
	public String getVersionName(){
		return getProperty(KEY_VERSION_NAME, "1.00.00");
	}
	/**
	 * 获取版本号
	 * @return
	 */
	public int getVersionCode(){
		return getInt(KEY_VERSION_CODE, 10000);
	}
}
