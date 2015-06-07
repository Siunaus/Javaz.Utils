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
public class TestByteBuffPool extends AbstractPool<ByteBuffer> {
	private static TestByteBuffPool instance=new TestByteBuffPool();
	public static TestByteBuffPool getInstance(){
		return instance;
	}
	private TestByteBuffPool(){
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
		System.out.println(TestByteBuffPool.getInstance().borrowObj());
	}
}
