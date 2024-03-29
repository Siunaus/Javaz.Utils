/**
 * 
 */
package javaz.utils.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javaz.utils.string.StringUtil;

import org.apache.log4j.Logger;

/**
 * @author Zero
 *
 * 2014年8月9日
 */
public class FileUtil {
	private static Logger logger=Logger.getLogger("javaz::FileUtil");
	/**
	 * 获取指定路径下的某文件
	 * @param name
	 * @return
	 */
	public static File getFile(String name){
		File file = new File(name);
		if(file==null||!file.isFile()){
			logger.warn("not find file:"+name);
			return null;
		}
		return file;
	}
	/**
	 * 按指定格式读取文件 返回字符串列表
	 * @param name
	 * @param charset
	 * @return
	 */
	public static List<String> getFileStrList(String name,String charset){
		File file = getFile(name);
		if(file==null||StringUtil.isEmpty(charset)){
			logger.warn("file charset:"+charset);
			return null;
		}
		List<String> rt = new ArrayList<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(name),charset));
			String tmp = null;
			while((tmp=br.readLine())!=null){
				rt.add(tmp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				br=null;
			}
		}
		return rt;
	}
	/**
	 * 按指定格式读取文件(默认utf8编码格式)
	 * @param name
	 * @return
	 */
	public static List<String> getFileStrList(String name){
		return getFileStrList(name, "utf8");
	}
	/**
	 * 获取某一文件的完整内容
	 * @param name
	 * @param charset
	 * @return
	 */
	public static String getFileStr(String name,String charset){
		StringBuilder sb = new StringBuilder();
		List<String> list = getFileStrList(name, charset);
		for(String s:list){
			sb.append(s).append("\n");
		}
		return sb.toString();
	}
	/**
	 * 获取某一文件的完整内容
	 * @param name
	 * @return
	 */
	public static String getFileStr(String name){
		return getFileStr(name, "utf8");
	}
	/**
	 * 记录日志
	 * @param logFile
	 * @param log
	 * @param e
	 */
	public static void log(String logFile,String log,Exception e){
		appendFileMsg(logFile, log, e, "utf8", true);
	}
	/**
	 * 追加消息至文件尾 指定输出格式
	 * @param fileName
	 * @param msg
	 * @param e
	 * @param charset
	 * @param printSplit
	 */
	private static void appendFileMsg(String fileName,String msg,Exception e,String charset,boolean printSplit){
		if(StringUtil.isEmpty(fileName)||StringUtil.isEmpty(msg)||StringUtil.isEmpty(charset)){
			return;
		}
		String sep = "/";
		if(fileName.indexOf("/")==-1){
			sep = "\\";
		}
		if(fileName.indexOf(sep)==-1){
			return;
		}
		String path = fileName.substring(0, fileName.lastIndexOf(sep)+1);
		String name = fileName.substring(fileName.lastIndexOf(sep)+1);
		if(StringUtil.isEmpty(path)||StringUtil.isEmpty(name)){
			return;
		}
		File file = getFile(fileName);
		if(file==null){
			File dir = new File(path);
			if(!dir.exists()){
				dir.mkdirs();
			}
			file = new File(fileName);
			try {
				file.createNewFile();
			} catch (IOException e1) {
			}
		}
		if(file!=null){
			PrintWriter p = null;
			try {
				p = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true),charset)));
				p.write(msg);
				if(e!=null){
					p.write("trace:\n");
					e.printStackTrace(p);
				}
				if(printSplit){
					p.write("=========================");
				}
				p.print("\n");
			} catch (Exception e1) {
				e1.printStackTrace();
			}finally{
				if(p!=null){
					try {
						p.close();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					p=null;
				}
			}
		}
	}
	/**
	 * 追加消息至文件尾 指定输出格式
	 * @param fileName
	 * @param msg
	 * @param charset
	 */
	public static void appendFileMsg(String fileName,String msg,String charset){
		appendFileMsg(fileName, msg, null, charset, false);
	}
	/**
	 * 追加消息至文件尾(默认utf8编码)
	 * @param fileName
	 * @param msg
	 */
	public static void appendFileMsg(String fileName,String msg){
		appendFileMsg(fileName, msg, "utf8");
	}
	/**
	 * 删除文件[递归]
	 * @param file
	 */
	public static void deleteFile(File file){
		if(file==null){
			return;
		}
		System.out.println("cmd:"+file.getAbsolutePath());
		if(file.isDirectory()){
			if(file.list()!=null){
				for(File f:file.listFiles()){
					deleteFile(f);
				}
			}
		}
		System.out.println("delete:"+file.getAbsolutePath());
		file.delete();
	}
}
