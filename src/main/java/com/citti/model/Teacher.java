package com.citti.model;

import com.citti.util.Constants.Role;
import com.citti.util.LoginInfo;


public class Teacher extends User {

	public Teacher(String firstName, String lastName, Role role, LoginInfo loginInfo) {
		super(firstName, lastName, role, loginInfo);
	}

	public void assignGrade(Student student, Grade grade) {
		student.addGrade(grade);
	}

	public void announceExam(Student student, Exam exam) {
		student.addUpcomingExam(exam);
	}

	public void addAbsence(Student student, Absence absence) {
		student.addAbsence(absence);
	}
}
