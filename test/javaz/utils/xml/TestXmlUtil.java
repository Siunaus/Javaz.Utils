/**
 * 
 */
package javaz.utils.xml;

import javaz.utils.xml.XmlUtil;
import junit.framework.TestCase;

import org.dom4j.Document;
import org.dom4j.Element;
import org.junit.Test;

/**
 * @author Zero
 * @mail baozilaji@126.com
 *
 * Aug 9, 2014
 */
public class TestXmlUtil extends TestCase {
	@Test
	public void testXmlFile() throws Exception {
		Document document=XmlUtil.parseXml("/Users/Zero/temp/protocols.xml");
		Element rootElement=document.getRootElement();
		System.out.println(rootElement.getName());
		System.out.println(XmlUtil.saveXmlFile(document, "/Users/Zero/temp/protocols1.xml"));
	}
}
