package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

import java.util.List;

public class PresentMedicineDTO {
	private Long id;
	private String name;
	private String type;
	private Double rating;
	private Long code;
	private String medicineForm;
	private String manufacturer;
	private Boolean recipeNeeded;
	private String sideEffects;
	private String contexture;
	private Integer dailyIntake;
	private Integer points;
	private List<PharmacyWithPrice> pharmacies;
	
	public List<PharmacyWithPrice> getPharmacies() {
		return pharmacies;
	}
	public void setPharmacies(List<PharmacyWithPrice> pharmacies) {
		this.pharmacies = pharmacies;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCode() {
		return code;
	}
	public void setCode(Long code) {
		this.code = code;
	}
	public String getMedicineForm() {
		return medicineForm;
	}
	public void setMedicineForm(String medicineForm) {
		this.medicineForm = medicineForm;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public Boolean getRecipeNeeded() {
		return recipeNeeded;
	}
	public void setRecipeNeeded(Boolean recipeNeeded) {
		this.recipeNeeded = recipeNeeded;
	}
	public String getSideEffects() {
		return sideEffects;
	}
	public void setSideEffects(String sideEffects) {
		this.sideEffects = sideEffects;
	}
	public String getContexture() {
		return contexture;
	}
	public void setContexture(String contexture) {
		this.contexture = contexture;
	}
	public Integer getDailyIntake() {
		return dailyIntake;
	}
	public void setDailyIntake(Integer dailyIntake) {
		this.dailyIntake = dailyIntake;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
}
