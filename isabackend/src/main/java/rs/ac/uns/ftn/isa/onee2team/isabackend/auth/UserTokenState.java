package rs.ac.uns.ftn.isa.onee2team.isabackend.auth;

import rs.ac.uns.ftn.isa.onee2team.isabackend.model.users.UserType;

public class UserTokenState {
	 private String accessToken;
	    private Long expiresIn;
	    private UserType userType;

	    public UserType getUserType() {
			return userType;
		}

		public void setUserType(UserType userType) {
			this.userType = userType;
		}

	    public UserTokenState() {
		}

		public UserTokenState(String accessToken, Long expiresIn, UserType userType) {
			super();
			this.accessToken = accessToken;
			this.expiresIn = expiresIn;
			this.userType = userType;
		}

		public String getAccessToken() {
	        return accessToken;
	    }

	    public void setAccessToken(String accessToken) {
	        this.accessToken = accessToken;
	    }

	    public Long getExpiresIn() {
	        return expiresIn;
	    }

	    public void setExpiresIn(Long expiresIn) {
	        this.expiresIn = expiresIn;
	    }
}
