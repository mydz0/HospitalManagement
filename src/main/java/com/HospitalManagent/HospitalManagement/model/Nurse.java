package com.HospitalManagent.HospitalManagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Nurse {

	private String user;
	@JsonProperty("psw")
	private String password;
	private String name;
	private String surname;

	public Nurse() {
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
}
