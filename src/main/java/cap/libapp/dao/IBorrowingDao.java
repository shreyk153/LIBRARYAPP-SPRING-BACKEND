package cap.libapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cap.libapp.entities.Book;
import cap.libapp.entities.Borrowing;

public interface IBorrowingDao extends JpaRepository<Borrowing, Integer>{
	
	/* Repository interface for Borrowing table for Library app.
	 * Author - Shrey.
	 */
	
	public Borrowing findByBook(Book b);
}