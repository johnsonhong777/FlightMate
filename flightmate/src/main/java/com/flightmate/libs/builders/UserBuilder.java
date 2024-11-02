package com.flightmate.libs.builders;

import com.flightmate.beans.User;

public class UserBuilder {
	private int userId, roleId;
	private String firstName, lastName, email;
	
	public UserBuilder setUserId(int userId) {
		this.userId = userId;
		return this;
	}
	
	public UserBuilder setEmail(String email) {
		this.email = email;
		return this;
	}
	
	public UserBuilder setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}
	
	public UserBuilder setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}
	
	public UserBuilder setRoleId(int roleId) {
		this.roleId = roleId;
		return this;
	}

	public User create() {
		return new User(userId, email, firstName, lastName, roleId);
	}
}
