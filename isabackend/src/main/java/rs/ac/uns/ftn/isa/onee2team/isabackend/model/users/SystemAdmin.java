package rs.ac.uns.ftn.isa.onee2team.isabackend.model.users;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SYSTEM_ADMIN")
public class SystemAdmin extends User {

	private static final long serialVersionUID = 1L;

	@Override
	public String getUsername() {
		return this.getEmail();
	}
}
