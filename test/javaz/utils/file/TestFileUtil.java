/**
 * 
 */
package javaz.utils.file;

import java.util.List;

import javaz.utils.PathUtil;
import javaz.utils.string.StringUtil;
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
	public void testLog(){
		String fileNameString="/tmp/logfile.log";
		for(int i=1;i<=100;i++){
			FileUtil.appendFileMsg(fileNameString, StringUtil.format("line num:{0}", i));
		}
		List<String> log=FileUtil.getFileStrList(fileNameString);
		for(String string:log){
			System.out.println(string);
		}
	}
}
