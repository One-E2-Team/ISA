package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

public class PharmacyWithPriceAndGradeDTO extends PharmacyWithPrice {

	private Double grade;

	public Double getGrade() {
		return grade;
	}

	public void setGrade(Double grade) {
		this.grade = grade;
	}

	public PharmacyWithPriceAndGradeDTO(Long id, String name, String address, double price, Double grade) {
		super(id, name, address, price);
		this.grade = grade;
	}

	public PharmacyWithPriceAndGradeDTO() {
		super();
	}
	
}
