package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

import java.util.Date;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.calendar.VacationType;

public class VacationRequestWithHealthWorkerDTO extends VacationRequestDTO {

	private Long requestId;
	private String healthWorkerFirstName;
	private String healthWorkerLastName;
	private Boolean isAccepted;

	public VacationRequestWithHealthWorkerDTO() {
	}

	public VacationRequestWithHealthWorkerDTO(Long requestId, Date start, Date end, VacationType type, Long healthWorkerId,
			String healthWorkerFirstName, String healthWorkerLastName, Boolean isAccepted) {
		super(start, end, type, healthWorkerId);
		this.requestId = requestId;
		this.healthWorkerFirstName = healthWorkerFirstName;
		this.healthWorkerLastName = healthWorkerLastName;
		this.isAccepted = isAccepted;
	}

	public Boolean getIsAccepted() {
		return isAccepted;
	}

	public void setIsAccepted(Boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	public String getHealthWorkerFirstName() {
		return healthWorkerFirstName;
	}

	public void setHealthWorkerFirstName(String healthWorkerFirstName) {
		this.healthWorkerFirstName = healthWorkerFirstName;
	}

	public String getHealthWorkerLastName() {
		return healthWorkerLastName;
	}

	public void setHealthWorkerLastName(String healthWorkerLastName) {
		this.healthWorkerLastName = healthWorkerLastName;
	}

	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}
}
