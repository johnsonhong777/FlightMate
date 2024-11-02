package com.flightmate.beans;

import com.flightmate.libs.Role;

public class User {
	private final int userId;
	private String firstName, lastName, email;
	private int roleId;
	
	
	// Constructor
	
	public User(int userId, String email, String firstName, String lastName, int roleId) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.roleId = roleId;
	}
	
	// Getter Methods
	
	public int getUserId() {return userId;}
	public String getFirstName() {return firstName;}
	public String getLastName() {return lastName;}
	public String getEmail() {return email;}
	public Role getRole() {return Role.fromInt(roleId);}
	
	// Setter Methods
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
