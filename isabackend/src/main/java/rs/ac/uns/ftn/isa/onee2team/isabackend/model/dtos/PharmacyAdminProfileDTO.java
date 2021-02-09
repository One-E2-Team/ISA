package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

public class PharmacyAdminProfileDTO extends UserProfileDTO {

	private Long pharmacyId;

	public PharmacyAdminProfileDTO() {
	}

	public PharmacyAdminProfileDTO(Long id, String email, String firstName, String lastName, String address,
			String city, String state, String phone, Long pharmacyId) {
		super(id, email, firstName, lastName, address, city, state, phone);
		this.pharmacyId = pharmacyId;
	}

	public PharmacyAdminProfileDTO(Long pharmacyId) {
		super();
		this.pharmacyId = pharmacyId;
	}

	public Long getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(Long pharmacyId) {
		this.pharmacyId = pharmacyId;
	}
}
