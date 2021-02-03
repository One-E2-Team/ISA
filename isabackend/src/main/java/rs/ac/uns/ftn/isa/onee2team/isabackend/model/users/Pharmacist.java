package rs.ac.uns.ftn.isa.onee2team.isabackend.model.users;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;

@Entity
public class Pharmacist extends HealthWorker {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "pharmacistsPharmacyId")
	private Pharmacy pharmacy;

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
}
