/**
 * 
 */
package javaz.utils;

import javaz.utils.date.DateUtil;
import junit.framework.TestCase;

import org.junit.Test;

/**
 * @author Zero
 * @mail baozilaji@126.com
 *
 * Aug 9, 2014
 */
public class TestPathUtil extends TestCase {
	@Test
	public void testClassPath() throws Exception {
		System.out.println("DateUtil.class is in path:"+PathUtil.getClassPath(DateUtil.class));
	}
	@Test
	public void testRootPath() throws Exception {
		System.out.println("root path:"+PathUtil.getRootClassPath());
	}
}
