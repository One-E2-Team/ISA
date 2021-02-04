package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

public class PharmacieWithMedicinesDTO {

	private Long pharmacy_id;
	private String pharmacy_name;
	private Long medicine_id;
	private String medicine_name;
	
	public PharmacieWithMedicinesDTO() {}
	
	public PharmacieWithMedicinesDTO(Long pharmacy_id, String pharmacy_name, Long medicine_id, String medicine_name) {
		super();
		this.pharmacy_id = pharmacy_id;
		this.pharmacy_name = pharmacy_name;
		this.medicine_id = medicine_id;
		this.medicine_name = medicine_name;
	}

	public Long getPharmacy_id() {
		return pharmacy_id;
	}
	public void setPharmacy_id(Long pharmacy_id) {
		this.pharmacy_id = pharmacy_id;
	}
	public String getPharmacy_name() {
		return pharmacy_name;
	}
	public void setPharmacy_name(String pharmacy_name) {
		this.pharmacy_name = pharmacy_name;
	}
	public Long getMedicine_id() {
		return medicine_id;
	}
	public void setMedicine_id(Long medicine_id) {
		this.medicine_id = medicine_id;
	}
	public String getMedicine_name() {
		return medicine_name;
	}
	public void setMedicine_name(String medicine_name) {
		this.medicine_name = medicine_name;
	}
	
	
}
