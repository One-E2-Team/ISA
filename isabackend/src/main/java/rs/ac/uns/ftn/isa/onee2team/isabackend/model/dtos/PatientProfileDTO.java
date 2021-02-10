package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

public class PatientProfileDTO extends UserProfileDTO{

	private Integer points;
	private String category;
	private double discount;
	private Integer penalties;
	
	public PatientProfileDTO() {}
	
	public PatientProfileDTO(Long id, String email, String firstName, String lastName, String address, String city,
			String state, String phone, Integer points, String category, double discount, Integer penalties) {
		super(id, email, firstName, lastName, address, city, state, phone);
		this.points = points;
		this.category = category;
		this.discount = discount;
		this.penalties = penalties;
	}

	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public Integer getPenalties() {
		return penalties;
	}

	public void setPenalties(Integer penalties) {
		this.penalties = penalties;
	}
	
}
