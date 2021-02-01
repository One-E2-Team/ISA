package rs.ac.uns.ftn.isa.onee2team.isabackend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "medicines")
public class Medicine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "code", nullable = false)
	private Long code;

	@Column(name = "medicineType", nullable = false)
	private String medicineType;

	@Column(name = "medicineForm", nullable = false)
	private String medicineForm;

	@Column(name = "manufacturer", nullable = false)
	private String manufacturer;

	@Column(name = "recipeNeeded", nullable = false)
	private Boolean recipeNeeded;

	@Column(name = "sideEffects", nullable = false)
	private String sideEffects;

	@Column(name = "contexture", nullable = false)
	private String contexture;

	@Column(name = "dailyIntake", nullable = false)
	private Integer dailyIntake;

	@Column(name = "points", nullable = false)
	private Integer points;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
}
