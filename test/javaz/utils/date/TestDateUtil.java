/**
 * 
 */
package javaz.utils.date;

import java.util.Date;

import junit.framework.TestCase;

/**
 * @author Zero
 * @mail baozilaji@126.com
 *
 * Aug 9, 2014
 */
public class TestDateUtil extends TestCase {
	public void testAction(){
		System.out.println(DateUtil.dateToString(DateUtil.getDateAfter(DateUtil.now(), 2)));
	}
	public void testDelay(){
		Date d1=DateUtil.stringToDate("2014-03-14 08:44:35");
		Date d2=DateUtil.stringToDate("2014-03-15 09:45:36");
		System.out.println(DateUtil.delayHour(d1, d2));
		System.out.println(DateUtil.delayMinute(d1, d2));
		System.out.println(DateUtil.delaySecond(d1, d2));
		System.out.println(DateUtil.delayTotalDay(d1, d2));
		System.out.println(DateUtil.delayTotalHour(d1, d2));
		System.out.println(DateUtil.delayTotalMinute(d1, d2));
		System.out.println(DateUtil.delayTotalSecond(d1, d2));
	}
}
