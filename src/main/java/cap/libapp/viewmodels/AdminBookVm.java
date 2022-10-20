package cap.libapp.viewmodels;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminBookVm 
{
	/* Veiw Model for Admin for Book for Library app.
	 * Author - Shrey.
	 */
	
	private int isbn;
	private String title;
	private String genre;
	private double price;
	private int quantityAvailable;
	private String authors;
	@Override
	public String toString() {
		return "Book [isbn=" + isbn + ", title=" + title + ", genre=" + genre + ", price=" + price
				+ ", quantityAvailable=" + quantityAvailable + ", authors=" + authors + "]";
	}
	
	
}
