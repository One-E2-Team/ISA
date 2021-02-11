package rs.ac.uns.ftn.isa.onee2team.isabackend.model.dtos;

import javax.validation.constraints.NotBlank;

public class UserRequestDTO {

	@NotBlank(message = "Email cannot be empty.")
	private String email;

	@NotBlank(message = "Password cannot be empty.")
	private String password;

	@NotBlank(message = "First name cannot be empty.")
	private String firstname;

	@NotBlank(message = "Last name cannot be empty.")
	private String lastname;
	
	@NotBlank(message = "Address cannot be empty.")
	private String address;
	
	@NotBlank(message = "City cannot be empty.")
	private String city;
	
	@NotBlank(message = "State cannot be empty.")
	private String state;
	
	@NotBlank(message = "Phone cannot be empty.")
	private String phone;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
