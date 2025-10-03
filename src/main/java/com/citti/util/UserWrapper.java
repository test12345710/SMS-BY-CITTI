package com.citti.util;

import com.citti.model.*;
import com.citti.util.Constants.Role;

public class UserWrapper {
	public int id;
	public String firstName;
	public String lastName;
	public String role;
	public LoginInfo loginInfo;

	public UserWrapper(User user) {
		this.id = user.getId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.role = user.getRole().name();
		this.loginInfo = user.getLoginInfo();
	}

	public User toUser() {
		Role r = Role.valueOf(role);
		LoginInfo li = loginInfo;
		return switch (r) {
			case STUDENT -> new Student(firstName, lastName, r, li);
			case TEACHER -> new Teacher(firstName, lastName, r, li);
			case ADMIN -> new Admin(firstName, lastName, r, li);
			case PRINCIPAL -> new Principal(firstName, lastName, r, li);
		};
	}
}
