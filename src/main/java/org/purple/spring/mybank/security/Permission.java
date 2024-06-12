package org.purple.spring.mybank.security;

public class Permission {
	private String permission = "";

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	@Override
	public String toString() {
		return "Permission [permission=" + permission + "]";
	}
	
	
}
