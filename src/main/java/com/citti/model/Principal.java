package com.citti.model;

import com.citti.dataAccessObj.UsersDAO;
import com.citti.util.Constants.Role;
import com.citti.util.LoginInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Principal extends Admin {

	private final UsersDAO usersDAO = UsersDAO.getInstance();

	public Principal(String firstName, String lastName, Role role, LoginInfo loginInfo) {
		super(firstName, lastName, role, loginInfo);
	}

	public Map<Student, List<Grade>> getAllGrades() {
		Map<Student, List<Grade>> studentGrades = new HashMap<>();
		for (User user : usersDAO.getAllUsers()) {
			if (user instanceof Student student) {
				studentGrades.put(student, student.getGrades());
			}
		}
		return studentGrades;
	}

	public Map<Student, List<Absence>> getAllAbsences() {
		Map<Student, List<Absence>> studentAbsences = new HashMap<>();
		for (User user : usersDAO.getAllUsers()) {
			if (user instanceof Student student) {
				studentAbsences.put(student, student.getAbsences());
			}
		}
		return studentAbsences;
	}

	public Map<Student, List<Exam>> getAllUpcomingExams() {
		Map<Student, List<Exam>> studentExams = new HashMap<>();
		for (User user : usersDAO.getAllUsers()) {
			if (user instanceof Student student) {
				studentExams.put(student, student.getUpcomingExams());
			}
		}
		return studentExams;
	}

	public void assignTimeTable() {

	}

	public void announceTo(Role role, String message) {

	}

}
