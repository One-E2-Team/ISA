package rs.ac.uns.ftn.isa.onee2team.isabackend.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("PHARMACY_ADMIN")
public class PharmacyAdmin extends User {

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
