package rs.ac.uns.ftn.isa.onee2team.isabackend.model.examination;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.Medicine;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.pharmacy.Pharmacy;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.HealthWorker;
import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.Patient;

@Entity
@Table(name = "examinations")
public class Examination {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@OneToOne(optional = false)
	@JoinColumn(name = "healthWokrerId")
	private HealthWorker healthWokrer;

	@ManyToOne(optional = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Patient patient;

	@OneToOne(optional = false)
	@JoinColumn(name = "pharmacyId")
	private Pharmacy pharmacy;

	@Column(name = "date", nullable = false)
	private Date date;

	@Column(name = "date", nullable = false)
	private Date startTime;

	@Column(name = "endTime", nullable = false)
	private Date endTime;

	@Column(name = "price", nullable = false)
	private Double price;

	@Column(name = "information", nullable = false)
	private String information;

	@Column(name = "status", nullable = false)
	private ExaminationStatus status;

	@OneToMany
	@JoinColumn(name = "medicines")
	private Set<Medicine> medicines;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public HealthWorker getHealthWokrer() {
		return healthWokrer;
	}

	public void setHealthWokrer(HealthWorker healthWokrer) {
		this.healthWokrer = healthWokrer;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public ExaminationStatus getStatus() {
		return status;
	}

	public void setStatus(ExaminationStatus status) {
		this.status = status;
	}

	public Set<Medicine> getMedicines() {
		return medicines;
	}

	public void setMedicines(Set<Medicine> medicines) {
		this.medicines = medicines;
	}
}
