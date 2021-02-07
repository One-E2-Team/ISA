package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

import java.util.Date;

public class RequestPharmacistDTO {

	private Long pharmacy_id;
	private Date date;
	
	public RequestPharmacistDTO() {}
	
	public RequestPharmacistDTO(Long pharmacy_id, Date date) {
		super();
		this.pharmacy_id = pharmacy_id;
		this.date = date;
	}

	public Long getPharmacy_id() {
		return pharmacy_id;
	}
	public void setPharmacy_id(Long pharmacy_id) {
		this.pharmacy_id = pharmacy_id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
