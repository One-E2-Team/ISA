package rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Dermatologist;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;

@Entity
@Table(name = "pharmacies")
public class Pharmacy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "description", nullable = false)
	private String description;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pharmacies")
	private Set<Dermatologist> dermatologists;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "subscribedPharmacies")
	private Set<Patient> subscribedPatients;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Dermatologist> getDermatologists() {
		return dermatologists;
	}

	public void setDermatologists(Set<Dermatologist> dermatologists) {
		this.dermatologists = dermatologists;
	}

	public Set<Patient> getSubscribedPatients() {
		return subscribedPatients;
	}

	public void setSubscribedPatients(Set<Patient> subscribedPatients) {
		this.subscribedPatients = subscribedPatients;
	}
}
