package cap.libapp.exceptions;

public class AlreadyBorrowedException extends RuntimeException
{
	/* AlreadyBorrowedException class for Library app.
	 * Author - Shrey.
	 */

	private static final long serialVersionUID = 1L;
	
	public AlreadyBorrowedException(String message)
	{
		super(message);
	}

}