/**
 * 
 */
package javaz.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import javaz.utils.date.DateUtil;

/**
 * @author Zero
 *
 * 2014年8月9日
 */
public class CommUtil {
	/**
	 * map转json字符串
	 * @param map
	 * @return
	 */
	public static String getJsonStr(Map<String, String> map){
		return JSON.toJSONString(map);
	}
	/**
	 * json对象转map
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,String> getMapFromJson(String json){
		return JSON.parseObject(json, Map.class);
	}
	/**
	 * 判断某个list是否没有数据
	 * @param list
	 * @return
	 */
	public static <T> boolean isEmpityList(List<T> list){
		boolean b = false;
		if(list==null||list.isEmpty()){
			b=true;
		}
		return b;
	}
	/**
	 * 截取某列表的部分数据
	 * @param list
	 * @param skip
	 * @param pageSize
	 * @return
	 */
	public static <T> List<T> getSubListPage(List<T> list, int skip, int pageSize) {
		if(list==null||list.isEmpty()){
			return null;
		}
		int startIndex = skip;
		int endIndex = skip+pageSize;
		if(startIndex>endIndex||startIndex>list.size()){
			return null;
		}
		if(endIndex>list.size()){
			endIndex = list.size();
		}
		return list.subList(startIndex, endIndex);
	}
	/**
	 * 获取某个对象某些字段的Map
	 * @param obj
	 * @param strings
	 * @return
	 */
	public static Map<String,String> getMap(Object obj,String ...strings){
		Map<String,String> map = new HashMap<String, String>();
		boolean addAllFields = false;
		if(strings==null||strings.length==0){
			addAllFields=true;
		}
		if(obj!=null){
			Field[] fields = getAllFields(obj);
			for(Field field:fields){
				field.setAccessible(true);
				try {
					boolean needsAddToMap = false;
					for(String s:strings){
						if(field.getName().equals(s)){
							needsAddToMap=true;
							break;
						}
					}
					if(needsAddToMap||addAllFields){
						map.put(field.getName(), getFieldsValueStr(obj,field.getName()).toString());
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if(!addAllFields&&strings.length!=map.size()){
			return new HashMap<String, String>();
		}
		return map;
	}
	private static Field[] getAllFields(Object obj){
		Class<?> clazz = obj.getClass();
		Field[] rt=null;
		for(;clazz!=Object.class;clazz=clazz.getSuperclass()){
			Field[] tmp = clazz.getDeclaredFields();
			rt = combine(rt, tmp);
		}
		return rt;
	}
	private static Field[] combine(Field[] a, Field[] b){
		if(a==null){
			return b;
		}
		if(b==null){
			return a;
		}
		Field[] rt = new Field[a.length+b.length];
		System.arraycopy(a, 0, rt, 0, a.length);
		System.arraycopy(b, 0, rt, a.length, b.length);
		return rt;
	}
	private static String getFieldsValueStr(Object obj,String fieldName){
		Object o = getFieldsValueObj(obj, fieldName);
		if(o instanceof Date){
			return DateUtil.dateToString((Date)o);
		}
		return o.toString();
	}
	private static Object getFieldsValueObj(Object obj,String fieldName){
		Field field = getDeclaredField(obj,fieldName);
		field.setAccessible(true);
		try {
			return field.get(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private static Field getDeclaredField(Object object, String fieldName){
		Class<?> clazz = object.getClass();
		for(; clazz != Object.class ; clazz = clazz.getSuperclass()) {
			try {
				return clazz.getDeclaredField(fieldName);
			} catch (Exception e) {
			}
		}
		return null;
    }
	/**
	 * 从map中构造一个对象
	 * @param map
	 * @param clazz
	 * @return
	 */
	public static <T> T getObjFromMap(Map<String,String> map,Class<?> clazz){
		try {
			Object obj=clazz.newInstance();
			return getObjFromMap(map, obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public static <T> T getObjFromMap(Map<String,String> map, Object obj){
		try {
			for(String key:map.keySet()){
				Field field=getDeclaredField(obj, key);
				Method method=getSetMethod(obj, buildSetMethod(key), field.getType());
				if(field.getType()==Integer.class||field.getType()==int.class){
					method.invoke(obj, Integer.parseInt(map.get(key)));
				}else if(field.getType()==Boolean.class||field.getType()==boolean.class){
					method.invoke(obj, Boolean.parseBoolean(map.get(key)));
				}else if(field.getType()==Long.class||field.getType()==long.class){
					method.invoke(obj, Long.parseLong(map.get(key)));
				}else if(field.getType()==Float.class||field.getType()==float.class){
					method.invoke(obj, Float.parseFloat(map.get(key)));
				}else if(field.getType()==Double.class||field.getType()==double.class){
					method.invoke(obj, Double.parseDouble(map.get(key)));
				}else if(field.getType()==Byte.class||field.getType()==byte.class){
					method.invoke(obj, Byte.parseByte(map.get(key)));
				}else if(field.getType()==Short.class||field.getType()==short.class){
					method.invoke(obj, Short.parseShort(map.get(key)));
				}else if(field.getType()==String.class){
					method.invoke(obj, map.get(key));
				}else if(field.getType()==Date.class){
					method.invoke(obj, DateUtil.stringToDate(map.get(key)));
				}
			}
			return (T)obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private static Method getSetMethod(Object object,String method,Class<?> fieldType){
		Class<?> clazz=object.getClass();
		for(;clazz!=Object.class;clazz=clazz.getSuperclass()){
			try {
				return clazz.getDeclaredMethod(method, fieldType);
			} catch (Exception e) {
			}
		}
		return null;
	}
	private static String buildSetMethod(String fieldName){
		StringBuffer sb=new StringBuffer("set");
		if(fieldName.length()>1){
			String first=fieldName.substring(0, 1);
			String next=fieldName.substring(1);
			sb.append(first.toUpperCase()).append(next);
		}else{
			sb.append(fieldName.toUpperCase());
		}
		return sb.toString();
	}
}
