package cap.libapp.exceptions;

public class MemberAlreadyExistsException extends RuntimeException
{
	/* MemberAlreadyExistsException class for Library app.
	 * Author - Shrey.
	 */

	private static final long serialVersionUID = 1L;
	
	public MemberAlreadyExistsException(String message)
	{
		super(message);
	}
}
