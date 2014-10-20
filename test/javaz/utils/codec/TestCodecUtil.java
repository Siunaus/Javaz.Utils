/**
 * 
 */
package javaz.utils.codec;

import java.io.Serializable;

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
	@Test
	public void testBase64(){
		String nameString="Zero丹峰张";
		String encode=CodecUtil.base64Encode(nameString);
		System.out.println(encode);
		System.out.println(new String(CodecUtil.base64Decode(encode)));
		System.out.println(CodecUtil.base64Encode("zhang但分"));
		System.out.println(CodecUtil.base64Decode("emhhbmfkvYbliIY="));
	}
	public static class Obj implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public String name;
		public int age;
		public int sex;
		public String pic;
	}
	@Test
	public void testObject(){
		Obj obj=new Obj();
		obj.age=10;
		obj.name="Zero";
		obj.sex=1;
		obj.pic="http://xxx/xxx/xxx.png";
		byte[] bs=CodecUtil.encode(obj);
		for(int i=0,j=bs.length;i<j;i++){
			System.out.print(bs[i]);
			System.out.print(",");
		}
		System.out.println();
		Obj o=CodecUtil.decode(bs);
		System.out.println(o.age);
		System.out.println(o.name);
	}
}
