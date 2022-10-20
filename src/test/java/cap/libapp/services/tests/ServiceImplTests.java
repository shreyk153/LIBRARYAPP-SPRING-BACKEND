package cap.libapp.services.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cap.libapp.dao.IBookDao;
import cap.libapp.dao.IBorrowingDao;
import cap.libapp.dao.IMembershipDao;
import cap.libapp.dto.BookDto;
import cap.libapp.dto.BorrowingDto;
import cap.libapp.dto.MembershipDto;
import cap.libapp.entities.Book;
import cap.libapp.entities.Borrowing;
import cap.libapp.entities.Membership;
import cap.libapp.exceptions.BadRangeException;
import cap.libapp.exceptions.MemberAlreadyExistsException;
import cap.libapp.exceptions.MemberNotFoundException;
import cap.libapp.exceptions.NoBookFoundException;
import cap.libapp.exceptions.WrongPasswordException;
import cap.libapp.mapper.LibraryMapper;
import cap.libapp.services.impl.BookAuthorServiceImpl;
import cap.libapp.services.impl.MembershipServiceImpl;
import cap.libapp.viewmodels.AdminBookVm;
import cap.libapp.viewmodels.BorrowingVm;
import cap.libapp.viewmodels.LoginVm;
import cap.libapp.viewmodels.ProfileVm;
import cap.libapp.viewmodels.UserBookVm;

@ExtendWith(MockitoExtension.class)
class ServiceImplTests {

	@Mock
	private IMembershipDao membershipDao;
	
	@Mock
	private IBorrowingDao borrowingDao;
	
	@Mock
	private IBookDao bookDao;
	
	@InjectMocks
	private MembershipServiceImpl membershipService;
	
	@InjectMocks
	private BookAuthorServiceImpl bookAuthorService;
	
	LibraryMapper libraryMapper = Mappers.getMapper(LibraryMapper.class);
	
	Membership membership1;
	Membership membership2;
	Membership membership3;
	Membership membership4;
	MembershipDto membershipDto4;
	MembershipDto membershipDto5;
	
	Borrowing borrowing1;
	Borrowing borrowing2;
	BorrowingVm borrowingVm4;
	BorrowingDto borrowDto4;
	BorrowingDto borrowDto41;
	BorrowingDto borrowDto5;
	
	Book book1;
	Book book2;
	Book book3;
	BookDto bookDto3;
	AdminBookVm ab1;
	UserBookVm ub1;
	ProfileVm pvm4;
	
	List<Book> bli;
	List<Membership> mli;
	List<AdminBookVm> abli;
	Set<Borrowing> borrow;
	Set<BorrowingVm> borrowVm;
	
	
	
	List<String> li;
	
	@BeforeEach
	void setUp() throws Exception 
	{
		book1 = new Book(1001, "B1", "G1", 14.0, 7, "A1, A2");
		book2 = new Book(1002, "B2", "G2", 15.0, 5, "A2");
		book3 = new Book(1003, "B3", "G2", 12.0, 9, "A3");
		bookDto3 = new BookDto(1003, "B3", "G2", 12.0, 9, "A3");
		ab1 = new AdminBookVm(1001, "B1", "G1", 14.0, 7, "A1, A2");
		
		bli = new ArrayList<>();
		abli = new ArrayList<>();
		bli.add(book1);
		abli.add(ab1);
		
		
		
		bookDao.save(book1);
		bookDao.save(book2);
		bookDao.save(book3);
		
		membership1 = new Membership("u1", "p1", "ROLE_ADMIN", "n1", 8450289493l);
		membership1.setId(1);
		membershipDao.delete(membership1);
		membership2 = new Membership("u2", "p2", "ROLE_USER", "n2", 8450289213l);
		membership3 = new Membership("u3", "p3", "ROLE_USER", "n3", 8450281221l);
		membership4 = new Membership("u4", "p4", "ROLE_ADMIN", "n4", 8921329109l);
		membership3.setId(3);
		membership4.setId(4);
		
		borrow = new HashSet<>();
		borrowVm = new HashSet<>();
		
		borrowing1 = new Borrowing(4001001, book1, LocalDate.of(2022, 2, 15));
		borrow.add(borrowing1);
		membership4.setBorrowings(borrow);
		mli = new ArrayList<>();
		mli.add(membership4);
		
		ub1 = new UserBookVm(1001, 14.0, "B1");
		borrowingVm4 = new BorrowingVm(4001001, ub1, LocalDate.of(2022, 2, 15));
		borrowVm.add(borrowingVm4);
		pvm4 = new ProfileVm("u4", "p4", "n4", 8921329109l,  "ROLE_ADMIN");
		
		borrowDto4 = new BorrowingDto(4, 1002);
		borrowDto41 = new BorrowingDto(4, 1001);
		borrowDto5 = new BorrowingDto(5, 1002);
		
		li = new ArrayList<>();
		
		li.add("u1");
		li.add("u2");
		li.add("u3");
		
		membershipDao.save(membership1);
		membershipDao.save(membership2);
		membershipDao.save(membership3);
		membershipDao.save(membership4);
		
		membershipDto4 = new MembershipDto("u4", "p4", "ROLE_ADMIN", "n4", 8921329109l);
		membershipDto5 = new MembershipDto("u2", "p5", "ROLE_ADMIN", "n5", 8982182109l);
		
		
		
	}
	
