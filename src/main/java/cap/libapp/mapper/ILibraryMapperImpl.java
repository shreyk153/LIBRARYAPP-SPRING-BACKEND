package cap.libapp.mapper;

import cap.libapp.dto.BookDto;
import cap.libapp.dto.MembershipDto;
import cap.libapp.entities.Book;
import cap.libapp.entities.Borrowing;
import cap.libapp.entities.Membership;
import cap.libapp.viewmodels.AdminBookVm;
import cap.libapp.viewmodels.BorrowingVm;
import cap.libapp.viewmodels.UserBookVm;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-17T17:49:48+0530",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 16.0.2 (Oracle Corporation)"
)
*/
public class ILibraryMapperImpl{

    
    public Membership toMembershipEntity(MembershipDto membershipDto) {
        if ( membershipDto == null ) {
            return null;
        }

        String username = membershipDto.getUsername();
        String password = membershipDto.getPassword();
        String roles = membershipDto.getRoles();
        String name = membershipDto.getName();
        long phone = membershipDto.getPhone();


        return new Membership( username, password, roles, name, phone );
    }

    
    public Book toBookEntity(BookDto bookDto) {
        if ( bookDto == null ) {
            return null;
        }

        return new Book(bookDto.getIsbn(), bookDto.getTitle(), bookDto.getGenre(),
				 bookDto.getPrice(), bookDto.getQuantityAvailable(), bookDto.getAuthors());
    }

    
    public BookDto toBookDto(Book book) {
        if ( book == null ) {
            return null;
        }

        return new BookDto(book.getIsbn(), book.getTitle(), 
        		book.getGenre(), book.getPrice(), book.getQuantityAvailable(), book.getAuthors());
    }

    
    public UserBookVm toUserBookVm(Book book) {
        if ( book == null ) {
            return null;
        }

        return new UserBookVm(book.getIsbn(), book.getPrice(), book.getTitle());
    }

    
    public AdminBookVm toAdminBookVm(Book book) {
        if ( book == null ) {
            return null;
        }
        return new AdminBookVm(book.getIsbn(), book.getTitle(), 
        		book.getGenre(), book.getPrice(), book.getQuantityAvailable(), book.getAuthors());
    }

    
    public BorrowingVm toBorrowingVm(Borrowing borrowing) {
        if ( borrowing == null ) {
            return null;
        }

        return new BorrowingVm
        		(borrowing.getId(), toUserBookVm(borrowing.getBook()), borrowing.getDateOfIssue());
    }
}

