package cap.libapp.entities;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
@Table(name="borrowings")
public class Borrowing 
{
	/* Borrowing Entity class for Library app.
	 * Author - Shrey.
	 */
	
	@Id
	private int id;
	
	@OneToOne(fetch=FetchType.EAGER)
	private Book book;
	
	@Column(name="date_of_issue")
	private LocalDate dateOfIssue;

	
	public Borrowing(Book book, LocalDate dateOfIssue) {
		super();
		this.book = book;
		this.dateOfIssue = dateOfIssue;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Borrowing other = (Borrowing) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Borrowing [id=" + id + ", bookId=" + book.getIsbn()+ ", bookName=" + book.getTitle() +", dateOfIssue=" + dateOfIssue + "]";
	}	
	
	
	
	
}
