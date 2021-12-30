package com.revature.lostchapterbackend.dto;

import java.util.Objects;

public class SignUpDto {
	
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private int age;
	private String email;
	private String birthday;
	private String address;
	private String role;
	
	public SignUpDto() {
		super();
	}

	public SignUpDto(String username, String password, String firstName, String lastName, int age, String email,
			String birthday, String address, String role) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.email = email;
		this.birthday = birthday;
		this.address = address;
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, age, birthday, email, firstName, lastName, password, role, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SignUpDto other = (SignUpDto) obj;
		return Objects.equals(address, other.address) && age == other.age && Objects.equals(birthday, other.birthday)
				&& Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(password, other.password)
				&& Objects.equals(role, other.role) && Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "SignUpDto [username=" + username + ", password=" + password + ", firstName=" + firstName + ", lastName="
				+ lastName + ", age=" + age + ", email=" + email + ", birthday=" + birthday + ", address=" + address
				+ ", role=" + role + "]";
	}
	
	

}
