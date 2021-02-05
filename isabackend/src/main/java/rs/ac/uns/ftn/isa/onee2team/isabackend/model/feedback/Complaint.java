package rs.ac.uns.ftn.isa.onee2team.isabackend.model.feedback;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;

@Entity
@Table(name = "complaints")
public class Complaint {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@ManyToOne(optional = false, cascade = CascadeType.ALL)//, fetch = FetchType.LAZY
	@JoinColumn(name = "patientId")
	private Patient patient;

	@Column(name = "type", nullable = false)
	private ComplaintType type;

	@Column(name = "complaintEntityId", nullable = false)
	private Long complaintEntityId;

	@Column(name = "comment", nullable = false)
	private String comment;
	
	@Column(name = "answer", nullable = true)
	private String answer;
	
	@Column(name = "handled", nullable = false)
	private Boolean handled;

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

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Boolean getHandled() {
		return handled;
	}

	public void setHandled(Boolean handled) {
		this.handled = handled;
	}
}
