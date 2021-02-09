package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

import java.util.List;

public class PresentMedicineDTO {
	private Long id;
	private String name;
	private String type;
	private Double rating;
	private List<PharmacyWithPrice> pharmacies;
	
	public List<PharmacyWithPrice> getPharmacies() {
		return pharmacies;
	}
	public void setPharmacies(List<PharmacyWithPrice> pharmacies) {
		this.pharmacies = pharmacies;
	}
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
}
