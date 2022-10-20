package cap.libapp.viewmodels;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginVm
{
	int membershipId;
	boolean admin;
	boolean loginSuccess;
}
