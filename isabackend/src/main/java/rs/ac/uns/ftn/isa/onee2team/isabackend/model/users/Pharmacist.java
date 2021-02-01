package rs.ac.uns.ftn.isa.onee2team.isabackend.model.users;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;

@Entity
@DiscriminatorValue("PHARMACIST")
public class Pharmacist extends HealthWorker {

	@OneToOne(optional = false)
	@JoinColumn(name = "pharmacyId")
	private Pharmacy pharmacy;

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
}
