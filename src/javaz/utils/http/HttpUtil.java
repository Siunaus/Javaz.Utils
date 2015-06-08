/**
 * 
 */
package javaz.utils.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javaz.utils.string.StringUtil;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson.JSONObject;

/**
 * @author Zero
 * @mail baozilaji@126.com
 *
 * Sep 18, 2014
 */
public class HttpUtil {
	static Logger logger=Logger.getLogger(HttpUtil.class);
	private static final int HTTP_TIME_OUT=6000;
	private static final RequestConfig requestConfig=RequestConfig.custom().setSocketTimeout(HTTP_TIME_OUT).setConnectTimeout(HTTP_TIME_OUT).build();
	private static final String ENCODING="utf-8";
	private static enum ProtocolType{
		HTTP,
		HTTPS
	}
	private static enum ReqType{
		GET,
		POST
	}
	private static enum ResDataType{
		STRING,
		JSON,
		XML
	}
	private static String urlEncode(String string,String charset){
		try {
			return URLEncoder.encode(string, charset);
		} catch (UnsupportedEncodingException e) {
			logger.error(StringUtil.format("url encode error:{0}", string), e);
		}
		return "";
	}
	private static CloseableHttpClient createSSLClientDefault(){
		SSLContextBuilder sslContextBuilder=new SSLContextBuilder();
		try {
			sslContextBuilder.loadTrustMaterial(null, new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] arg0, String arg1)throws CertificateException {
					return true;
				}
			});
			SSLConnectionSocketFactory sslsf=new SSLConnectionSocketFactory(sslContextBuilder.build());
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (NoSuchAlgorithmException e1) {
			logger.error("NoSuchAlgorithmException", e1);
		} catch (KeyStoreException e1) {
			logger.error("KeyStoreException", e1);
		} catch (KeyManagementException e1) {
			logger.error("KeyManagementException", e1);
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	private static <T> T request(ProtocolType protocolType,ReqType reqType,ResDataType resDataType,String url,Map<String, String> param,Map<String,String> heads,Map<String, String> cookies,String charset){
		CloseableHttpClient client=null;
		if(protocolType==ProtocolType.HTTP){
			client=HttpClients.createDefault();
		}else{
			client=createSSLClientDefault();
		}
		if(client==null){
			logger.error("http request error:can't creat an closeable httpclient.");
			return null;
		}
		HttpRequestBase request=null;
		if(reqType==ReqType.POST){
			request=new HttpPost(url);
			request.setHeader("Content-Type", "application/x-www-form-urlencoded");
			if(param!=null&&!param.isEmpty()){
				List<NameValuePair> formParam=new ArrayList<NameValuePair>();
				for(Entry<String, String> entry:param.entrySet()){
					formParam.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				try {
					UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formParam, ENCODING);
					((HttpPost)request).setEntity(urlEncodedFormEntity);
				} catch (UnsupportedEncodingException e) {
					logger.error("", e);
				}
			}
		}else{
			StringBuilder sbBuilder=new StringBuilder(url);
			if(param!=null&&!param.isEmpty()){
				String prefix="?";
				if(sbBuilder.indexOf("?")!=-1){
					prefix="&";
				}
				sbBuilder.append(prefix);
				for(Entry<String, String> entry:param.entrySet()){
					sbBuilder.append(entry.getKey()).append("=").append(urlEncode(entry.getValue(), ENCODING)).append("&");
				}
			}
			request=new HttpGet(sbBuilder.toString());
		}
		request.setConfig(requestConfig);
		CloseableHttpResponse response=null;
		try {
			if(heads!=null&&!heads.isEmpty()){
				for(Entry<String, String>entry:heads.entrySet()){
					request.setHeader(entry.getKey(), entry.getValue());
				}
			}
			if(cookies!=null&&!cookies.isEmpty()){
				StringBuilder sb=new StringBuilder();
				for(Entry<String, String> entry:cookies.entrySet()){
					sb.append(entry.getKey()).append("=").append(urlEncode(entry.getValue(), ENCODING)).append(";");
				}
				request.setHeader("Cookie", sb.toString());
			}
			response=client.execute(request);
			if(resDataType==ResDataType.XML){
				SAXReader saxReader = new SAXReader();
				saxReader.setEncoding(charset);
				Document doc = saxReader.read(response.getEntity().getContent());
				return (T)doc.getRootElement();
			}else if(resDataType==ResDataType.JSON){
				return (T)JSONObject.parseObject(EntityUtils.toString(response.getEntity(), charset));
			}else{
				return (T)EntityUtils.toString(response.getEntity(), charset);
			}
		} catch (ClientProtocolException e) {
			logger.error("ClientProtocolException:", e);
		} catch (IOException e) {
			logger.error("IOException:", e);
		} catch (IllegalStateException e) {
			logger.error("IllegalStateException:", e);
		} catch (DocumentException e) {
			logger.error("DocumentException:", e);
		}finally{
			if(response!=null){
				try {
					response.close();
				} catch (IOException e) {
					logger.error("response.close->IOException:", e);
				}
			}
			try {
				client.close();
			} catch (IOException e) {
				logger.error("client.close->IOException:", e);
			}
		}
		return null;
	}
	////////https get//////
	public static String httpsGet(String url,Map<String, String> param){
		return request(ProtocolType.HTTPS, ReqType.GET, ResDataType.STRING, url, param, null, null, ENCODING);
	}
	public static JSONObject httpsGetJson(String url,Map<String, String>param){
		return request(ProtocolType.HTTPS, ReqType.GET, ResDataType.JSON, url, param, null, null, ENCODING);
	}
	public static Element httpsGetXml(String url, Map<String, String> param){
		return request(ProtocolType.HTTPS, ReqType.GET, ResDataType.XML, url, param, null, null, ENCODING);
	}
	public static String httpsGet(String url,Map<String, String> param,Map<String,String> heads,Map<String, String> cookies){
		return request(ProtocolType.HTTPS, ReqType.GET, ResDataType.STRING, url, param, heads, cookies, ENCODING);
	}
	public static JSONObject httpsGetJson(String url,Map<String, String>param,Map<String,String> heads,Map<String, String> cookies){
		return request(ProtocolType.HTTPS, ReqType.GET, ResDataType.JSON, url, param, heads, cookies, ENCODING);
	}
	public static Element httpsGetXml(String url, Map<String, String> param,Map<String,String> heads,Map<String, String> cookies){
		return request(ProtocolType.HTTPS, ReqType.GET, ResDataType.XML, url, param, heads, cookies, ENCODING);
	}
	////////https post//////
	public static String httpsPost(String url,Map<String, String> param){
		return request(ProtocolType.HTTPS, ReqType.POST, ResDataType.STRING, url, param, null, null, ENCODING);
	}
	public static JSONObject httpsPostJson(String url,Map<String, String>param){
		return request(ProtocolType.HTTPS, ReqType.POST, ResDataType.JSON, url, param, null, null, ENCODING);
	}
	public static Element httpsPostXml(String url, Map<String, String> param){
		return request(ProtocolType.HTTPS, ReqType.POST, ResDataType.XML, url, param, null, null, ENCODING);
	}
	public static String httpsPost(String url,Map<String, String> param,Map<String,String> heads,Map<String, String> cookies){
		return request(ProtocolType.HTTPS, ReqType.POST, ResDataType.STRING, url, param, heads, cookies, ENCODING);
	}
	public static JSONObject httpsPostJson(String url,Map<String, String>param,Map<String,String> heads,Map<String, String> cookies){
		return request(ProtocolType.HTTPS, ReqType.POST, ResDataType.JSON, url, param, heads, cookies, ENCODING);
	}
	public static Element httpsPostXml(String url, Map<String, String> param,Map<String,String> heads,Map<String, String> cookies){
		return request(ProtocolType.HTTPS, ReqType.POST, ResDataType.XML, url, param, heads, cookies, ENCODING);
	}
	//////////http get///////
	public static String httpGet(String url,Map<String,String> param){
		return request(ProtocolType.HTTP, ReqType.GET, ResDataType.STRING, url, param, null, null, ENCODING);
	}
	public static JSONObject httpGetJson(String url,Map<String, String> param){
		return request(ProtocolType.HTTP, ReqType.GET, ResDataType.JSON, url,param,null,null, ENCODING);
	}
	public static Element httpGetXml(String url, Map<String, String> param){
		return request(ProtocolType.HTTP, ReqType.GET, ResDataType.XML, url, param, null, null, ENCODING);
	}
	public static String httpGet(String url,Map<String,String> param,Map<String,String> heads,Map<String, String> cookies){
		return request(ProtocolType.HTTP, ReqType.GET, ResDataType.STRING, url, param, heads, cookies, ENCODING);
	}
	public static JSONObject httpGetJson(String url,Map<String, String> param,Map<String,String> heads,Map<String, String> cookies){
		return request(ProtocolType.HTTP, ReqType.GET, ResDataType.JSON, url,param,heads,cookies, ENCODING);
	}
	public static Element httpGetXml(String url, Map<String, String> param,Map<String,String> heads,Map<String, String> cookies){
		return request(ProtocolType.HTTP, ReqType.GET, ResDataType.XML, url, param, heads, cookies, ENCODING);
	}
	////////http post////////
	public static String httpPost(String url,Map<String, String> param){
		return request(ProtocolType.HTTP, ReqType.POST, ResDataType.STRING, url, param, null, null, ENCODING);
	}
	public static JSONObject httpPostJson(String url,Map<String, String> param){
		return request(ProtocolType.HTTP, ReqType.POST, ResDataType.JSON, url,param,null,null, ENCODING);
	}
	public static Element httpPostXml(String url, Map<String, String> param){
		return request(ProtocolType.HTTP, ReqType.POST, ResDataType.XML, url, param, null, null, ENCODING);
	}
	public static String httpPost(String url,Map<String, String> param,Map<String,String> heads,Map<String, String> cookies){
		return request(ProtocolType.HTTP, ReqType.POST, ResDataType.STRING, url, param, heads, cookies, ENCODING);
	}
	public static JSONObject httpPostJson(String url,Map<String, String> param,Map<String,String> heads,Map<String, String> cookies){
		return request(ProtocolType.HTTP, ReqType.POST, ResDataType.JSON, url,param,heads,cookies, ENCODING);
	}
	public static Element httpPostXml(String url, Map<String, String> param,Map<String,String> heads,Map<String, String> cookies){
		return request(ProtocolType.HTTP, ReqType.POST, ResDataType.XML, url, param, heads, cookies, ENCODING);
	}
	public static void main(String[] args) throws Exception{
//		Map<String, String> paramMap=new HashMap<String, String>();
//		paramMap.put("access_token", "CAACEdEose0cBABZC7johp8G7xDJUiZBpE8iXVPXZBGVb3FT9IT0B1fDRHoTWozJjMxvTi47UTlI3YbiutyTEZBLf2KizGHIHhZALmB24YDOXKZBt1UZCZAXatQg4jTmWnlHbMs1hGoZA8RhqukP6VjUvaNohFscy0QqZBhG5y0wXPgbzjmUeGDUZAQF9tq4CcURo4SbWxEAaHVNLm873UDkQFQJvsB6VSslBN8ZD");
//		paramMap.put("fields", "id,name");
//		System.out.println(httpsGet("https://graph.facebook.com/v2.1/me", paramMap));
		
		
//		System.out.println(httpGet("http://oa.leuok.com/sys.d?a=index", null));
		
		Map<String, String> paramMap=new HashMap<String, String>();
		paramMap.put("k", "ddh4fgdfadfm3PdfsdfdhPMw11PNsJx8");
		System.out.println(httpPost("http://114.66.5.82:10101/game_state_xxyyzzYuxoq4sfy0Iuy.jsp", paramMap));
		System.out.println(httpGet("http://114.66.5.82:10101/game_state_xxyyzzYuxoq4sfy0Iuy.jsp", paramMap));
	}
}
