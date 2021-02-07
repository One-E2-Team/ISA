package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

import java.util.Date;

public class NewExaminationDTO {
	
	private Date startTime;
	private Date endTime;
	private Double price;
	
	public NewExaminationDTO() {}

	public NewExaminationDTO(Date startTime, Date endTime, Double price) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.price = price;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
