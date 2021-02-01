package rs.ac.uns.ftn.isa.onee2team.isabackend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "warehouses")
public class Warehouse {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@OneToOne(optional = false)
	@JoinColumn(name = "pharmacyId")
	private Pharmacy pharmacy;

	@OneToOne(optional = false)
	@JoinColumn(name = "medicineId")
	private Medicine medicine;

	@Column(name = "amount", nullable = false)
	private Integer amount;

	@Column(name = "reservedAmount", nullable = false)
	private Integer reservedAmount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public Medicine getMedicine() {
		return medicine;
	}

	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getReservedAmount() {
		return reservedAmount;
	}

	public void setReservedAmount(Integer reservedAmount) {
		this.reservedAmount = reservedAmount;
	}
}
