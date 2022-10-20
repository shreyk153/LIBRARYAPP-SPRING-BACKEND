package cap.libapp.mapper;

import org.mapstruct.Mapper;

import cap.libapp.dto.BookDto;
import cap.libapp.dto.MembershipDto;
import cap.libapp.entities.Book;
import cap.libapp.entities.Borrowing;
import cap.libapp.entities.Membership;
import cap.libapp.viewmodels.AdminBookVm;
import cap.libapp.viewmodels.BorrowingVm;
import cap.libapp.viewmodels.UserBookVm;

@Mapper
public interface LibraryMapper 
{
	/* Mapper interface for Library app.
	 * Author - Shrey.
	 */
	
	public Membership toMembershipEntity(MembershipDto membershipDto);
	/* To convert MembershipDto to Membership.
	 */
	
	
	public Book toBookEntity(BookDto bookDto);
	/* To convert BookDto to Book.
	 */
	
	public BookDto toBookDto(Book book);
	/* To convert Book to BookDto.
	 */
	
	public UserBookVm toUserBookVm(Book book);
	/* To convert Book to UserBookVm.
	 */
	
	public AdminBookVm toAdminBookVm(Book book);
	/* To convert Book to AdminBookVm.
	 */
		
	public BorrowingVm toBorrowingVm(Borrowing borrowing);
	/* To convert Borrowing to BorrowingVm.
	 */
	
}
