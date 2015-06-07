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
public class TestMessagePool extends DefaultPool<TestMessage> {
	private static TestMessagePool instance=new TestMessagePool();
	private TestMessagePool(){
	}
	public static TestMessagePool getInstance(){
		return instance;
	}
	/* (non-Javadoc)
	 * @see Java.Pool.DefaultPool#getClassType()
	 */
	@Override
	Class<TestMessage> getClassType() {
		// TODO Auto-generated method stub
		return TestMessage.class;
	}
	public static void main(String[] args) throws Exception{
		System.out.println(TestMessage.class.newInstance());
		System.out.println(TestMessagePool.getInstance().borrowObj());
	}
}
