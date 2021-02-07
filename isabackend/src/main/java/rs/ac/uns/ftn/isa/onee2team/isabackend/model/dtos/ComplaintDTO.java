package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

public class ComplaintDTO {
	private Long id;
	private String patientName;
	private String addressedOn;
	private String comment;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getAddressedOn() {
		return addressedOn;
	}
	public void setAddressedOn(String addressedOn) {
		this.addressedOn = addressedOn;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
