/**
 * 
 */
package javaz.utils.pool;

/**
 * @author Zero
 * @param <T>
 * @mail baozilaji@126.com
 *
 * Aug 7, 2014
 */
public interface Pool<T> {
	/**
	 * 借用对象
	 * @return
	 */
	public T borrowObj();
	/**
	 * 归还对象
	 * @param obj
	 */
	public void returnObj(T obj);
}
