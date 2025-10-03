package com.citti.dataAccessObj;

import com.citti.model.*;
import com.citti.util.Constants.Role;
import com.citti.util.LoginInfo;
import com.citti.auth.SecurityUtil;
import com.citti.util.UserWrapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.citti.util.Constants.USERS_FILE;


public class UsersDAO {

	private static final UsersDAO instance = new UsersDAO();
	private Map<String, User> users = new HashMap<>();
	private static final String FILE_PATH = USERS_FILE;

	public UsersDAO() {
		loadFromFile();
	}

	public void saveToFile() {
		List<UserWrapper> wrapped = users.values().stream()
				.map(UserWrapper::new)
				.toList();
		try (FileWriter writer = new FileWriter(FILE_PATH)) {
			new GsonBuilder().setPrettyPrinting().create().toJson(wrapped, writer);
		} catch (Exception e) { e.printStackTrace(); }
	}

	public void loadFromFile() {
		Map<String, User> map = new HashMap<>();
		try (FileReader reader = new FileReader(FILE_PATH)) {
			UserWrapper[] wrapped = new Gson().fromJson(reader, UserWrapper[].class);
			if (wrapped != null) {
				for (UserWrapper uw : wrapped) {
					User u = uw.toUser();
					map.put(u.getFullName(), u);
				}
			}
		} catch (Exception e) { e.printStackTrace(); }
		users = map;
	}

	public void addUserToDAO(User user) {
		User userToStore;
		LoginInfo loginInfo = user.getLoginInfo();
		Role role = user.getRole();
		switch (role) {
			case ADMIN -> userToStore = new Admin(user.getId(), user.getFirstName(), user.getLastName(), role, loginInfo);
			case STUDENT -> userToStore = new Student(user.getId(), user.getFirstName(), user.getLastName(), role, loginInfo);
			case TEACHER -> userToStore = new Teacher(user.getId(), user.getFirstName(), user.getLastName(), role, loginInfo);
			case PRINCIPAL -> userToStore = new Principal(user.getId(), user.getFirstName(), user.getLastName(), role, loginInfo);
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
