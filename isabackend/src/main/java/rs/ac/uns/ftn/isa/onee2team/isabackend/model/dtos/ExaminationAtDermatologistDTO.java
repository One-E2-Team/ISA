package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

public class ExaminationAtDermatologistDTO {

	private Long id;
	private String startTime;
	private Long dermatologistId;
	private String firstName;
	private String lastName;
	private double price;
	
	public ExaminationAtDermatologistDTO() {}
	
	public ExaminationAtDermatologistDTO(Long id, String startTime, Long dermatologistId, String firstName,
			String lastName, double price) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.dermatologistId = dermatologistId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.price = price;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public Long getDermatologistId() {
		return dermatologistId;
	}
	public void setDermatologistId(Long dermatologistId) {
		this.dermatologistId = dermatologistId;
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
}
