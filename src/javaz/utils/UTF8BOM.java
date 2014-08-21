package javaz.utils;
import java.io.File;

import javaz.utils.codec.CodecUtil;

/**
 * 
 * @author Zero
 * @mail baozilaji@126.com
 *
 * Aug 6, 2014
 */
public class UTF8BOM {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length!=1){
			System.out.println("needs 1 param. file_name or base_dir!");
			return;
		}
		System.out.println(args[0]);
		File file = new File(args[0]);
		parseUTF8(file);
	}
	public static void parseUTF8(File file){
		if(file==null){
			return;
		}
		if(file.isDirectory()){
			if(file.list()!=null){
				for(File f:file.listFiles()){
					parseUTF8(f);
				}
			}
		}else{
			if(file.getName().endsWith(".java")){
				System.out.println(file.getName());
				CodecUtil.clearUTF8BOM(file);
			}
		}
	}
}
