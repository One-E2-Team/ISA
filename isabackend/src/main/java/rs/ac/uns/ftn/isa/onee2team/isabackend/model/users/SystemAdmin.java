package rs.ac.uns.ftn.isa.onee2team.isabackend.model.users;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SYSTEM_ADMIN")
public class SystemAdmin extends User {
}
