package cap.libapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cap.libapp.entities.Book;

@Repository
public interface IBookDao extends JpaRepository<Book, Integer>
{
	/* Repository interface for Book table for Library app.
	 * Author - Shrey.
	 */
	
	@Query("select b.isbn from Book b")
	public List<Integer> getAllISBN();
	
	/* Fetch all isbns.
	 */
	
	public List<Book> findByAuthorsContainingIgnoreCase(String author);
	/* Fetch all books by author.
	 */
	
	public List<Book> findByTitleContainingIgnoreCase(String title);
	/* Fetch all books by title.
	 */
	
	public List<Book> findByGenreContainingIgnoreCase(String genre);
	/* Fetch all books by genre.
	 */
	
	@Query("select b from Book b where b.price between ?1 and ?2")
	public List<Book> getBooksByPrice(double min, double max);
	/* Fetch all books by price.
	 */	
	
	@Query("select max(b.price) from Book b")
	public int getMaxPrice();
	/* Fetch max price of book.
	 */
	
	
}