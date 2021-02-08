package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

public class PharmacyWithFreeAppointmentDTO {

	private Long id;
	private String name;
	private String address;
	private double rate;
	private double price;
	
	public PharmacyWithFreeAppointmentDTO() {}
	
	public PharmacyWithFreeAppointmentDTO(Long id, String name, String address, double rate, double price) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.rate = rate;
		this.price = price;
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
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}
