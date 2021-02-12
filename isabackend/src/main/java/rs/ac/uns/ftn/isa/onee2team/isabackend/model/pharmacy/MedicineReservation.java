package rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.Medicine;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;

@Entity
@Table(name = "medicineReservations")
public class MedicineReservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@ManyToOne(optional = false, cascade = CascadeType.ALL/*, fetch = FetchType.LAZY*/)
	@JoinColumn(name = "medicineId")
	private Medicine medicine;
	
	@ManyToOne(optional = true, cascade = CascadeType.ALL/*, fetch = FetchType.LAZY*/)
	@JoinColumn(name = "pharmacyId")
	private Pharmacy pharmacy;

	@ManyToOne(cascade = CascadeType.ALL/*, fetch = FetchType.LAZY*/)
	@JoinColumn(name = "patientId")
	private Patient patient;

	@Column(name = "expireDate", nullable = false)
	private Date expireDate;

	@Column(name = "status", nullable = false)
	private MedicineReservationStatus status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Medicine getMedicine() {
		return medicine;
	}

	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public MedicineReservationStatus getStatus() {
		return status;
	}

	public void setStatus(MedicineReservationStatus status) {
		this.status = status;
	}
	
	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
}
