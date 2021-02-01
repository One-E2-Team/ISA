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
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "expireDate", nullable = false)
	private Date expireDate;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "medicinesWithQuantity")
	@MapKeyJoinColumn(name = "id")
	private Map<Medicine, Integer> medicinesWithQuantity;

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

	public Map<Medicine, Integer> getMedicinesWithQuantity() {
		return medicinesWithQuantity;
	}

	public void setMedicinesWithQuantity(Map<Medicine, Integer> medicinesWithQuantity) {
		this.medicinesWithQuantity = medicinesWithQuantity;
	}
}
