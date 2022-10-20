package cap.libapp.exceptions;

public class BadRangeException  extends RuntimeException
{
	/* BadRangeException class for Library app.
	 * Author - Shrey.
	 */

	private static final long serialVersionUID = 1L;
	
	public BadRangeException(String message)
	{
		super(message);
	}

}
