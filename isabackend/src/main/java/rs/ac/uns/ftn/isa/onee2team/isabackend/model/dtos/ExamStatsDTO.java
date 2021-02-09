package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

import java.util.Date;

public class ExamStatsDTO {

	private Date date;
	private Integer number;

	public ExamStatsDTO() {
	}

	public ExamStatsDTO(Date date, Integer number) {
		super();
		this.date = date;
		this.number = number;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
}
