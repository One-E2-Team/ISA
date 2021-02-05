package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

public class NewElevatedUserRequestDTO extends UserRequestDTO {
	private String userType;

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
}
