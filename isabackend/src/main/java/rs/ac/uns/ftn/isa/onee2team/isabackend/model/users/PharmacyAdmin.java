package rs.ac.uns.ftn.isa.onee2team.isabackend.model.users;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;

@Entity
@DiscriminatorValue("PHARMACY_ADMIN")
public class PharmacyAdmin extends User {

	private static final long serialVersionUID = 1L;
	@ManyToOne(optional = true)
	@JoinColumn(name = "adminsPharmacyId")
	private Pharmacy pharmacy;

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	@Override
	public String getUsername() {
		return this.getEmail();
	}
}
