package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

import java.util.Date;

public class RequestReservationDTO {

	private Long pharmacy_id;
	private Long medicine_id;
	private Date expireDate;
	
	public RequestReservationDTO() {}
	
	public RequestReservationDTO(Long pharmacy_id, Long medicine_id, Date expireDate) {
		super();
		this.pharmacy_id = pharmacy_id;
		this.medicine_id = medicine_id;
		this.expireDate = expireDate;
	}
	public Long getPharmacy_id() {
		return pharmacy_id;
	}
	public void setPharmacy_id(Long pharmacy_id) {
		this.pharmacy_id = pharmacy_id;
	}
	public Long getMedicine_id() {
		return medicine_id;
	}
	public void setMedicine_id(Long medicine_id) {
		this.medicine_id = medicine_id;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	
}
