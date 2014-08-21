/**
 * 
 */
package javaz.utils.xls;

import java.io.File;

import javaz.utils.string.StringUtil;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.log4j.Logger;

/**
 * @author Zero
 * @mail baozilaji@126.com
 *
 * Aug 9, 2014
 */
public class XlsUtil {
	private static Logger logger=Logger.getLogger("javaz::XlsUtil");
	/**
	 * 解析excel文件
	 * @param xlsFile
	 * @return
	 */
	public static Workbook parseXls(File xlsFile){
		Workbook rt=null;
		if(xlsFile!=null&&xlsFile.exists()){
			try {
				rt=Workbook.getWorkbook(xlsFile);
			} catch (Exception e) {
				logger.warn(xlsFile.getAbsoluteFile()+" parse error.", e);
			}
		}else{
			logger.warn(xlsFile==null?"xlsFile is null":xlsFile.getAbsoluteFile()+"file not exist.");
		}
		return rt;
	}
	/**
	 * 解析excel文件
	 * @param xlsFilePath
	 * @return
	 */
	public static Workbook parseXls(String xlsFilePath){
		return parseXls(new File(xlsFilePath));
	}
	/**
	 * 获取表格内容
	 * @param sheet
	 * @param i
	 * @param j
	 * @param defaultVal
	 * @return
	 */
	public static String getContent(Sheet sheet,int i,int j,String defaultVal){
		String rt=sheet.getCell(j,i).getContents();
		if(StringUtil.isEmpty(rt)){
			logger.warn(StringUtil.format("第{0}行,第{1}列内容为空,替换默认值:{2}", i,j,defaultVal));
			rt=defaultVal;
		}
		return rt.trim();
	}
	/**
	 * 获取表格内容
	 * @param sheet
	 * @param i
	 * @param j
	 * @return
	 */
	public static String getContent(Sheet sheet,int i,int j){
		return getContent(sheet, i, j, "");
	}
	/**
	 * 获取表格short内容
	 * @param sheet
	 * @param i
	 * @param j
	 * @return
	 */
	public static short getShortContent(Sheet sheet,int i,int j){
		return getShortContent(sheet, i, j, 0);
	}
	/**
	 * 获取表格short内容
	 * @param sheet
	 * @param i
	 * @param j
	 * @param def
	 * @return
	 */
	public static short getShortContent(Sheet sheet,int i,int j,int def){
		return StringUtil.toShort(getContent(sheet, i, j, def+""),(short)def);
	}
	/**
	 * 获取表格int内容
	 * @param sheet
	 * @param i
	 * @param j
	 * @return
	 */
	public static int getIntContent(Sheet sheet,int i,int j){
		return getIntContent(sheet, i, j, 0);
	}
	/**
	 * 获取表格int内容
	 * @param sheet
	 * @param i
	 * @param j
	 * @param def
	 * @return
	 */
	public static int getIntContent(Sheet sheet,int i,int j,int def){
		return StringUtil.toInt(getContent(sheet, i, j, def+""),def);
	}
	/**
	 * 获取表格float内容
	 * @param sheet
	 * @param i
	 * @param j
	 * @return
	 */
	public static float getFloatContent(Sheet sheet,int i,int j){
		return getFloatContent(sheet, i, j, 0.0f);
	}
	/**
	 * 获取表格float内容
	 * @param sheet
	 * @param i
	 * @param j
	 * @param def
	 * @return
	 */
	public static float getFloatContent(Sheet sheet,int i,int j,float def){
		return StringUtil.toFloat(getContent(sheet, i, j, def+""),def);
	}
	/**
	 * 获取表byte格内容
	 * @param sheet
	 * @param i
	 * @param j
	 * @return
	 */
	public static byte getByteContent(Sheet sheet,int i,int j){
		return getByteContent(sheet, i, j, 0);
	}
	/**
	 * 获取表格byte内容
	 * @param sheet
	 * @param i
	 * @param j
	 * @param def
	 * @return
	 */
	public static byte getByteContent(Sheet sheet,int i,int j,int def){
		return StringUtil.toByte(getContent(sheet, i, j, def+""),(byte)def);
	}
	/**
	 * 获取表格long内容
	 * @param sheet
	 * @param i
	 * @param j
	 * @return
	 */
	public static long getLongContent(Sheet sheet,int i,int j){
		return getLongContent(sheet, i, j, 0);
	}
	/**
	 * 获取表格long内容
	 * @param sheet
	 * @param i
	 * @param j
	 * @param def
	 * @return
	 */
	public static long getLongContent(Sheet sheet,int i,int j,long def){
		return StringUtil.toLong(getContent(sheet, i, j, def+""),def);
	}
}