	@Test 
	void testAddMember1()
	{
		when(membershipDao.getAllUsernames()).thenReturn(li);
		assertThrows(MemberAlreadyExistsException.class, ()->membershipService.addData(membershipDto5)); 
	}
	 
	
	@Test 
	void testAddMember2() 
	{
		when(membershipDao.getAllUsernames()).thenReturn(li);
		when(membershipDao.save(libraryMapper.toMembershipEntity(membershipDto4))).thenReturn(membership4);
	 	assertEquals(4, membershipService.addData(membershipDto4)); 
	}
	
	@Test
	void testUpdateMember1()
	{
		assertThrows(MemberNotFoundException.class, ()->membershipService.updateData(5, membershipDto4));
	}
	
	@Test
	void testUpdateMember2()
	{
		when(membershipDao.findById(4)).thenReturn(Optional.of(membership4));
		when(membershipDao.save(membership4)).thenReturn(membership4);
		assertEquals(4, membershipService.updateData(4, membershipDto4));
	}
	
	@Test
	void listMembers1()
	{
		assertThrows(MemberNotFoundException.class, ()->membershipService.getAllBorrowings(6));
	}
	
	@Test
	void listMembers2()
	{
		when(membershipDao.findById(4)).thenReturn(Optional.of(membership4));
		assertEquals(borrowVm, membershipService.getAllBorrowings(4));
	}
	
	@Test
	void getMemberById1()
	{
		assertThrows(MemberNotFoundException.class, ()->membershipService.getMemberById(9));
	}
	
	@Test
	void getMemberById2()
	{
		when(membershipDao.findById(4)).thenReturn(Optional.of(membership4));
		assertEquals(pvm4, membershipService.getMemberById(4));
	}
	
	@Test
	void borrowbook1()
	{
		when(bookDao.findById(1002)).thenReturn(Optional.of(book2));
		when(membershipDao.findById(4)).thenReturn(Optional.of(membership4));
		when(bookDao.getById(1002)).thenReturn(book2);
		when(membershipDao.getById(4)).thenReturn(membership4);
		assertTrue(membershipService.borrowBook(borrowDto4));
	}
	
	@Test
	void borrowbook2()
	{
		when(bookDao.findById(1002)).thenReturn(Optional.of(book2));
		when(bookDao.getById(1002)).thenReturn(book2);
		assertThrows(MemberNotFoundException.class,()-> membershipService.borrowBook(borrowDto5));
	}
	
	@Test
	void returnbook1()
	{
		when(bookDao.findById(1001)).thenReturn(Optional.of(book1));
		when(membershipDao.findById(4)).thenReturn(Optional.of(membership4));
		when(bookDao.getById(1001)).thenReturn(book1);
		when(membershipDao.getById(4)).thenReturn(membership4);
		assertTrue(membershipService.returnBook(borrowDto41));
	}
	
	@Test
	void returnbook2()
	{
		when(bookDao.findById(1002)).thenReturn(Optional.of(book2));
		when(bookDao.getById(1002)).thenReturn(book2);
		assertThrows(MemberNotFoundException.class,()-> membershipService.borrowBook(borrowDto5));
	}
	
