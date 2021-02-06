package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.promotions.CategoryType;

public class LoyaltyDTO {

	private CategoryType type;
	private Integer minPoints;
	private Double discount;
	private Integer examinationPoints;
	
	public CategoryType getType() {
		return type;
	}
	public void setType(CategoryType type) {
		this.type = type;
	}
	public Integer getMinPoints() {
		return minPoints;
	}
	public void setMinPoints(Integer minPoints) {
		this.minPoints = minPoints;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Integer getExaminationPoints() {
		return examinationPoints;
	}
	public void setExaminationPoints(Integer examinationPoints) {
		this.examinationPoints = examinationPoints;
	}
}
