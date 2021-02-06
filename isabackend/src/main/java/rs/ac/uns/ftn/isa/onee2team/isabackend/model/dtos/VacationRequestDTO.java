package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

import java.util.Date;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.calendar.VacationType;

public class VacationRequestDTO {
	private Date start;
	private Date end;
	private VacationType type;
	private Long healthWorkerId;

	public VacationRequestDTO() {
	}

	public VacationRequestDTO(Date start, Date end, VacationType type, Long healthWorkerId) {
		this.start = start;
		this.end = end;
		this.type = type;
		this.healthWorkerId = healthWorkerId;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public VacationType getType() {
		return type;
	}

	public void setType(VacationType type) {
		this.type = type;
	}

	public Long getHealthWorkerId() {
		return healthWorkerId;
	}

	public void setHealthWorkerId(Long healthWorkerId) {
		this.healthWorkerId = healthWorkerId;
	}
}
