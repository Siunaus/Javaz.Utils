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
public class TestMessage {
	int id;
	long timestamp;
	int seq;
	String body;
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new StringBuilder()
		.append("id:").append(id)
		.append(",timestamp:").append(timestamp)
		.append(",seq:").append(seq)
		.append(",body:").append(body)
		.toString();
	}
}
