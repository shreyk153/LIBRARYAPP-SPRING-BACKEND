package cap.libapp.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @NoArgsConstructor
@ToString @AllArgsConstructor
@Table(name="books")
public class Book 
{
	/* Book Entity class for Library app.
	 * Author - Shrey.
	 */
	
	@Id
	@Column(name="isbn")
	private int isbn;
	
	@Column(name="title")
	private String title;
		
	@Column(name="genre")
	private String genre;
	
	@Column(name="price")
	private double price;
	
	@Column(name="quantity_available")
	private int quantityAvailable;
	
	@Column(name="authors")
	private String authors;

	@Override
	public int hashCode() {
		return Objects.hash(isbn);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return isbn == other.isbn;
	}


	public Book(int isbn) {
		super();
		this.isbn = isbn;
	}	
	
}
