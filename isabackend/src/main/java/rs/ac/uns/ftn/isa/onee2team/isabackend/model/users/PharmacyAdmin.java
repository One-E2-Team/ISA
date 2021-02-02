package rs.ac.uns.ftn.isa.onee2team.isabackend.model.users;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;

@Entity
@DiscriminatorValue("PHARMACY_ADMIN")
public class PharmacyAdmin extends User {

	@OneToOne(optional = true)
	@JoinColumn(name = "adminsPharmacyId")
	private Pharmacy pharmacy;

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
}
