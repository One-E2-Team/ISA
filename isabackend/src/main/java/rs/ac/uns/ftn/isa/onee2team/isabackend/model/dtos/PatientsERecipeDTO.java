package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

import java.util.Date;

public class PatientsERecipeDTO {
	
	private Long id;
	private String code;
	private String pharmacyName;
	private Date date;
	private String status;
	
	public PatientsERecipeDTO() {}
	
	public PatientsERecipeDTO(Long id, String code, String pharmacyName, Date date, String status) {
		super();
		this.id = id;
		this.code = code;
		this.pharmacyName = pharmacyName;
		this.date = date;
		this.status = status;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPharmacyName() {
		return pharmacyName;
	}
	public void setPharmacyName(String pharmacyName) {
		this.pharmacyName = pharmacyName;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
