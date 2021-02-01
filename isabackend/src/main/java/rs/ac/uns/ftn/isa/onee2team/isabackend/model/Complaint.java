package rs.ac.uns.ftn.isa.onee2team.isabackend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "complaints")
public class Complaint {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@OneToOne(optional = true)
	@JoinColumn(name = "patientId")
	private Patient patient;

	@Column(name = "type", nullable = false)
	private ComplaintType type;

	@Column(name = "complaintEntityId", nullable = false)
	private Long complaintEntityId;

	@Column(name = "comment", nullable = false)
	private String comment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public ComplaintType getType() {
		return type;
	}

	public void setType(ComplaintType type) {
		this.type = type;
	}

	public Long getComplaintEntityId() {
		return complaintEntityId;
	}

	public void setComplaintEntityId(Long complaintEntityId) {
		this.complaintEntityId = complaintEntityId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
