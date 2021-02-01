package rs.ac.uns.ftn.isa.onee2team.isabackend.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "workingCalendars")
public class WorkingCalendar {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@OneToOne(optional = false)
	@JoinColumn(name = "healthWokrerId")
	private HealthWorker healthWorker;

	@OneToOne(optional = false)
	@JoinColumn(name = "pharmacyId")
	private Pharmacy pharmacy;

	@Column(name = "startDate", nullable = false)
	private Date startDate;

	@Column(name = "endDate", nullable = false)
	private Date endDate;

	@Column(name = "startHour", nullable = false)
	private Date startHour;

	@Column(name = "endHour", nullable = false)
	private Date endHour;

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

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
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

	public Date getStartHour() {
		return startHour;
	}

	public void setStartHour(Date startHour) {
		this.startHour = startHour;
	}

	public Date getEndHour() {
		return endHour;
	}

	public void setEndHour(Date endHour) {
		this.endHour = endHour;
	}
}
