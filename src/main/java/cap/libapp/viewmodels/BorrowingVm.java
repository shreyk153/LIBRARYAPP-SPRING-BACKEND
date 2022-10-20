package cap.libapp.viewmodels;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BorrowingVm 
{
	/* Veiw Model for Borrowing for Library app.
	 * Author - Shrey.
	 */
	
	int id;
	UserBookVm book;
	LocalDate dateOfIssue;
	
	@Override
	public String toString() {
		return "Borrowing [id=" + id + ", book=" + book + ", dateOfIssue=" + dateOfIssue.toString() + "]";
	}
}
