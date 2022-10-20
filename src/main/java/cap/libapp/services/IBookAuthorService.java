package cap.libapp.services;

import java.util.List;

import cap.libapp.dto.BookDto;
import cap.libapp.viewmodels.AdminBookVm;

public interface IBookAuthorService 
{
	/* Book Service interface for Library app.
	 * Author - Shrey.
	 */
	public int addBook(BookDto bookDto);
	/* Method to add book.
	 */
	
	public List<AdminBookVm> getAllBooks();
	/* Method to get all books.
	 */
	
	public AdminBookVm getBookByISBN(int isbn);
	/* Method to get book by isbn.
	 */
	
	public List<AdminBookVm> getBooksByAuthor(String author);
	/* Method to get books by author.
	 */
	
	public List<AdminBookVm> getBooksByGenre(String genre);
	/* Method to get books by genre.
	 */
	
	public List<AdminBookVm> getBooksByPrice(int min, int max);
	/* Method to get books by price.
	 */
	
	public List<AdminBookVm> searchBooks(String key);
	/* Method to search for books.
	 */
	
	public void deleteBook(int isbn);
	/* Method to delete book.
	 */
	
}
