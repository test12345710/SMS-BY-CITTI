package com.citti.dataAccessObj;

import com.citti.model.*;
import com.citti.util.Constants.Role;
import com.citti.util.LoginInfo;
import com.citti.util.SecurityUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UsersDAO {

	private static final UsersDAO instance = new UsersDAO();
	private final Map<String, User> users = new HashMap<>();

	public void addUserToDAO(User user, Role role, String entryCode) {
		String salt = SecurityUtil.generateSalt();
		String hash = SecurityUtil.hash(entryCode + salt);
		LoginInfo loginInfo = new LoginInfo(hash, salt);

		User userToStore;

		switch (role) {
			case ADMIN -> userToStore = new Admin(user.getFirstName(), user.getLastName(), role, loginInfo);
			case STUDENT -> userToStore = new Student(user.getFirstName(), user.getLastName(), role, loginInfo);
			case TEACHER -> userToStore = new Teacher(user.getFirstName(), user.getLastName(), role, loginInfo);
			case PRINCIPAL -> userToStore = new Principal(user.getFirstName(), user.getLastName(), role, loginInfo);
			default -> throw new IllegalArgumentException("Invalid role: " + role);
		}

		users.put(user.getFullName(), userToStore);
	}

	public void removeUserFromDAO(User user) {
		users.remove(user.getFullName());
	}

	public User findUserInDAO(String fullName) {
		return users.get(fullName);
	}

	public void updateEntryCode(String fullName, String newCode) {
		User user = findUserInDAO(fullName);
		if (user != null) {
			String salt = SecurityUtil.generateSalt();
			String hash = SecurityUtil.hash(newCode + salt);

			while (isLoginInfoTaken(hash, salt)) {
				salt = SecurityUtil.generateSalt();
				hash = SecurityUtil.hash(newCode + salt);
			}

			user.getLoginInfo().setSalt(salt);
			user.getLoginInfo().setHash(hash);
		}
	}

	public boolean isLoginInfoTaken(String hash, String salt) {
		return users.values().stream()
				.anyMatch(user -> user.getLoginInfo().getHash().equals(hash) ||
						user.getLoginInfo().getSalt().equals(salt));
	}

	public List<User> getAllUsers() {
		return new ArrayList<>(users.values());
	}

	public static UsersDAO getInstance() {
		return instance;
	}
}
