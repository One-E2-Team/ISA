package rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "equivalent_medicines")
public class EquivalentMedicine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@Column(name = "primaryMedicineId", nullable = false)
	private Long primaryMedicineId;
	
	@Column(name = "similarMedicineId", nullable = false)
	private Long similarMedicineId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPrimaryMedicineId() {
		return primaryMedicineId;
	}

	public void setPrimaryMedicineId(Long primaryMedicineId) {
		this.primaryMedicineId = primaryMedicineId;
	}

	public Long getSimilarMedicineId() {
		return similarMedicineId;
	}

	public void setSimilarMedicineId(Long similarMedicineId) {
		this.similarMedicineId = similarMedicineId;
	}
}
