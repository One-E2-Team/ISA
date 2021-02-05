package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

public class SearchedPatientDTO {
	private Long patientId;
	private String firstName;
	private String lastName;
	private String email;
	private String telephone;
	
	public SearchedPatientDTO() {
		super();
	}

	public SearchedPatientDTO(Long patientId,String firstName, String lastName, String email, String telephone) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.telephone = telephone;
		this.patientId = patientId;
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

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	
}
