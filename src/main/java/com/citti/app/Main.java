package com.citti.app;

import com.citti.controller.AppController;
import com.citti.dataAccessObj.AbsencesDAO;
import com.citti.dataAccessObj.ExamsDAO;
import com.citti.dataAccessObj.GradesDAO;
import com.citti.dataAccessObj.UsersDAO;
import com.citti.model.*;
import com.citti.service.*;
import com.citti.util.Constants.LOGINSTATE;
import com.citti.util.Constants.Role;
import com.citti.util.FirstRunCheck;
import com.citti.util.InputUtil;
import com.citti.util.LoginInfo;


public class Main {
	public static void main(String[] args) {

		// Initialize DAOs
		UsersDAO usersDAO = new UsersDAO();
		GradesDAO gradesDAO = new GradesDAO();
		AbsencesDAO absencesDAO = new AbsencesDAO();
		ExamsDAO examsDAO = new ExamsDAO();

		// Initialize AuthService
		AuthService authService = new AuthService(usersDAO);

		// Initialize Controller (services will be set per-user)
		AppController appController = new AppController(usersDAO, gradesDAO, absencesDAO, examsDAO, authService);

		while (true) {
			AbsencesDAO.getInstance().saveToFile();
			ExamsDAO.getInstance().saveToFile();
			GradesDAO.getInstance().saveToFile();
			UsersDAO.getInstance().saveToFile();
			try {
				System.out.println("=== Student Management System ===");
				if (FirstRunCheck.isFirstRun()) {
					String fullName = InputUtil.readInput("Enter full name: ");
					int roleIndex = InputUtil.readInt("Enter role index (0 if unknown): ", 0,999);

					Role role = switch (roleIndex) {
						case 69 -> Role.ADMIN;
						case 300 -> Role.TEACHER;
						case 150 -> Role.PRINCIPAL;
						default -> Role.STUDENT;
					};

					// first, last, role
					String entryCode = authService.register(new User(fullName.split(" ")[0], fullName.split(" ")[1], role, new LoginInfo("", "")), role);

					System.out.println("Registered successfully! ");
					System.out.println("Entry code: " + entryCode + " DON'T FORGET!");
				} else {
						String fullName = InputUtil.readInput("Enter full name: ");
						String entryCode = InputUtil.readInput("Enter entry code: ");

						if (authService.login(fullName, entryCode) == LOGINSTATE.SUCCESS) {

							User loggedInUser = usersDAO.findUserInDAO(fullName);
							appController.routeUser(loggedInUser);

						} else {
							System.out.println("Invalid credentials. Try again.");
						}
				}

			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
				break;
			}
		}
	}
}
