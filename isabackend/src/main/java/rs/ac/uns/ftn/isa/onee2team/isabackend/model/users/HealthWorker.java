package rs.ac.uns.ftn.isa.onee2team.isabackend.model.users;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("HEALTH_WORKER")
public class HealthWorker extends User {

	private static final long serialVersionUID = 1L;

	@Override
	public String getUsername() {
		return this.getEmail();
	}
}
