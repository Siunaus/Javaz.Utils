/**
 * 
 */
package javaz.utils.pool;

/**
 * @author Zero
 * @mail baozilaji@126.com
 *
 * Aug 7, 2014
 */
public interface Factory<T> {
	/**
	 * 生成实例对象
	 * @return
	 */
	public T buildObj();
	/**
	 * 释放对象资源
	 * @param obj
	 */
	public void releaseObj(T obj);
}
