package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

public class NewMedicineWithQuantityDTO {

	private Long medicineId;
	private Integer quantity;

	public NewMedicineWithQuantityDTO() {
	}

	public Long getMedicineId() {
		return medicineId;
	}

	public void setMedicineId(Long medicineId) {
		this.medicineId = medicineId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
