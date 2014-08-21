/**
 * 
 */
package javaz.utils.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javaz.utils.string.StringUtil;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @author Zero
 * @mail baozilaji@126.com
 *
 * Aug 9, 2014
 */
public class XmlUtil {
	private static Logger logger=Logger.getLogger("javaz::XmlUtil");
	/**
	 * 解析xml文件
	 * @param xmlFile
	 * @return
	 */
	public static Document parseXml(File xmlFile){
		Document rt=null;
		if(xmlFile!=null&&xmlFile.exists()){
			try {
				rt=new SAXReader().read(xmlFile);
			} catch (DocumentException e) {
				logger.warn(xmlFile.getAbsoluteFile()+" parse error.", e);
			}
		}else{
			logger.warn(xmlFile==null?"xmlFile is null":xmlFile.getAbsoluteFile()+"file not exist.");
		}
		return rt;
	}
	/**
	 * 解析xml文件
	 * @param xmlFilePath
	 * @return
	 */
	public static Document parseXml(String xmlFilePath){
		return parseXml(new File(xmlFilePath));
	}
	/**
	 * 获取元素的属性
	 * @param e
	 * @param name
	 * @param defaultVal
	 * @return
	 */
	public static String getAttribute(Element e,String name,String defaultVal){
		return e.attributeValue(name, defaultVal);
	}
	/**
	 * 获取元素的属性
	 * @param e
	 * @param name
	 * @return
	 */
	public static String getAttribute(Element e,String name){
		return getAttribute(e, name, "");
	}
	/**
	 * 获取元素的int属性
	 * @param e
	 * @param name
	 * @param defaultVal
	 * @return
	 */
	public static int getInt(Element e,String name,int defaultVal){
		return StringUtil.toInt(getAttribute(e, name, defaultVal+""),defaultVal);
	}
	/**
	 * 获取元素的int属性
	 * @param e
	 * @param name
	 * @return
	 */
	public static int getInt(Element e,String name){
		return getInt(e, name, 0);
	}
	/**
	 * 获取元素的short属性
	 * @param e
	 * @param name
	 * @param defaultVal
	 * @return
	 */
	public static short getShort(Element e,String name,int defaultVal){
		return StringUtil.toShort(getAttribute(e, name, defaultVal+""),(short)defaultVal);
	}
	/**
	 * 获取元素的short属性
	 * @param e
	 * @param name
	 * @return
	 */
	public static short getShort(Element e,String name){
		return getShort(e, name, 0);
	}
	/**
	 * 获取元素的byte属性
	 * @param e
	 * @param name
	 * @param defaultVal
	 * @return
	 */
	public static byte getByte(Element e,String name,int defaultVal){
		return StringUtil.toByte(getAttribute(e, name, defaultVal+""),(byte)defaultVal);
	}
	/**
	 * 获取元素的byte属性
	 * @param e
	 * @param name
	 * @return
	 */
	public static byte getByte(Element e,String name){
		return getByte(e, name, 0);
	}
	/**
	 * 获取元素的long属性
	 * @param e
	 * @param name
	 * @param defaultVal
	 * @return
	 */
	public static long getLong(Element e,String name,long defaultVal){
		return StringUtil.toLong(getAttribute(e, name, defaultVal+""),defaultVal);
	}
	/**
	 * 获取元素的long属性
	 * @param e
	 * @param name
	 * @return
	 */
	public static long getLong(Element e,String name){
		return getLong(e, name, 0l);
	}
	/**
	 * 获取元素的float属性
	 * @param e
	 * @param name
	 * @param defaultVal
	 * @return
	 */
	public static float getFloat(Element e,String name,float defaultVal){
		return StringUtil.toFloat(getAttribute(e, name, defaultVal+""),defaultVal);
	}
	/**
	 * 获取元素的float属性
	 * @param e
	 * @param name
	 * @return
	 */
	public static float getFloat(Element e,String name){
		return getFloat(e, name, 0f);
	}
	/**
	 * 获取元素的double属性
	 * @param e
	 * @param name
	 * @param defaultVal
	 * @return
	 */
	public static double getDouble(Element e,String name,double defaultVal){
		return StringUtil.toDouble(getAttribute(e, name, defaultVal+""),defaultVal);
	}
	/**
	 * 获取元素的double属性
	 * @param e
	 * @param name
	 * @return
	 */
	public static double getDouble(Element e,String name){
		return getDouble(e, name, 0d);
	}
	/**
	 * 保存xml文件
	 * @param doc
	 * @param xmlFile
	 * @return
	 */
	public static boolean saveXmlFile(Document doc,File xmlFile){
		if(doc==null||xmlFile==null){
			logger.warn("save xml file error.doc or file is null.");
		}else{
			OutputFormat format=OutputFormat.createPrettyPrint();
			XMLWriter writer=null;
			try {
				writer=new XMLWriter(new OutputStreamWriter(new FileOutputStream(xmlFile)), format);
				writer.write(doc);
				writer.close();
				return true;
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(writer!=null){
					try {
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return false;
	}
	/**
	 * 保存xml文件
	 * @param doc
	 * @param xmlFilePath
	 * @return
	 */
	public static boolean saveXmlFile(Document doc,String xmlFilePath){
		return saveXmlFile(doc, new File(xmlFilePath));
	}
}
