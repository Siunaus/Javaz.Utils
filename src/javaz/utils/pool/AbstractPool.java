/**
 * 
 */
package javaz.utils.pool;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javaz.utils.properties.DefaultProperties;

/**
 * @author Zero
 * @param <T>
 * @mail baozilaji@126.com
 *
 * Aug 7, 2014
 */
public abstract class AbstractPool<T> implements Pool<T> {
	private int minSize;
	protected Queue<T> queue=new ConcurrentLinkedQueue<T>();
	protected Factory<T> builder;
	private static final String KEY_POOL_MIN_SIZE="pool_min_size";
	/**
	 * 
	 */
	public AbstractPool() {
		minSize=DefaultProperties.getInstance().getInt(KEY_POOL_MIN_SIZE, 1000);
	}
	/**
	 * 获取对象
	 */
	public T borrowObj() {
		T rt = queue.poll();
		if(rt==null){
			if(builder!=null){
				rt=builder.buildObj();
			}else{
				throw new Error("do not set factory yet.");
			}
		}
		return rt;
	}
	/**
	 * 返回对象
	 */
	public void returnObj(T obj) {
		if(builder!=null){
			builder.releaseObj(obj);
		}else{
			throw new Error("do not set factory yet.");
		}
		if(queue.size()<minSize){
			queue.add(obj);
		}
	}
	/**
	 * 设置工厂对象
	 * @param builder
	 */
	public void setBuilder(Factory<T> builder){
		this.builder=builder;
	}
}
