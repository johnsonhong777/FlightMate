package com.flightmate.libs;

public enum Role {
	PILOT(1, "PILOT"),
	ADMINISTRATOR(2, "ADMINISTRATOR");
	
	private final int roleId;
	private final String roleKey;
	
	Role(int roleId, String roleKey) {
		this.roleId = roleId;
		this.roleKey = roleKey;
	}
	
	public int toInt() {
		return roleId;
	}
	
	public String toString() {
		return roleKey;
	}
	public int getRoleId() {
	    return roleId;
	}
	
	public static Role fromInt(int roleId) {
		for (Role role : Role.values()) {
			if (role.roleId == roleId) {
				return role;
			}
		}
		throw new IllegalArgumentException("Invalid Role: " + roleId);
	}
	
	public static int toRoleId(String roleKey) {
		for (Role role : Role.values()) {
			if (role.toString().equalsIgnoreCase(roleKey)) {
				return role.toInt();
			}
		}
		throw new IllegalArgumentException("Invalid Role: " + roleKey);
	}
}
