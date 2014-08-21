/**
 * 
 */
package javaz.utils.properties;

import javaz.utils.PathUtil;
import junit.framework.TestCase;

import org.junit.Test;

/**
 * @author Zero
 * @mail baozilaji@126.com
 *
 * Aug 9, 2014
 */
public class TestProperties extends TestCase {
	@Test
	public void testGetProperties() throws Exception {
		System.out.println(PathUtil.getRootClassPath());
		System.out.println(DefaultProperties.getInstance().getVersionName());
		System.out.println(DefaultProperties.getInstance().getVersionCode());
	}
}
