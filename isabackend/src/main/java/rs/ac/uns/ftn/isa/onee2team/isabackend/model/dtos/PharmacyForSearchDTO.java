package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

public class PharmacyForSearchDTO {

	private Long id;
	private String name;
	private String address;
	private String description;
	private double rate;

	public PharmacyForSearchDTO() {
	}

	public PharmacyForSearchDTO(Long id, String name, String address, String description) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
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

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}
}
