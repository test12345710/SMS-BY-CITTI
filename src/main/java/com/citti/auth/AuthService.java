package com.citti.auth;

import com.citti.dataAccessObj.UsersDAO;
import com.citti.model.User;
import com.citti.util.Constants.LOGINSTATE;
import com.citti.util.Constants.Role;


public record AuthService(UsersDAO usersDAO) {

	public LOGINSTATE login(String fullName, String entryCode) {

		User user = usersDAO.findUserInDAO(fullName);
		if (user != null && verifyEntryCode(user, entryCode)) {
			return LOGINSTATE.SUCCESS;
		}
		return LOGINSTATE.FAILURE;
	}

	public String register(User user, Role role) {
		String entryCode;
		String salt;
		String hash;

		do {
			entryCode = SecurityUtil.generateEntryCode();
			salt = SecurityUtil.generateSalt();
			hash = SecurityUtil.hash(entryCode + salt);
		} while (usersDAO.isLoginInfoTaken(hash, salt));

		user.getLoginInfo().setSalt(salt);
		user.getLoginInfo().setHash(hash);

		user.assignID(usersDAO.getAllUsers().size() + 1);
		usersDAO.addUserToDAO(user, role, entryCode);

		return entryCode;
	}

	private boolean verifyEntryCode(User user, String inputEntryCode) {
		String salt = user.getLoginInfo().getSalt();
		String storedHash = user.getLoginInfo().getHash();


		return SecurityUtil.verifyHash(inputEntryCode, storedHash, salt);
	}
}
