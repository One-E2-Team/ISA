package rs.ac.uns.ftn.isa.onee2team.isabackend.model.users;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.MedicineWithQuantity;

@Entity
@DiscriminatorValue("DEALER")
public class Dealer extends User {

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "medicinesWithQuantity")
	private List<MedicineWithQuantity> medicinesWithQuantity;

	public List<MedicineWithQuantity> getMedicinesWithQuantity() {
		return medicinesWithQuantity;
	}

	public void setMedicinesWithQuantity(List<MedicineWithQuantity> medicinesWithQuantity) {
		this.medicinesWithQuantity = medicinesWithQuantity;
	}
}
