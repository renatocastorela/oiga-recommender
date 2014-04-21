package org.oiga.web.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class SignupForm {
	@NotEmpty
	@Size(max=100)
	private String firstName;
	@NotEmpty
	@Size(max=100)
	private String lastName;
	@Email
	@NotEmpty
	@Size(max=100)
	private String email;
	@Size(min = 6, max = 30)
	private String password;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
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
	
}
