package rs.ac.uns.ftn.isa.onee2team.isabackend.model.users;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.examination.Examination;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.Medicine;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.MedicineReservation;

@Entity
@DiscriminatorValue("PATIENT")
public class Patient extends User {

	@Column(name = "points", nullable = false)
	private Integer points;

	@Column(name = "isActiveAccount", nullable = false)
	private Boolean isActiveAccount;

	@Column(name = "penalties", nullable = false)
	private Integer penalties;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "allergies", referencedColumnName = "id")
	private Set<Medicine> allergies;

	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Examination> examinations;

	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<MedicineReservation> medicineReservations;

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Boolean getIsActiveAccount() {
		return isActiveAccount;
	}

	public void setIsActiveAccount(Boolean isActiveAccount) {
		this.isActiveAccount = isActiveAccount;
	}

	public Integer getPenalties() {
		return penalties;
	}

	public void setPenalties(Integer penalties) {
		this.penalties = penalties;
	}

	public Set<Medicine> getAllergies() {
		return allergies;
	}

	public void setAllergies(Set<Medicine> allergies) {
		this.allergies = allergies;
	}

	public Set<Examination> getExaminations() {
		return examinations;
	}

	public void setExaminations(Set<Examination> examinations) {
		this.examinations = examinations;
	}

	public Set<MedicineReservation> getMedicineReservations() {
		return medicineReservations;
	}

	public void setMedicineReservations(Set<MedicineReservation> medicineReservations) {
		this.medicineReservations = medicineReservations;
	}
}
