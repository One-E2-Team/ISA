package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

public class PharmacistWithFreeAppointmentDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private double rate;
	
	public PharmacistWithFreeAppointmentDTO() {}
	
	public PharmacistWithFreeAppointmentDTO(Long id, String firstName, String lastName, double rate) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.rate = rate;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
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
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
}
