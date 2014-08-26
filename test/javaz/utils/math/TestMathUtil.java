/**
 * 
 */
package javaz.utils.math;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

/**
 * @author Zero
 * @mail baozilaji@126.com
 *
 * Aug 9, 2014
 */
public class TestMathUtil extends TestCase {
	public void testRandom(){
		for(int i=0;i<100;i++){
			System.out.println(MathUtil.getRandomNum(10));
		}
	}
	public void testRandIndex(){
		List<Integer> list=new ArrayList<Integer>();
		int sum=0;
		for(int i=0;i<10;i++){
			list.add(i+1);
			sum+=i+1;
		}
		for(int i=0;i<100;i++){
			System.out.println(MathUtil.getRandomIndex(list,sum));
		}
	}
}
