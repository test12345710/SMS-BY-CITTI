package com.citti.model;

import com.citti.dataAccessObj.AbsencesDAO;
import com.citti.dataAccessObj.GradesDAO;
import com.citti.dataAccessObj.UsersDAO;
import com.citti.util.Constants.Role;
import com.citti.util.LoginInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Principal extends Admin {

	private final UsersDAO usersDAO;

	public Principal(int id, String firstName, String lastName, Role role, LoginInfo loginInfo, GradesDAO gradesDAO, AbsencesDAO absencesDAO, UsersDAO usersDAO) {
		super(id, firstName, lastName, role, loginInfo, gradesDAO, absencesDAO, usersDAO);
		this.usersDAO = usersDAO;
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

	public void announceTo(javax.management.relation.Role role, String message) {

	}

}
