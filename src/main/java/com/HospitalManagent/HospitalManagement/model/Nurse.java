package com.HospitalManagent.HospitalManagement.model;

public class Nurse {

	private int id;
	private String name;
	private String user;
	private String password;

	public Nurse(int id, String name, String user, String password) {
		this.id = id;
		this.name = name;
		this.user = user;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}
}
