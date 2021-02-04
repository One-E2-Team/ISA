package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

import java.util.Date;
import java.util.List;

public class NewOrderDTO {

	private List<NewMedicineWithQuantityDTO> newMedicineWithQuantity;
	private Date expireDate;

	public NewOrderDTO() {
	}

	public List<NewMedicineWithQuantityDTO> getNewMedicineWithQuantity() {
		return newMedicineWithQuantity;
	}

	public void setNewMedicineWithQuantity(List<NewMedicineWithQuantityDTO> newMedicineWithQuantity) {
		this.newMedicineWithQuantity = newMedicineWithQuantity;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
}
