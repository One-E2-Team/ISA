package rs.ac.uns.ftn.isa.onee2team.isabackend.auth;

public class ElevatedFirstLogIn extends JwtAuthenticationRequest {
	private String newPassword;

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
