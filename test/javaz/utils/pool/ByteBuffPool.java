/**
 * 
 */
package javaz.utils.pool;

import java.nio.ByteBuffer;

/**
 * @author Zero
 * @mail baozilaji@126.com
 *
 * Aug 7, 2014
 */
public class ByteBuffPool extends AbstractPool<ByteBuffer> {
	private static ByteBuffPool instance=new ByteBuffPool();
	public static ByteBuffPool getInstance(){
		return instance;
	}
	private ByteBuffPool(){
		setBuilder(new Factory<ByteBuffer>() {
			public ByteBuffer buildObj() {
				return ByteBuffer.allocate(1024);
			}
			public void releaseObj(ByteBuffer obj) {
				obj.clear();
			}
		});
	}
	public static void main(String[] args) {
		System.out.println(ByteBuffPool.getInstance().borrowObj());
	}
}
