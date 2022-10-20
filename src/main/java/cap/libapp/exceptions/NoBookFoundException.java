package cap.libapp.exceptions;

public class NoBookFoundException extends RuntimeException
{
	/* NoBookFoundException class for Library app.
	 * Author - Shrey.
	 */

	private static final long serialVersionUID = 1L;
	
	public NoBookFoundException(String message)
	{
		super(message);
	}
}