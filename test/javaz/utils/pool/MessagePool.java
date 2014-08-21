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
public class MessagePool extends DefaultPool<Message> {
	private static MessagePool instance=new MessagePool();
	private MessagePool(){
	}
	public static MessagePool getInstance(){
		return instance;
	}
	/* (non-Javadoc)
	 * @see Java.Pool.DefaultPool#getClassType()
	 */
	@Override
	Class<Message> getClassType() {
		// TODO Auto-generated method stub
		return Message.class;
	}
	public static void main(String[] args) throws Exception{
		System.out.println(Message.class.newInstance());
		System.out.println(MessagePool.getInstance().borrowObj());
	}
}
