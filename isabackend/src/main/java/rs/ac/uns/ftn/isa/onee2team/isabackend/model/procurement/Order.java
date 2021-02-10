package rs.ac.uns.ftn.isa.onee2team.isabackend.model.procurement;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.MedicineWithQuantity;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "expireDate", nullable = false)
	private Date expireDate;

	@ManyToMany(cascade = CascadeType.ALL)//, fetch = FetchType.LAZY
	@JoinColumn(name = "medicinesWithQuantity")
	private List<MedicineWithQuantity> medicinesWithQuantity;
	
	@Column(name = "creatorId", nullable = false)
	private Long creatorId;
	
	@Column(name = "pharmacyId", nullable = false)
	private Long pharmacyId;
	
	@Column(name = "finished", nullable = true)
	private Boolean finished;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public List<MedicineWithQuantity> getMedicinesWithQuantity() {
		return medicinesWithQuantity;
	}

	public void setMedicinesWithQuantity(List<MedicineWithQuantity> medicinesWithQuantity) {
		this.medicinesWithQuantity = medicinesWithQuantity;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	
	public Long getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(Long pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public Boolean getFinished() {
		return finished;
	}

	public void setFinished(Boolean finished) {
		this.finished = finished;
	}
}
