package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

public class MedicineDTO {
	
	private Long id;
	private Long code;
	private String name;
	private String contexture;
	private String manufacturer;
	
	public MedicineDTO( ) {}
	
	public MedicineDTO(Long id, Long code, String name, String contexture, String manufacturer) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.contexture = contexture;
		this.manufacturer = manufacturer;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContexture() {
		return contexture;
	}

	public void setContexture(String contexture) {
		this.contexture = contexture;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
}
