/**
 * 
 */
package javaz.utils;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * @author Zero
 *
 * 2014年8月9日
 */
public class TestCommUtil extends TestCase {
	@Test
	public void testSubList() throws Exception {
		List<Integer> list=new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		for(Integer i:CommUtil.getSubListPage(list, 1, 3)){
			System.out.println(i);
		}
	}
}
