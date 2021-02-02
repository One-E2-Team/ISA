package rs.ac.uns.ftn.isa.onee2team.isabackend.model.users;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;

@Entity
public class Pharmacist extends HealthWorker {

	@OneToOne(optional = true)
	@JoinColumn(name = "pharmacistsPharmacyId")
	private Pharmacy pharmacy;

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
}
