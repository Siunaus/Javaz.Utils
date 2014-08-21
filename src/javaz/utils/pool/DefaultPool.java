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
public abstract class DefaultPool<T> extends AbstractPool<T> {
	public DefaultPool(){
		/**
		 * 设置工厂对象
		 */
		setBuilder(new Factory<T>() {
			public T buildObj() {
				try {
					return getClassType().newInstance();
				} catch (Exception e) {
					throw new Error("can not build an instance of:"+getClass().getName());
				}
			}
			public void releaseObj(T obj) {
				//do nothing here.
			}
		});
	}
	/**
	 * 获取具体对象类型
	 * @return
	 */
	abstract Class<T> getClassType();
}
