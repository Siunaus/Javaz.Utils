/**
 * 
 */
package javaz.utils.codec;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.MessageDigest;

/**
 * @author Zero
 * @mail baozilaji@126.com
 *
 * Aug 9, 2014
 */
public class CodecUtil {
	/**
	 * 反序列化
	 * @param bytes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T decode(byte[] bytes){
		if(bytes==null||bytes.length==0) return null;
		ObjectInputStream objIn = null;
		try {
			objIn = new ObjectInputStream(new ByteArrayInputStream(bytes));
			Object object = objIn.readObject();
			return (T) object;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			if(objIn!=null){
				try {
					objIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	/**
	 * 序列化
	 * @param object
	 * @return
	 */
	public static <T> byte[] encode(T object){
		if(object==null) return null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream objectOut = null;
		try {
			objectOut = new ObjectOutputStream(out);
			objectOut.writeObject(object);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(objectOut!=null){
				try {
					objectOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return out.toByteArray();
	}
	/**
	 * 清除文件UTF8的BOM格式
	 * @param file
	 */
	public static void clearUTF8BOM(File file){
		FileInputStream fis = null;
		InputStream is = null;
		ByteArrayOutputStream baos=null;
		OutputStream ops=null;
		try {
			fis = new FileInputStream(file);
			is = new BufferedInputStream(fis);
			baos = new ByteArrayOutputStream();
			ops = null;
			byte b[] = new byte[3];
			is.read(b);
			
			if(-17==b[0]&&-69==b[1]&&-65==b[2]){
				System.out.println("file:"+file.getAbsolutePath());
				b = new byte[1024];
				while(true){
					int bytes=0;
					bytes = is.read(b);
					if(bytes==-1){
						break;
					}
					baos.write(b, 0, bytes);
					
					b = baos.toByteArray();
				}
				file.delete();
				ops=new FileOutputStream(file);
				baos.writeTo(ops);
			}
		} catch (Exception e) {
		}finally{
			try {
				if(fis!=null){
					fis.close();
				}
				if(ops!=null){
					ops.close();
				}
				if(is!=null){
					is.close();
				}
				if(baos!=null){
					baos.close();
				}
			} catch (IOException e) {
			}
		}
	}
	/**
	 * MD5加密
	 * @param source
	 * @return
	 */
	private static final char[] hexDigits={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	public static String MD5(String source){
		return MD5(source,"utf8");
	}
	public static String MD5(String source,String charset){
		String rt="";
		try {
			MessageDigest mg = MessageDigest.getInstance("MD5");
			mg.update(source.getBytes(charset));
			byte[] md = mg.digest();
			char str[] = new char[md.length*2];
			for(int i=0,k=0;i<md.length;i++){
				byte b = md[i];
				str[k++]=hexDigits[b>>>4&0xf];
				str[k++]=hexDigits[b&0xf];
			}
			rt = new String(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rt;
	}
	private static char[] base64EncodeChars = new char[] {
											'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
											'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
											'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
											'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
											'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
											'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
											'w', 'x', 'y', 'z', '0', '1', '2', '3',
											'4', '5', '6', '7', '8', '9', '+', '/' };
	private static byte[] base64DecodeChars = new byte[] {
											-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
											-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
											-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,
											52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,
											-1,0,1,2,3,4,5,6,7,8,9, 10, 11, 12, 13, 14,
											15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,
											-1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
											41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1 };
	/**
	 * BASE64编码
	 * @param data
	 * @return
	 */
	public static String base64Encode(byte[] data) {
		StringBuffer sb = new StringBuffer();
		int len = data.length;
		int i = 0;
		int b1, b2, b3;

		while (i < len) {
			b1 = data[i++] & 0xff;
			if (i == len) {
				sb.append(base64EncodeChars[b1 >>> 2]);
				sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
				sb.append("==");
				break;
			}
			b2 = data[i++] & 0xff;
			if (i == len) {
				sb.append(base64EncodeChars[b1 >>> 2]);
				sb.append(base64EncodeChars[((b1 & 0x03) << 4)
						| ((b2 & 0xf0) >>> 4)]);
				sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
				sb.append("=");
				break;
			}
			b3 = data[i++] & 0xff;
			sb.append(base64EncodeChars[b1 >>> 2]);
			sb.append(base64EncodeChars[((b1 & 0x03) << 4)
					| ((b2 & 0xf0) >>> 4)]);
			sb.append(base64EncodeChars[((b2 & 0x0f) << 2)
					| ((b3 & 0xc0) >>> 6)]);
			sb.append(base64EncodeChars[b3 & 0x3f]);
		}
		return sb.toString();
	} 
	/**
	 * BASE64解码
	 * @param str
	 * @return
	 */
	public static byte[] base64Decode(String str) {
		byte[] data = str.getBytes();
		int len = data.length;
		ByteArrayOutputStream buf = new ByteArrayOutputStream(len);
		int i = 0;
		int b1, b2, b3, b4;

		while (i < len) {

			do {
				b1 = base64DecodeChars[data[i++]];
			} while (i < len && b1 == -1);
			if (b1 == -1) {
				break;
			}

			do {
				b2 = base64DecodeChars[data[i++]];
			} while (i < len && b2 == -1);
			if (b2 == -1) {
				break;
			}
			buf.write((int) ((b1 << 2) | ((b2 & 0x30) >>> 4)));

			do {
				b3 = data[i++];
				if (b3 == 61) {
					return buf.toByteArray();
				}
				b3 = base64DecodeChars[b3];
			} while (i < len && b3 == -1);
			if (b3 == -1) {
				break;
			}
			buf.write((int) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));

			do {
				b4 = data[i++];
				if (b4 == 61) {
					return buf.toByteArray();
				}
				b4 = base64DecodeChars[b4];
			} while (i < len && b4 == -1);
			if (b4 == -1) {
				break;
			}
			buf.write((int) (((b3 & 0x03) << 6) | b4));
		}
		return buf.toByteArray();
	}
}
