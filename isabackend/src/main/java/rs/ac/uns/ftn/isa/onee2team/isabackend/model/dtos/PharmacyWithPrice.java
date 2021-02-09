package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

public class PharmacyWithPrice {
	private Long id;
	private String name;
	private String address;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	private double price;
	
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	public PharmacyWithPrice(Long id, String name, String address, double price) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.price = price;
	}
	public PharmacyWithPrice() {
		super();
	}
	
}
