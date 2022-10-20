package cap.libapp.exceptions;

public class MemberNotFoundException extends RuntimeException
{
	/* MemberNotFoundException class for Library app.
	 * Author - Shrey.
	 */

	private static final long serialVersionUID = 1L;
	
	public MemberNotFoundException(String message)
	{
		super(message);
	}

}
