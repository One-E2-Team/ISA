package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

import java.util.List;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.Medicine;

public class PharmacyWithDoctorsMedicinesAndRateDTO {

	private String name;
	private String address;
	private String description;
	private List<DermatologistWithFreeExaminationsDTO> pharmacists;
	private List<DermatologistWithFreeExaminationsDTO> dermatologists;
	private List<Medicine> medicines;
	private Double rate;

	public PharmacyWithDoctorsMedicinesAndRateDTO() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<DermatologistWithFreeExaminationsDTO> getPharmacists() {
		return pharmacists;
	}

	public void setPharmacists(List<DermatologistWithFreeExaminationsDTO> pharmacists) {
		this.pharmacists = pharmacists;
	}

	public List<DermatologistWithFreeExaminationsDTO> getDermatologists() {
		return dermatologists;
	}

	public void setDermatologists(List<DermatologistWithFreeExaminationsDTO> dermatologists) {
		this.dermatologists = dermatologists;
	}

	public List<Medicine> getMedicines() {
		return medicines;
	}

	public void setMedicines(List<Medicine> medicines) {
		this.medicines = medicines;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
