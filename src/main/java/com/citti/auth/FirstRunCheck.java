package com.citti.auth;

import java.io.File;

public class FirstRunCheck {
	private static final String FLAG_FILE = "first_run.flag";

	public static boolean isFirstRun() {
		File file = new File(FLAG_FILE);
		if (file.exists()) {
			return false; // Not first run
		} else {
			try {
				file.createNewFile(); // Create marker
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true; // First run
		}
	}
}