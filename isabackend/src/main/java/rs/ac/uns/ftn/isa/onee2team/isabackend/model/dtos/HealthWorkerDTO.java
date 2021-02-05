package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

import java.util.List;

public class HealthWorkerDTO {
	
	private CredentialsAndIdDTO dto = new CredentialsAndIdDTO();
	private Double rate;
	private List<String> pharmacyNames;

	public HealthWorkerDTO() {
	}

	public Long getId() {
		return dto.getId();
	}

	public void setId(Long id) {
		this.dto.setId(id);
	}
	
	public String getFirstName() {
		return dto.getFirstName();
	}

	public void setFirstName(String firstName) {
		this.dto.setFirstName(firstName);
	}

	public String getLastName() {
		return dto.getLastName();
	}

	public void setLastName(String lastName) {
		this.dto.setLastName(lastName);
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
