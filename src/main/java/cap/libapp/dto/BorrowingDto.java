package cap.libapp.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BorrowingDto 
{
	/* BorrowingDto for Library app.
	 * Author - Shrey.
	 */
	
	@NotBlank(message = "MembershipId is mandatory")
	int membershipId;
	
	@NotBlank(message = "ISBN is mandatory")
	int isbn;
}
