package rs.ac.uns.ftn.isa.onee2team.isabackend.model.users;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;

@Entity
public class Dermatologist extends HealthWorker {

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "pharmacies", referencedColumnName = "id")
	private Set<Pharmacy> pharmacies;

	public Set<Pharmacy> getPharmacies() {
		return pharmacies;
	}

	public void setPharmacies(Set<Pharmacy> pharmacies) {
		this.pharmacies = pharmacies;
	}
}
