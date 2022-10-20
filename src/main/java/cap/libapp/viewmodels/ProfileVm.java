package cap.libapp.viewmodels;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileVm 
{
	String username;
	String password;
	String name;
	long phone;
	String roles;
}
