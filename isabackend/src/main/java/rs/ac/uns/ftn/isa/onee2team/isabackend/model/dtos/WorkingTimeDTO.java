package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

import java.util.Date;

public class WorkingTimeDTO {

	private Long dermatologistId;
	private Date date;

	public WorkingTimeDTO() {
	}

	public WorkingTimeDTO(Long dermatologistId, Date date) {
		super();
		this.dermatologistId = dermatologistId;
		this.date = date;
	}

	public Long getDermatologistId() {
		return dermatologistId;
	}

	public void setDermatologistId(Long dermatologistId) {
		this.dermatologistId = dermatologistId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
