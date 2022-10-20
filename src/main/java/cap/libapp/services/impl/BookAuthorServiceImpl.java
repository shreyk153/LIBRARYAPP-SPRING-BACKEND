package cap.libapp.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cap.libapp.dao.IBookDao;
import cap.libapp.dao.IBorrowingDao;
import cap.libapp.dto.BookDto;
import cap.libapp.entities.Book;
import cap.libapp.exceptions.AlreadyBorrowedException;
import cap.libapp.exceptions.BadRangeException;
import cap.libapp.exceptions.NoBookFoundException;
import cap.libapp.mapper.LibraryMapper;
import cap.libapp.services.IBookAuthorService;
import cap.libapp.util.Constants;
import cap.libapp.viewmodels.AdminBookVm;
import lombok.extern.slf4j.Slf4j;

@Service @Slf4j
@Transactional
public class BookAuthorServiceImpl implements IBookAuthorService
{
	/* Implementation class for Book Service interface for Library app.
	 * Author - Shrey.
	 */
	
	@Autowired
	private IBookDao bookDao;
	
	@Autowired
	private IBorrowingDao borrowingDao;
	
	LibraryMapper libraryMapper = Mappers.getMapper(LibraryMapper.class);
	

	@Override
	public int addBook(BookDto bookDto) 
	{
		log.info("Method to add book");
		try
		{
			Book b = bookDao.save(libraryMapper.toBookEntity(bookDto));
			return b.getIsbn();
		}
		catch(Exception e)
		{
			log.error(Constants.SEO);
			throw e;
		}
		
	}


	@Override
	public List<AdminBookVm> getAllBooks() 
	{
		log.info("Method to get all books");
		try
		{
			List<AdminBookVm> result = new ArrayList<>();
			for(Book b: bookDao.findAll())
				result.add(libraryMapper.toAdminBookVm(b));
			
			if(result.isEmpty()) throw new NoBookFoundException(Constants.NBF);
			
			return result;
		}
		catch(NoBookFoundException nbe)
		{
			log.error(Constants.NBF);
			throw nbe;
		}
		catch(Exception e)
		{
			log.error(Constants.SEO);
			throw e;
		}
		
	}

	@Override
	public AdminBookVm getBookByISBN(int isbn) 
	{
		log.info("Method to get book by isbn");
		try
		{
			if(!bookDao.findById(isbn).isPresent()) throw new NoBookFoundException(Constants.NBF);
			return libraryMapper.toAdminBookVm(bookDao.getById(isbn));
		}
		catch(NoBookFoundException nbe)
		{
			log.error(Constants.NBF);
			throw nbe;
		}
		catch(Exception e)
		{
			log.error(Constants.SEO);
			throw e;
		}
	}


	@Override
	public void deleteBook(int isbn)
	{
		log.info("Method to delete books");
		try
		{
			Optional<Book> oBook = bookDao.findById(isbn);
			if(!oBook.isPresent()) throw new NoBookFoundException(Constants.NBF);
			if(borrowingDao.findByBook(oBook.get())!=null) 
				throw new AlreadyBorrowedException(Constants.RET);
			bookDao.deleteById(isbn);
		}
		catch(NoBookFoundException nbe)
		{
			log.error(Constants.NBF);
			throw nbe;
		}
		catch(AlreadyBorrowedException abe)
		{
			log.error(Constants.NBF);
			throw abe;
		}
		catch(Exception e)
		{
			log.error(Constants.SEO);
			throw e;
		}
		
	}

	@Override
	public List<AdminBookVm> getBooksByAuthor(String author) 
	{
		log.info("Method to get books by author");
		try
		{
			List<AdminBookVm> result = new ArrayList<>();
			for(Book b: bookDao.findByAuthorsContainingIgnoreCase(author))
				result.add(libraryMapper.toAdminBookVm(b));
			
			if(result.isEmpty()) throw new NoBookFoundException(Constants.NBF);
			
			return result;
		}
		catch(NoBookFoundException nbe)
		{
			log.error(Constants.NBF);
			throw nbe;
		}
		catch(Exception e)
		{
			log.error(Constants.SEO);
			throw e;
		}
	}

	@Override
	public List<AdminBookVm> getBooksByGenre(String genre) 
	{
		log.info("Method to get books by genre");
		try
		{
			List<AdminBookVm> result = new ArrayList<>();
			for(Book b: bookDao.findByGenreContainingIgnoreCase(genre))
				result.add(libraryMapper.toAdminBookVm(b));
			
			if(result.isEmpty()) throw new NoBookFoundException(Constants.NBF);
			
			return result;
		}
		catch(NoBookFoundException nbe)
		{
			log.error(Constants.NBF);
			throw nbe;
		}
		catch(Exception e)
		{
			log.error(Constants.SEO);
			throw e;
		}
	}

	@Override
	public List<AdminBookVm> getBooksByPrice(int min, int max) 
	{
		log.info("Method to get books by price");
		try
		{
			if(max<=min&&max!=0) throw new BadRangeException(Constants.BR);
			max = (max==0)? bookDao.getMaxPrice():max;
			
			List<AdminBookVm> result = new ArrayList<>();
			for(Book b: bookDao.getBooksByPrice(min, max))
				result.add(libraryMapper.toAdminBookVm(b));
			
			if(result.isEmpty()) throw new NoBookFoundException(Constants.NBF);
			
			return result;
		}
		catch(BadRangeException bre)
		{
			log.error("Bad range");
			throw bre;
		}
		catch(NoBookFoundException nbe)
		{
			log.error(Constants.NBF);
			throw nbe;
		}
		catch(Exception e)
		{
			log.error(Constants.SEO);
			throw e;
		}
	}

	@Override
	public List<AdminBookVm> searchBooks(String key) 
	{
		log.info("Method to search books");
		try
		{
			List<AdminBookVm> result = new ArrayList<>();
			for(Book b: bookDao.findByTitleContainingIgnoreCase(key))
				result.add(libraryMapper.toAdminBookVm(b));
			
			if(result.isEmpty()) throw new NoBookFoundException(Constants.NBF);
			
			return result;
		}
		catch(NoBookFoundException nbe)
		{
			log.error(Constants.NBF);
			throw nbe;
		}
		catch(Exception e)
		{
			log.error(Constants.SEO);
			throw e;
		}
	}
}
