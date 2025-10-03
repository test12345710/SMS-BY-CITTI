package com.citti.app;

import com.citti.controller.AppController;
import com.citti.dataAccessObj.AbsencesDAO;
import com.citti.dataAccessObj.ExamsDAO;
import com.citti.dataAccessObj.GradesDAO;
import com.citti.dataAccessObj.UsersDAO;
import com.citti.model.*;
import com.citti.service.*;
import com.citti.util.Constants.LOGINSTATE;
import com.citti.util.InputUtil;

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

		System.out.println("=== Student Management System ===");

		while (true) {
			try {
				String fullName = InputUtil.readInput("Enter full name: ");
				String entryCode = InputUtil.readInput("Enter entry code: ");

				if (authService.login(fullName, entryCode) == LOGINSTATE.SUCCESS) {
					User loggedInUser = usersDAO.findUserInDAO(fullName);
					appController.routeUser(loggedInUser);
				} else {
					System.out.println("Invalid credentials. Try again.");
				}

			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
				break;
			}
		}
	}
}
