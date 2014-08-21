/**
 * 
 */
package javaz.utils.codec;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * @author Zero
 *
 * 2014年8月9日
 */
public class TestCodecUtil extends TestCase {
	@Test
	public void testMd5() throws Exception {
		System.out.println(CodecUtil.MD5("Zero"));
	}
}