	@Test
	void totalFine()
	{
		when(membershipDao.findById(4)).thenReturn(Optional.of(membership4));
		assertEquals(Period.between(LocalDate.of(2022, 2, 15), LocalDate.now()).getDays()*14, membershipService.totalFine(4));
		
	}
	
	@Test
	void addBook1()
	{
		when(bookDao.save(libraryMapper.toBookEntity(bookDto3))).thenReturn(book3);
		assertEquals(1003, bookAuthorService.addBook(bookDto3));
	}
	
	@Test
	void listbooks1()
	{
		when(bookDao.findAll()).thenReturn(bli);
		assertEquals(abli, bookAuthorService.getAllBooks());
	}
	
	@Test
	void listbooks2()
	{
		assertThrows(NoBookFoundException.class, ()->bookAuthorService.getAllBooks());
	}
	
	@Test
	void getBookIsbn1()
	{
		assertThrows(NoBookFoundException.class, ()->bookAuthorService.getBookByISBN(1004));
	}
	
	@Test
	void getBookIsbn2()
	{
		when(bookDao.findById(1001)).thenReturn(Optional.of(book1));
		when(bookDao.getById(1001)).thenReturn(book1);
		assertEquals(ab1, bookAuthorService.getBookByISBN(1001));
	}
	
	@Test
	void getBooksByAuthor1()
	{
		when(bookDao.findByAuthorsContainingIgnoreCase("A1")).thenReturn(bli);
		assertEquals(abli, bookAuthorService.getBooksByAuthor("A1"));
	}
	
	@Test
	void getBooksByAuthor2()
	{
		assertThrows(NoBookFoundException.class, ()->bookAuthorService.getBooksByAuthor("A5"));
	}
	
	@Test
	void getBooksByGenre1()
	{
		when(bookDao.findByGenreContainingIgnoreCase("G1")).thenReturn(bli);
		assertEquals(abli, bookAuthorService.getBooksByGenre("G1"));
	}
	
	@Test
	void getBooksByGenre2()
	{
		assertThrows(NoBookFoundException.class, ()->bookAuthorService.getBooksByGenre("G4"));
	}
	
	@Test
	void getBooksByPrice1()
	{
		assertThrows(BadRangeException.class, ()->bookAuthorService.getBooksByPrice(10, 5));
	}
	
	@Test
	void getBooksByPrice2()
	{
		assertThrows(NoBookFoundException.class, ()->bookAuthorService.getBooksByPrice(5, 10));
	}
	
	@Test
	void getBooksByPrice3()
	{
		when(bookDao.getBooksByPrice(8, 12)).thenReturn(bli);
		assertEquals(abli, bookAuthorService.getBooksByPrice(8, 12));
	}
	
	@Test
	void getBooksByPrice4()
	{
		when(bookDao.getMaxPrice()).thenReturn(15);
		when(bookDao.getBooksByPrice(13, 15)).thenReturn(bli);
		assertEquals(abli, bookAuthorService.getBooksByPrice(13, 0));
	}
	
	@Test
	void searchBooks1()
	{
		when(bookDao.findByTitleContainingIgnoreCase("B1")).thenReturn(bli);
		assertEquals(abli, bookAuthorService.searchBooks("B1"));
	}
	
	@Test
	void searchBooks2()
	{
		assertThrows(NoBookFoundException.class, ()->bookAuthorService.searchBooks("B65"));
	}
	
	@Test
	void deleteBooks()
	{
		assertThrows(NoBookFoundException.class, ()->bookAuthorService.deleteBook(7378));
	}
	
	@Test
	void deleteMember()
	{
		assertThrows(MemberNotFoundException.class, ()->membershipService.deleteMembership(5));
	}
	
	@Test
	void logintest1()
	{
		assertThrows(MemberNotFoundException.class, ()->membershipService.login("u5", "p5"));
	}
	
	@Test
	void logintest2()
	{
		when(membershipDao.getAllUsernames()).thenReturn(li);
		when(membershipDao.findByUsername("u3")).thenReturn(membership3);
		assertThrows(WrongPasswordException.class, ()->membershipService.login("u3", "p5"));
	}
	
	@Test
	void logintest3()
	{
		when(membershipDao.getAllUsernames()).thenReturn(li);
		when(membershipDao.findByUsername("u3")).thenReturn(membership3);
		assertEquals(new LoginVm(3, false, true), membershipService.login("u3", "p3"));
	}
	

}
