package rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.MedicineWithQuantity;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;

@Entity
@Table(name = "eRecipes")
public class ERecipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "code", unique = true, nullable = false)
	private String code;

	@ManyToOne(optional = false)
	@JoinColumn(name = "patientId")
	private Patient patient;

	@ManyToOne(optional = true)
	@JoinColumn(name = "pharmacyId")
	private Pharmacy pharmacy;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "medicinesWithQuantity")
	private List<MedicineWithQuantity> medicinesWithQuantity;

	@Column(name = "date", nullable = false)
	private Date date;
	
	@Column(name = "status", nullable = false)
	private ERecipeStatus status;

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

	public List<MedicineWithQuantity> getMedicinesWithQuantity() {
		return medicinesWithQuantity;
	}

	public void setMedicinesWithQuantity(List<MedicineWithQuantity> medicinesWithQuantity) {
		this.medicinesWithQuantity = medicinesWithQuantity;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ERecipeStatus getStatus() {
		return status;
	}

	public void setStatus(ERecipeStatus status) {
		this.status = status;
	}

}
