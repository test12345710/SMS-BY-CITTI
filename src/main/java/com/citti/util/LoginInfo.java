package com.citti.util;

public class LoginInfo {
	private String hash;
	private String salt;

	public LoginInfo(String hash, String salt) {
		this.hash = hash;
		this.salt = salt;
	}

	public String getHash() {
		return hash;
	}

	public String getSalt() {
		return salt;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
}
