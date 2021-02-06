package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

public class ScheduledExaminationDTO {

	private Long id;
	private String startTime;
	private Long healthWorkerId;
	private String firstName;
	private String lastName;
	private String doctorType;
	private double price;
	
	public ScheduledExaminationDTO() {}

	public ScheduledExaminationDTO(Long id, String startTime, Long healthWorkerId, String firstName,
			String lastName, String doctorType, double price) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.healthWorkerId = healthWorkerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.doctorType = doctorType;
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

	public Long getHealthWorkerId() {
		return healthWorkerId;
	}

	public void setHealthWorkerId(Long healthWorkerId) {
		this.healthWorkerId = healthWorkerId;
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

	public String getDoctorType() {
		return doctorType;
	}

	public void setDoctorType(String doctorType) {
		this.doctorType = doctorType;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
}
