package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

import java.util.List;

public class HealthWorkerDTO {
	
	private Long id;
	private String firstName;
	private String lastName;
	private Double rate;
	private List<String> pharmacyNames;

	public HealthWorkerDTO() {
	}

	public String getFirstName() {
		return firstName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public List<String> getPharmacyNames() {
		return pharmacyNames;
	}

	public void setPharmacyNames(List<String> pharmacyNames) {
		this.pharmacyNames = pharmacyNames;
	}
}
