package cap.libapp.exceptions;

public class BorrowingNotFoundException extends RuntimeException
{
	/* BorrowingNotFoundException class for Library app.
	 * Author - Shrey.
	 */

	private static final long serialVersionUID = 1L;
	
	public BorrowingNotFoundException(String message)
	{
		super(message);
	}

}