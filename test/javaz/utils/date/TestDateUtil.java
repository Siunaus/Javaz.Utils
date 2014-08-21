/**
 * 
 */
package javaz.utils.date;

import junit.framework.TestCase;

/**
 * @author Zero
 * @mail baozilaji@126.com
 *
 * Aug 9, 2014
 */
public class TestDateUtil extends TestCase {
	public void testDelay(){
		System.out.println(DateUtil.dateToString(DateUtil.getDateAfter(DateUtil.now(), 2)));
	}
}
