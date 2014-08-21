/**
 * 
 */
package javaz.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * @author Zero
 * @mail baozilaji@126.com
 *
 * Aug 9, 2014
 */
public class DateUtil {
	private static Logger logger=Logger.getLogger("Javaz::DateUtil");
	/**
	 * 默认的日期格式
	 */
	public static final String DEFAULT_DATE_FAMAT="yyyy-MM-dd HH:mm:ss";
	/**
	 * 获取系统时间
	 * @return
	 */
	public static Date now(){
		return new Date();
	}
	/**
	 * 获取系统时间，毫秒级
	 * @return
	 */
	public static long getCurrentTimeMillis(){
		return System.currentTimeMillis();
	}
	/**
	 * 字符串转日期 按指定格式
	 * @param stringValue
	 * @param format
	 * @return
	 */
	public static Date stringToDate(String stringValue,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(stringValue);
		} catch (ParseException e) {
			logger.warn("parse <"+stringValue+"> use format<"+format+"> error", e);
		}
		return null;
	}
	/**
	 * 字符串转日期 按默认格式
	 * @param stringValue
	 * @return
	 */
	public static Date stringToDate(String stringValue){
		return stringToDate(stringValue,DEFAULT_DATE_FAMAT);
	}
	/**
	 * 格式化输出日期
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToString(Date date,String format){
		SimpleDateFormat time = new SimpleDateFormat(format);
		return time.format(date);
	}
	/**
	 * 日期转换为默认格式字符串
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		return dateToString(date, DEFAULT_DATE_FAMAT);
	}
	/**
	 * 获取当前日期是本周的周几
	 * @param date
	 * @return
	 */
	public static int getDayOfWeek(Date date){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}
	/**
	 * 获取当前日期是今年的第几周
	 * @param date
	 * @return
	 */
	public static int getWeekOfYear(Date date){
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}
	/**
	 * 时间相隔类型
	 * @author Zero
	 * @mail baozilaji@126.com
	 *
	 * Aug 9, 2014
	 */
	public static enum DelayType{
		/** 两日期相差总天数*/
		DELAY_TYPE_TOTAL_DAY,
		/** 两日期相差总小时数*/
		DELAY_TYPE_TOTAL_HOUR,
		/** 两日期相差总分钟数*/
		DELAY_TYPE_TOTAL_MINUTE,
		/** 两日期相差总秒数*/
		DELAY_TYPE_TOTAL_SECOND,
		/** 两日期相差小时数(忽略天)*/
		DELAY_TYPE_HOUR,
		/** 两日期相差分钟数(忽略天，小时)*/
		DELAY_TYPE_MINUTE,
		/** 两日期相差秒数(忽略天，小时和分钟)*/
		DELAY_TYPE_SECOND
	}
	private static int delay(Date date1,Date date2,DelayType type){
		return delay(date1.getTime(), date2.getTime(), type);
	}
	/**
	 * 根据相隔类型，计算两个时间戳相隔的时间
	 * @param t1
	 * @param t2
	 * @param type
	 * @return
	 */
	private static int delay(long t1,long t2,DelayType type){
		long fenzi = 0;
		if(t1<t2){
			fenzi = t2-t1;
		}else{
			fenzi = t1-t2;
		}
		long fenmu = 1000;
		switch(type){
		case DELAY_TYPE_TOTAL_DAY:
			fenmu *= 3600*24;
			break;
		case DELAY_TYPE_TOTAL_HOUR:
			fenmu *= 3600;
			break;
		case DELAY_TYPE_TOTAL_MINUTE:
			fenmu *= 60;
			break;
		case DELAY_TYPE_TOTAL_SECOND:
			//do nothing here
			break;
		case DELAY_TYPE_HOUR:
			fenzi %= 3600000*24;
			fenmu *=3600;
			break;
		case DELAY_TYPE_MINUTE:
			fenzi %= 3600000;
			fenmu *= 60;
			break;
		case DELAY_TYPE_SECOND:
			fenzi %= 60000;
			break;
		default:
			return 0;
		}
		return (int)(fenzi/fenmu);
	}
	/**
	 * 计算两个日期相差的总天数
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int delayTotalDay(Date date1,Date date2){
		return delay(date1, date2, DelayType.DELAY_TYPE_TOTAL_DAY);
	}
	/**
	 * 计算两个日期相差的总小时数
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int delayTotalHour(Date date1,Date date2){
		return delay(date1, date2, DelayType.DELAY_TYPE_TOTAL_HOUR);
	}
	/**
	 * 计算两个日期相差的总分钟数
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int delayTotalMinute(Date date1,Date date2){
		return delay(date1, date2, DelayType.DELAY_TYPE_TOTAL_MINUTE);
	}
	/**
	 * 计算两个日期相差的总秒数
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int delayTotalSecond(Date date1,Date date2){
		return delay(date1, date2, DelayType.DELAY_TYPE_TOTAL_SECOND);
	}
	/**
	 * 计算两个日期相差的小时数（忽略天）
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int delayHour(Date date1,Date date2){
		return delay(date1, date2, DelayType.DELAY_TYPE_HOUR);
	}
	/**
	 * 计算两个日期相差的分钟数（忽略天，小时）
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int delayMinute(Date date1,Date date2){
		return delay(date1, date2, DelayType.DELAY_TYPE_MINUTE);
	}
	/**
	 * 计算两个日期相差的秒数（忽略天，小时和分钟数）
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int delaySecond(Date date1,Date date2){
		return delay(date1, date2, DelayType.DELAY_TYPE_SECOND);
	}
	
	/** 之后*/
	private static final int ACTION_AFTER=0;
	/** 之前*/
	private static final int ACTION_BEFORE=1;
	/** 天*/
	private static final int TYPE_DAY=0;
	/** 小时*/
	private static final int TYPE_HOUR=1;
	/** 分钟*/
	private static final int TYPE_MINUTE=2;
	/** 秒*/
	private static final int TYPE_SECOND=3;
	/**
	 * 获取 count(10) type(分钟 小时 天 秒) action(之后 之前) 的日期
	 * @param date
	 * @param type
	 * @param action
	 * @param count
	 * @return
	 */
	private static Date getDate(Date date,int type,int action,int count){
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		count=Math.abs(count);
		switch(type){
		case TYPE_DAY:
			if(action==ACTION_AFTER){
				now.set(Calendar.DATE, now.get(Calendar.DATE) + count);
			}else if(action==ACTION_BEFORE){
				now.set(Calendar.DATE, now.get(Calendar.DATE) - count);
			}
			break;
		case TYPE_HOUR:
			if(action==ACTION_AFTER){
				now.set(Calendar.HOUR, now.get(Calendar.HOUR) + count);
			}else if(action==ACTION_BEFORE){
				now.set(Calendar.HOUR, now.get(Calendar.HOUR) - count);
			}
			break;
		case TYPE_MINUTE:
			if(action==ACTION_AFTER){
				now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) + count);
			}else if(action==ACTION_BEFORE){
				now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) - count);
			}
			break;
		case TYPE_SECOND:
			if(action==ACTION_AFTER){
				now.set(Calendar.SECOND, now.get(Calendar.SECOND) + count);
			}else if(action==ACTION_BEFORE){
				now.set(Calendar.SECOND, now.get(Calendar.SECOND) - count);
			}
			break;
		}
		return now.getTime();
	}
	/**
	 * 得到某天前几天日期
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date getDateBefore(Date date,int day){
		return getDate(date, TYPE_DAY, ACTION_BEFORE, day);
	}
	/**
	 * 得到某天后几天日期
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date getDateAfter(Date date,int day){
		return getDate(date, TYPE_DAY, ACTION_AFTER, day);
	}
	/**
	 * 得到某天多少小时前的日期
	 * @param date
	 * @param hour
	 * @return
	 */
	public static Date getHourBefore(Date date,int hour){
		return getDate(date, TYPE_HOUR, ACTION_BEFORE, hour);
	}
	/**
	 * 得到某天多少小时后的日期
	 * @param date
	 * @param hour
	 * @return
	 */
	public static Date getHourAfter(Date date,int hour){
		return getDate(date, TYPE_HOUR, ACTION_AFTER, hour);
	}
	/**
	 * 得到某天多少分钟后的日期
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date getMinuteAfter(Date date,int minute){
		return getDate(date, TYPE_MINUTE, ACTION_AFTER, minute);
	}
	/**
	 * 得到某天多少分钟前的日期
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date getMinuteBefore(Date date,int minute){
		return getDate(date, TYPE_MINUTE, ACTION_BEFORE, minute);
	}
	/**
	 * 得到某天多少秒钟前的日期
	 * @param date
	 * @param second
	 * @return
	 */
	public static Date getSecondBefore(Date date,int second){
		return getDate(date, TYPE_SECOND, ACTION_BEFORE, second);
	}
	/**
	 * 得到某天多少秒钟前的日期
	 * @param date
	 * @param second
	 * @return
	 */
	public static Date getSecondAfter(Date date,int second){
		return getDate(date, TYPE_SECOND, ACTION_AFTER, second);
	}
	/**
	 * 两日起是否是同一天
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean areSameDays(Date date1,Date date2){
		if(date1==null||date2==null){
			return false;
		}
		if(dateToString(date1, "yyyy-MM-dd").equals(dateToString(date2, "yyyy-MM-dd"))){
			return true;
		}
		return false;
	}
	/**
	 * 是否介于两个日期之间
	 * @param date
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isBetweenDays(Date date,Date date1,Date date2){
		if(date==null||date1==null||date2==null){
			return false;
		}
		if(date1.before(date2)
				&& date.after(date1)
					&& date.before(date2)){
			return true;
		}else if(date.before(date1)
					&& date.after(date2)){
			return true;
		}
		return false;
	}
}
