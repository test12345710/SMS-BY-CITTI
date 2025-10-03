package com.citti.controller;

import com.citti.dataAccessObj.AbsencesDAO;
import com.citti.dataAccessObj.ExamsDAO;
import com.citti.dataAccessObj.GradesDAO;
import com.citti.dataAccessObj.UsersDAO;
import com.citti.model.*;
import com.citti.service.*;


public record AppController(UsersDAO usersDAO, GradesDAO gradesDAO, AbsencesDAO absencesDAO, ExamsDAO examsDAO, AuthService authService) {

	public void routeUser(User loggedInUser) {

		switch (loggedInUser.getRole()) {
			case STUDENT -> new StudentService((Student) loggedInUser);
			case TEACHER -> new TeacherService((Teacher) loggedInUser);
			case ADMIN -> new AdminService((Admin) loggedInUser);
			case PRINCIPAL -> new PrincipalService((Principal) loggedInUser);
			default -> System.out.println("Unknown role. Cannot route user.");
		}
	}
}