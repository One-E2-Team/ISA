package rs.ac.uns.ftn.isa.onee2team.isabackend.model.users;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.Medicine;

@Entity
@DiscriminatorValue("DEALER")
public class Dealer extends User {

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "medicinesWithQuantity")
	@MapKeyJoinColumn(name = "id")
	private Map<Medicine, Integer> medicinesWithQuantity;

	public Map<Medicine, Integer> getMedicinesWithQuantity() {
		return medicinesWithQuantity;
	}

	public void setMedicinesWithQuantity(Map<Medicine, Integer> medicinesWithQuantity) {
		this.medicinesWithQuantity = medicinesWithQuantity;
	}
}
