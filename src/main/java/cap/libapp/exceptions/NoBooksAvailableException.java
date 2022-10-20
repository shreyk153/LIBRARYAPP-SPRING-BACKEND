package cap.libapp.exceptions;

public class NoBooksAvailableException extends RuntimeException
{
	/* NoBooksAvailableException class for Library app.
	 * Author - Shrey.
	 */

	private static final long serialVersionUID = 1L;
	
	public NoBooksAvailableException(String message)
	{
		super(message);
	}
}