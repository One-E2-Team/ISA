package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

import java.util.Date;
import java.util.List;

public class ERecipeDTO {
	private Long patientId;
	private Date date;
	private List<ERecipeMedicine> medicine;
	public Long getPatientId() {
		return patientId;
	}
	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public List<ERecipeMedicine> getMedicine() {
		return medicine;
	}
	public void setMedicine(List<ERecipeMedicine> medicine) {
		this.medicine = medicine;
	}
	
}
