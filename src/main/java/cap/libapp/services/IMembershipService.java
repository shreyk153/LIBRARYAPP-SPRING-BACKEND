package cap.libapp.services;

import java.util.List;
import java.util.Set;

import cap.libapp.dto.BorrowingDto;
import cap.libapp.dto.MembershipDto;
import cap.libapp.viewmodels.BorrowingVm;
import cap.libapp.viewmodels.LoginVm;
import cap.libapp.viewmodels.MemberDataVm;
import cap.libapp.viewmodels.ProfileVm;

public interface IMembershipService 
{
	/* Membership Service interface for Library app.
	 * Author - Shrey.
	 */
	public int addData(MembershipDto md);
	/* Method to add member data.
	 */
	
	public int updateData(int id, MembershipDto md);
	/* Method to update member data.
	 */
	
	public Set<BorrowingVm> getAllBorrowings(int id);
	/* Method to list all borrowings for a member.
	 */
	
	public ProfileVm getMemberById(int id);
	/* Method to get member data using id.
	 */
	
	public List<MemberDataVm> getAllMembers();
	/* Method to get member data using id.
	 */
	
	public void deleteMembership(int id);
	/* Method to delete member data using id.
	 */
	
	public boolean borrowBook(BorrowingDto borrowingdto);
	/* Method to borrow a book.
	 */
	
	public boolean returnBook(BorrowingDto borrowingdto);
	/* Method to return a book.
	 */
	
	public double totalFine(int membershipId);
	/* Method calculate total fine for a member.
	 */
	
	public LoginVm login(String username, String password);
	/* Method to login a member.
	 */
	
}
