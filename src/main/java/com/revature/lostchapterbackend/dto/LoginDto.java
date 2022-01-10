package com.revature.lostchapterbackend.dto;

import java.util.Objects;

public class LoginDto {

	private String access; // This will take an email or username
	private String password;

	public LoginDto() {
		super();
	}

	public LoginDto(String access, String password) {
		super();
		this.access = access;
		this.password = password;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(access, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginDto other = (LoginDto) obj;
		return Objects.equals(access, other.access) && Objects.equals(password, other.password);
	}

	@Override
	public String toString() {
		return "LoginDto [access=" + access + ", password=" + password + "]";
	}

}
