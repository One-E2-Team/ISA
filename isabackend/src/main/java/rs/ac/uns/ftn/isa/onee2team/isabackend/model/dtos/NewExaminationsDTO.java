package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

import java.util.Date;
import java.util.List;

public class NewExaminationsDTO {

	private Long healthWorkerId;
	private Date date;
	private List<NewExaminationDTO> newExaminations;

	public NewExaminationsDTO() {

	}

	public NewExaminationsDTO(Long healthWorkerId, Date date, List<NewExaminationDTO> newExaminations) {
		super();
		this.healthWorkerId = healthWorkerId;
		this.date = date;
		this.newExaminations = newExaminations;
	}

	public Long getHealthWorkerId() {
		return healthWorkerId;
	}

	public void setHealthWorkerId(Long healthWorkerId) {
		this.healthWorkerId = healthWorkerId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<NewExaminationDTO> getNewExaminations() {
		return newExaminations;
	}

	public void setNewExaminations(List<NewExaminationDTO> newExaminations) {
		this.newExaminations = newExaminations;
	}
}
