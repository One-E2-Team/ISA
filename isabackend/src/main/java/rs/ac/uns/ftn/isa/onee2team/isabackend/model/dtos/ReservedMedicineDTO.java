package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

import java.util.Date;

public class ReservedMedicineDTO {

	private Long id;
	private String medicineName;
	private String pharmacyName;
	private Date expireDate;
	
	public ReservedMedicineDTO() {}
	
	public ReservedMedicineDTO(Long id, String medicineName, String pharmacyName, Date expireDate) {
		super();
		this.id = id;
		this.medicineName = medicineName;
		this.pharmacyName = pharmacyName;
		this.expireDate = expireDate;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMedicineName() {
		return medicineName;
	}
	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}
	public String getPharmacyName() {
		return pharmacyName;
	}
	public void setPharmacyName(String pharmacyName) {
		this.pharmacyName = pharmacyName;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	
}
