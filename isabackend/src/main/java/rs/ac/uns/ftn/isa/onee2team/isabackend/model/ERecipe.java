package rs.ac.uns.ftn.isa.onee2team.isabackend.model;

import java.util.Date;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "eRecipes")
public class ERecipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@OneToOne(optional = false)
	@JoinColumn(name = "patientId")
	private Patient patient;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "medicinesWithQuantity")
	@MapKeyJoinColumn(name = "id")
	private Map<Medicine, Integer> medicinesWithQuantity;

	@Column(name = "date", nullable = false)
	private Date date;

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

	public Map<Medicine, Integer> getMedicinesWithQuantity() {
		return medicinesWithQuantity;
	}

	public void setMedicinesWithQuantity(Map<Medicine, Integer> medicinesWithQuantity) {
		this.medicinesWithQuantity = medicinesWithQuantity;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
