package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

import java.util.List;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.medicine.Medicine;

public class NewMedicineDTO {
	private String name;
	private Long code;
	private String medicineType;
	private String medicineForm;
	private String manufacturer;
	private Boolean recipeNeeded;
	private String sideEffects;
	private String contexture;
	private Integer dailyIntake;
	private Integer points;
	private List<Medicine> equivalentMedicines;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getCode() {
		return code;
	}
	public void setCode(Long code) {
		this.code = code;
	}
	public String getMedicineType() {
		return medicineType;
	}
	public void setMedicineType(String medicineType) {
		this.medicineType = medicineType;
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
	public List<Medicine> getEquivalentMedicines() {
		return equivalentMedicines;
	}
	public void setEquivalentMedicines(List<Medicine> equivalentMedicines) {
		this.equivalentMedicines = equivalentMedicines;
	}
}
