package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

import java.util.Date;

public class RequestDTO {

	private Long id;
	private Date date;
	
	public RequestDTO() {}
	
	public RequestDTO(Long pharmacy_id, Date date) {
		super();
		this.id = pharmacy_id;
		this.date = date;
	}

	public Long getPharmacy_id() {
		return id;
	}
	public void setPharmacy_id(Long pharmacy_id) {
		this.id = pharmacy_id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
