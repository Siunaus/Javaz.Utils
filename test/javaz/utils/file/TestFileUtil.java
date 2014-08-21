/**
 * 
 */
package javaz.utils.file;

import java.util.List;

import javaz.utils.PathUtil;
import junit.framework.TestCase;

import org.junit.Test;

/**
 * @author Zero
 *
 * 2014年8月9日
 */
public class TestFileUtil extends TestCase{
	@Test
	public void testGetFile() throws Exception {
		List<String> list = FileUtil.getFileStrList(PathUtil.getRootClassPath()+"default.properties");
		for(String s:list){
			System.out.println(s);
		}
		System.out.println(FileUtil.getFileStr(PathUtil.getRootClassPath()+"default.properties"));
	}
}
