package com.citti.util;

import com.citti.model.*;
import com.citti.util.Constants.Role;

public class UserWrapper {
	public int id;
	public String firstName;
	public String lastName;
	public String role;
	public LoginInfo loginInfo;

	public String type;

	public UserWrapper() {}  // needed for JSON deserialization

	public UserWrapper(User user) {
		this.id = user.getId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.role = user.getRole().name();
		this.loginInfo = user.getLoginInfo();

		switch (user) {
			case Student student -> type = "student";
			case Teacher teacher -> type = "teacher";
			case Principal principal -> type = "principal";
			case Admin admin -> type = "admin";
			default -> type = "user";
		}
	}

	public User toUser() {
		Role r = Role.valueOf(role);
		LoginInfo li = loginInfo;
		return switch (type) {
			case "student" -> new Student(firstName, lastName, r, li);
			case "teacher" -> new Teacher(firstName, lastName, r, li);
			case "admin" -> new Admin(firstName, lastName, r, li);
			case "principal" -> new Principal(firstName, lastName, r, li);
			default -> new User(firstName, lastName, r, li);
		};
	}
}
