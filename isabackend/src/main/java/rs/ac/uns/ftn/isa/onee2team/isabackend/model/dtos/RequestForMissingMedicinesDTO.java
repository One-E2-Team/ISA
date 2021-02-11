package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

import java.util.Date;

public class RequestForMissingMedicinesDTO {

	private Long id;
	private Date date;
	private Long medicineId;
	private String medicineName;

	public RequestForMissingMedicinesDTO() {
	}

	public RequestForMissingMedicinesDTO(Long id, Date date, Long medicineId, String medicineName) {
		super();
		this.id = id;
		this.date = date;
		this.medicineId = medicineId;
		this.medicineName = medicineName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getMedicineId() {
		return medicineId;
	}

	public void setMedicineId(Long medicineId) {
		this.medicineId = medicineId;
	}

	public String getMedicineName() {
		return medicineName;
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}
}
