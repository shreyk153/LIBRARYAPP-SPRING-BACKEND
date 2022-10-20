package cap.libapp.entities;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name="memberships")
public class Membership 
{
	/* Membership Entity class for Library app.
	 * Author - Shrey.
	 */
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(unique=true, name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="roles")
	private String roles;
	
	@Column(name="name")
	private String name;
	
	@Column(name="phone")
	private long phone;
	
	@OneToMany(fetch=FetchType.EAGER)
	Set<Borrowing> borrowings;
	
	
	public Membership(String username, String password, String roles, String name, long phone) {
		super();
		this.username = username;
		this.password = password;
		this.roles = roles;
		this.name = name;
		this.phone = phone;
	}


	@Override
	public int hashCode() {
		return Objects.hash(username);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Membership other = (Membership) obj;
		return Objects.equals(username, other.username);
	}


	@Override
	public String toString() {
		return "Membership [id=" + id + ", name=" + name + ", phoneNumber=" + phone + ", borrowings=" + borrowings
				+ "]";
	}
	
	
	

	
}
