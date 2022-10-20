package cap.libapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cap.libapp.entities.Membership;

@Repository
public interface IMembershipDao extends JpaRepository<Membership, Integer>
{
	/* Repository interface for Membership table for Library app.
	 * Author - Shrey.
	 */
	
	@Query("select m.username from Membership m")
	public List<String> getAllUsernames();
	/* Fetch all usernames.
	 */
	
	public Membership findByUsername(String username);
	/* Fetch member by username.
	 */
}