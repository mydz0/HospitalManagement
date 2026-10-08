package com.HospitalManagent.HospitalManagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Nurse {

	private String user;
	@JsonProperty(value = "psw", access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	private String name;

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
}
