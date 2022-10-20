package cap.libapp.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cap.libapp.dto.BookDto;
import cap.libapp.dto.BorrowingDto;
import cap.libapp.dto.MembershipDto;
import cap.libapp.services.IBookAuthorService;
import cap.libapp.services.IMembershipService;
import cap.libapp.util.Constants;
import cap.libapp.viewmodels.AdminBookVm;
import cap.libapp.viewmodels.BorrowingVm;
import cap.libapp.viewmodels.LoginVm;
import cap.libapp.viewmodels.MemberDataVm;
import cap.libapp.viewmodels.ProfileVm;
import lombok.extern.slf4j.Slf4j;

@RestController @Slf4j
@CrossOrigin(origins="http://localhost:4200")
public class LibraryController  
{
	
	/* Controller for Library app.
	 * Author - Shrey.
	 */
	
	@Autowired
	IMembershipService membershipService;
	
	@Autowired
	IBookAuthorService bookAuthorService;
	
	@GetMapping("/print")
	public String print()
	{
		return "Trial!!";
	}
	
	@PostMapping("/memberdata") //Rest Endpoint for entering member data.
	public Map<String, String> addMember(@RequestBody @Valid MembershipDto membershipDto)
	{
		log.info("add data endpoint called");
		membershipService.addData(membershipDto);
		Map<String, String> message = new HashMap<>();
		message.put(Constants.MSG, Constants.ADDED);
		return message;
	}
	
	@PutMapping("/memberdata") //Rest Endpoint for updating member data.
	public Map<String, String> updateMember(@RequestBody @Valid MembershipDto membershipDto, @RequestParam int id)
	{
		log.info("update data endpoint called");
		int result = membershipService.updateData(id, membershipDto);
		Map<String, String> message = new HashMap<>();
		if(result==-1) message.put(Constants.MSG, Constants.INF);
		message.put(Constants.MSG, "Profile Updated.");
		return message;
	}
	
	@GetMapping("/memberborrowings") //Rest Endpoint for listing all members.
	public Set<BorrowingVm> listMemberBorrowings(@RequestParam int id) 
	{
		log.info(Constants.MDD);
		return membershipService.getAllBorrowings(id);
	}
	
	@GetMapping("/members")
	public List<MemberDataVm> listAllMembers()
	{
		log.info(Constants.MDD);
		return membershipService.getAllMembers();
	}
	
	@GetMapping("/memberdata") //Rest Endpoint for getting member using id.
	public ProfileVm getMember(@RequestParam int id)
	{
		log.info(Constants.MDD);
		return membershipService.getMemberById(id);
	} 
	
	@DeleteMapping("/memberdata") //Rest Endpoint for deleting member.
	public Map<String, String> deleteMember(@RequestParam int id)
	{
		log.info("member data delete endpoint called");
		membershipService.deleteMembership(id);
		Map<String, String> message = new HashMap<>();
		message.put(Constants.MSG, Constants.DEL);
		return message;
	}
	
	@PostMapping("/bookdata") //Rest Endpoint for entering book data.
	public Map<String,String> addbookData(@RequestBody @Valid BookDto bookDto)
	{
		log.info("add data endpoint called");
		bookAuthorService.addBook(bookDto);
		Map<String, String> message = new HashMap<>();
		message.put(Constants.MSG, Constants.ADDED+"ISBN - "+ bookDto.getIsbn()+".");
		return message;
	}
	
	@GetMapping("/getbyany") //Rest Endpoint for getting all books.
	public List<AdminBookVm> getBooksByAny()
	{
		log.info(Constants.DDB);
		return bookAuthorService.getAllBooks();
	}
	
	@GetMapping("/getbyisbn") //Rest Endpoint for getting book using isbn.
	public List<AdminBookVm> getBookbyIsbn(@RequestParam int isbn)
	{
		log.info(Constants.DDB);
		List<AdminBookVm> bookList = new ArrayList<>();
		bookList.add(bookAuthorService.getBookByISBN(isbn));
		return bookList;
	}
	
	@GetMapping("/getbyauthor") //Rest Endpoint for getting book using author.
	public List<AdminBookVm> getBooksByAuthor(@RequestParam String author)
	{
		log.info(Constants.DDB);
		return bookAuthorService.getBooksByAuthor(author);
	}
	
	@GetMapping("/getbygenre") //Rest Endpoint for getting book using genre.
	public List<AdminBookVm> getBooksByGenre(@RequestParam String genre)
	{
		log.info(Constants.DDB);
		return bookAuthorService.getBooksByGenre(genre);
	}
	
	@GetMapping("/getbyprice") //Rest Endpoint for getting book using price range.
	public List<AdminBookVm> getBooksByPrice(@RequestParam int min, @RequestParam int max)
	{
		log.info(Constants.DDB);
		return bookAuthorService.getBooksByPrice(min, max);
	}
	
	@GetMapping("/getbytitle") //Rest Endpoint for getting book using title.
	public List<AdminBookVm> getBooksByTitle(@RequestParam String key)
	{
		log.info(Constants.DDB);
		return bookAuthorService.searchBooks(key);
	}
	
	
	@DeleteMapping("/bookdata") //Rest Endpoint for deleting book.
	public Map<String, String> deleteBook(@RequestParam int isbn)
	{
		log.info("book data delete endpoint called");
		bookAuthorService.deleteBook(isbn);
		Map<String, String> message = new HashMap<>();
		message.put(Constants.MSG, Constants.DEL);
		return message;
	}
	
	@PostMapping("/borrowbook") //Rest Endpoint for borrowing book.
	public Map<String, String> borrowBook(@RequestParam int membershipId, @RequestParam int isbn)
	{
		log.info("book borrow endpoint called");
		Map<String, String> message = new HashMap<>();
		message.put(Constants.MSG, (membershipService.borrowBook(new BorrowingDto(membershipId, isbn)))?"Success":"Fail");
		return message;
	}
	
	@DeleteMapping("/returnbook") //Rest Endpoint for returning book.
	public Map<String, String> returnBook(@RequestParam int membershipId, @RequestParam int isbn)
	{
		log.info("book return endpoint called"); 
		Map<String, String> message = new HashMap<>();
		message.put(Constants.MSG, (membershipService.returnBook(new BorrowingDto(membershipId, isbn)))?"Success":"Fail");
		return message;
	}
	
	@GetMapping("/getfine") //Rest Endpoint for getting total fine.
	public Map<String, String> totalFine(@RequestParam int membershipId)
	{
		log.info("book fine display endpoint called");
		Map<String, String> message = new HashMap<>();
		message.put(Constants.MSG, Constants.TF + membershipService.totalFine(membershipId));
		return message;
	}
	
	@GetMapping("/login") //Rest Endpoint for logging in.
	public LoginVm login(@RequestParam String username, @RequestParam String password)
	{
		log.info("login endpoint called");
		return membershipService.login(username, password);
	}
}