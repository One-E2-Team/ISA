package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

import java.util.Date;

public class ExaminationDTO {

	private Long id;
	private Long healthWorkerId;
	private Long pharmacyId;
	private String pharmacyName;
	private Date date;
	private Date startTime;
	private Date endTime;

	public ExaminationDTO() {
	}

	public ExaminationDTO(Long id, Long healthWorkerId, Long pharmacyId, Date date, Date startTime, Date endTime) {
		this.id = id;
		this.healthWorkerId = healthWorkerId;
		this.pharmacyId = pharmacyId;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public ExaminationDTO(Long id, Long healthWorkerId, Long pharmacyId, String pharmacyName, Date date, Date startTime,
			Date endTime) {
		super();
		this.id = id;
		this.healthWorkerId = healthWorkerId;
		this.pharmacyId = pharmacyId;
		this.pharmacyName = pharmacyName;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getHealthWorkerId() {
		return healthWorkerId;
	}

	public void setHealthWorkerId(Long healthWorkerId) {
		this.healthWorkerId = healthWorkerId;
	}

	public Long getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(Long pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public String getPharmacyName() {
		return pharmacyName;
	}

	public void setPharmacyName(String pharmacyName) {
		this.pharmacyName = pharmacyName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
