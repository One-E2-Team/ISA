package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

public class MedStatsDTO {

	private MedicineDTO medicine;
	private Integer quantity;

	public MedStatsDTO() {
	}

	public MedStatsDTO(MedicineDTO medicine, Integer quantity) {
		super();
		this.medicine = medicine;
		this.quantity = quantity;
	}

	public MedicineDTO getMedicine() {
		return medicine;
	}

	public void setMedicine(MedicineDTO medicine) {
		this.medicine = medicine;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
