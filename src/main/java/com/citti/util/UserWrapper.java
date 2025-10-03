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
			case STUDENT -> new Student(id, firstName, lastName, r, li);
			case TEACHER -> new Teacher(id, firstName, lastName, r, li);
			case ADMIN -> new Admin(id, firstName, lastName, r, li);
			case PRINCIPAL -> new Principal(id, firstName, lastName, r, li);
		};
	}
}
