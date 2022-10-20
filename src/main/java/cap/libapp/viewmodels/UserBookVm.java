package cap.libapp.viewmodels;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserBookVm
{
	/* Veiw Model for User for Book for Library app.
	 * Author - Shrey.
	 */
	
	private int isbn;
	private double price;
	private String title;
	@Override
	public String toString() {
		return "UserBookVm [isbn=" + isbn + ", price=" + price + ", title=" + title + "]";
	}
	
	
}
