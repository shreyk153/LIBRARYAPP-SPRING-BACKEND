package cap.libapp.exceptions;

public class WrongPasswordException extends RuntimeException
{
	/* WrongPasswordException class for Library app.
	 * Author - Shrey.
	 */

	private static final long serialVersionUID = 1L;
	
	public WrongPasswordException(String message)
	{
		super(message);
	}

}
