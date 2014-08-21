/**
 * 
 */
package javaz.utils.xls;

import junit.framework.TestCase;
import jxl.Workbook;

import org.junit.Test;

/**
 * @author Zero
 * @mail baozilaji@126.com
 *
 * Aug 9, 2014
 */
public class TestXlsUtl extends TestCase {
	@Test
	public void testXls() throws Exception {
		Workbook book=XlsUtil.parseXls("/Users/Zero/temp/client_init.xls");
		System.out.println(XlsUtil.getIntContent(book.getSheet(0), 0, 1));
	}
}
