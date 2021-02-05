package rs.ac.uns.ftn.isa.onee2team.isabackend.model.calendar;

import java.util.Date;

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

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.HealthWorker;

@Entity
@Table(name = "vacationRequests")
public class VacationRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "healthWokrerId")
	private HealthWorker healthWorker;

	@Column(name = "startDate", nullable = false)
	private Date startDate;

	@Column(name = "endDate", nullable = false)
	private Date endDate;

	@Column(name = "accepted", nullable = true)
	private Boolean accepted;

	@Column(name = "type", nullable = false)
	private VacationType type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public HealthWorker getHealthWorker() {
		return healthWorker;
	}

	public void setHealthWorker(HealthWorker healthWorker) {
		this.healthWorker = healthWorker;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Boolean getAccepted() {
		return accepted;
	}

	public void setAccepted(Boolean accepted) {
		this.accepted = accepted;
	}

	public VacationType getType() {
		return type;
	}

	public void setType(VacationType type) {
		this.type = type;
	}
}
