package com.citti.model;

import com.citti.util.Constants.*;
import com.citti.util.LoginInfo;


public class User {
	private final int id;
	private final String firstName;
	private final String lastName;
	private Role role;
	private LoginInfo loginInfo;

	public User(int id, String firstName, String lastName, Role role, LoginInfo loginInfo) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.loginInfo = loginInfo;
	}

	public int getId() { return id; }
	public String getFirstName() { return firstName; }
	public String getLastName() { return lastName; }
	public String getFullName() { return getFirstName() + " " + getLastName(); }
	public Role getRole() { return role; }
	public LoginInfo getLoginInfo() { return loginInfo; }

	public void setRole(Role newRole) { this.role = newRole; }
	public void setLoginInfo(LoginInfo loginInfo) { this.loginInfo = loginInfo; }
}
