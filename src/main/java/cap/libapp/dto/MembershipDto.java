package cap.libapp.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import cap.libapp.util.ValidPassword;
import cap.libapp.util.ValidRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MembershipDto 
{
	/* MembershipDto for Library app.
	 * Author - Shrey.
	 */
	
	@NotBlank(message = "Username is mandatory")
	private String username;
	
	@NotBlank(message = "Password is mandatory")
	@ValidPassword
	private String password;
	
	@ValidRole
	private String roles;
	
	@NotBlank
	private String name;
	
	@Min(value=6000000000l)
	@Max(value=10000000000l)
	private long phone;
}
