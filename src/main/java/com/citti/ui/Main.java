package com.citti.ui;

import com.citti.auth.AuthService;
import com.citti.dataAccessObj.AbsencesDAO;
import com.citti.dataAccessObj.ExamsDAO;
import com.citti.dataAccessObj.GradesDAO;
import com.citti.dataAccessObj.UsersDAO;
import com.citti.model.*;
import com.citti.util.Constants.LOGINSTATE;
import com.citti.util.Constants.Role;
import com.citti.util.InputUtil;
import com.citti.util.LoginInfo;

import static com.citti.util.Constants.*;
import static java.lang.Thread.sleep;

public class Main {

	public static void loading(int seconds, String messageAfter, boolean showMessage) throws InterruptedException {
		long end = System.currentTimeMillis() + seconds * 1000L;
		int dots = 1;

		while (System.currentTimeMillis() < end) {
			System.out.print("\r" + ".".repeat(dots));
			dots = (dots % 5) + 1;
			sleep(500);
		}
		if (showMessage) {
			System.out.println("\r" + messageAfter);
		} else {
			System.out.println();
		}

	}

	public static void main(String[] args) {

		UsersDAO usersDAO = UsersDAO.getInstance();
		GradesDAO gradesDAO = GradesDAO.getInstance();
		AbsencesDAO absencesDAO = AbsencesDAO.getInstance();
		ExamsDAO examsDAO = ExamsDAO.getInstance();

		usersDAO.loadFromFile();
		gradesDAO.loadFromFile();
		absencesDAO.loadFromFile();
		examsDAO.loadFromFile();

		AuthService authService = new AuthService(usersDAO);

		AppController appController = new AppController(usersDAO, gradesDAO, absencesDAO, examsDAO, authService);

		while (true) {
			try {
				System.out.println("=== " + APP_NAME + " ===");
				String fullName = InputUtil.readInput(TYPE_FULL_NAME_PROMPT);

				if (usersDAO.findUserInDAO(fullName) == null) { // new user
					System.out.println("Welcome " + fullName + " to our platform!");

					Role role = Role.STUDENT;
					String entryCode;
					String fullNameClean = "EMPTY";
					if (fullName.contains("_")) {
						fullNameClean = fullName.split("_")[0];
						role = switch (fullName.split("_")[1]) {
							case "ADMIN" -> Role.ADMIN;
							case "TEACHER" -> Role.TEACHER;
							case "PRINCIPAL" -> Role.PRINCIPAL;
							default -> Role.STUDENT;
						};
						entryCode = authService.register(new User(fullNameClean.split(" ")[0], fullNameClean.split(" ")[1], role,
								new LoginInfo("", "")));
					} else {
						entryCode = authService.register(new User(fullName.split(" ")[0], fullName.split(" ")[1], role,
								new LoginInfo("", "")));
					}

					sleep(500);
					loading(5, "You have been registered successfully!", false);
					sleep(1800);
					System.out.println("\nYour assigned entry code is:\n " + entryCode + " \nKeep it somewhere safe and do not forget it!!!\n");
					UsersDAO.getInstance().saveToFile();
					System.out.println("Press any key to continue...");
					System.in.read();

					User loggedInUser = usersDAO.findUserInDAO(fullNameClean);
					appController.routeUser(loggedInUser);
				} else { // existing user
						String entryCode = InputUtil.readInput(TYPE_ENTRY_CODE_PROMPT);

						boolean success = authService.login(fullName, entryCode) == LOGINSTATE.SUCCESS;

					sleep(500);
					if (success) {
						loading(2, "", false);
					} else {
						loading(2, INVALID_CREDENTIALS, true);
						continue;
					}
					sleep(250);


					User loggedInUser = usersDAO.findUserInDAO(fullName);
					appController.routeUser(loggedInUser);
				}

			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
				break;
			}
		}
	}
}
