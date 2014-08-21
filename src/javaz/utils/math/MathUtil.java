/**
 * 
 */
package javaz.utils.math;

import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

/**
 * @author Zero
 * @mail baozilaji@126.com
 *
 * Aug 9, 2014
 */
public class MathUtil {
	private static Logger logger=Logger.getLogger("Javaz::MathUtil");
	/**
	 * 随机对象
	 */
	private static Random random = new Random(System.currentTimeMillis());
	/**
	 * 随机类型
	 * @author Zero
	 * @mail baozilaji@126.com
	 *
	 * Aug 9, 2014
	 */
	public static enum RandomType{
		/** 半开半闭*/
		K,
		/** 必区间*/
		B
	}
	/**
	 * 随机数计算 [start,end ) or ]
	 * @param start
	 * @param end
	 * @param randomType
	 * @return
	 */
	public static int random(int start,int end,RandomType randomType){
		if(end-start<0){
			int smaller = end;
			end = start;
			start = smaller;
		}else if(end-start==0){
			return start;
		}
		int rt = start+random.nextInt(end-start);
		if(randomType==RandomType.B){
			rt += random.nextInt(2);
		}
		return rt;
	}
	/**
	 * 随机数计算 [start,end)
	 * @param start
	 * @param end
	 * @return
	 */
	public static int random(int start,int end){
		return random(start, end, RandomType.K);
	}
	/**
	 * 随机数计算 [0,i)
	 * @param i
	 * @return
	 */
	public static int random(int i){
		return random(0, i);
	}
	/**
	 * 概率命中 (max分之percent的概率命中)
	 * @param percent
	 * @param max
	 * @return
	 */
	public static boolean isHitRandom(int percent,int max){
		if(percent>=max){
			return true;
		}
		int r = random(1, max,RandomType.B);
		if(r<=percent){
			return true;
		}
		return false;
	}
	/**
	 * 概率命中 (100分之percent的概率命中)
	 * @param percent
	 * @return
	 */
	public static boolean isHitRandom(int percent){
		return isHitRandom(percent, 100);
	}
	/**
	 * 取整函数
	 * @param d
	 * @return
	 */
	public static int round(double d){
		int ret=0;
		ret=(int) Math.round(d);
		return ret;
	}
	/**
	 * 按权重随机抽取
	 * @param listRate
	 * @param rateMaxNum
	 * @return
	 */
	public static int getRandomIndex(List<Integer> listRate,int rateMaxNum) {
		int left = 0;
		int right = 0;
		int index = -1;
		int random = random(rateMaxNum);
		for(int i=0; i < listRate.size(); i++){
			right = left + listRate.get(i);
			if(random >= left && random < right){
				index = i;
				break;
			}
			left = right;
		}
		if(index==-1){
			StringBuilder sb=new StringBuilder(",rates:");
			for(Integer i:listRate){
				sb.append(i).append(",");
			}
			logger.warn("max:"+rateMaxNum+sb.toString());
		}
		return index;
	}
	/**
	 * 按权重随机抽取
	 * @param listRate
	 * @return
	 */
	public static int getRandomIndex(List<Integer> listRate) {
		int max=0;
		for(Integer i:listRate){
			max+=i;
		}
		return getRandomIndex(listRate, max);
	}
	/**
	 * 生成指定长度的随机数
	 * @param len
	 * @return
	 */
	public static long getRandomNum(int len){
		long rt = 0;
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<len;i++){
			if(i==0){
				sb.append(random(1, 10));
			}else{
				sb.append(random(10));
			}
		}
		try {
			rt = Long.parseLong(sb.toString());
		} catch (NumberFormatException e) {
			rt=Long.MAX_VALUE;
			logger.warn("return max long value.", e);
		}
		return rt;
	}
	/**
	 * 生成指定长度的随机负数
	 * @param len
	 * @return
	 */
	public static long getNegativeRandomNum(int len){
		return getRandomNum(len)*(-1);
	}
}
