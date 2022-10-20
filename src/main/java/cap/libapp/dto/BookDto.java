package cap.libapp.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDto 
{
	/* BookDto for Library app.
	 * Author - Shrey.
	 */
	
	@Min(value=1000, message = "ISBN is mandatory")
	private int isbn;
	
	@NotBlank(message = "Title is mandatory")
	private String title;
	
	private String genre;
	
	@Min(value=0)
	private double price;
	
	@Min(value=0)
	private int quantityAvailable;
	private String authors;
	
}
