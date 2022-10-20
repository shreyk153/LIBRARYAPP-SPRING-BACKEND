package cap.libapp.viewmodels;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDataVm
{
	int id;
	ProfileVm profile;
	Set<BorrowingVm> borrowings;
}
