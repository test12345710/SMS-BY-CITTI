package com.citti.model;

import com.citti.dataAccessObj.AbsencesDAO;
import com.citti.dataAccessObj.ExamsDAO;
import com.citti.dataAccessObj.GradesDAO;
import com.citti.util.Constants.Role;
import com.citti.util.LoginInfo;


public class Teacher extends User {

	public Teacher(String firstName, String lastName, Role role, LoginInfo loginInfo) {
		super(firstName, lastName, role, loginInfo);
	}

	public void assignGrade(Student student, Grade grade) {
		GradesDAO.getInstance().addGradeToDAO(student, grade);
	}

	public void announceExam(Student student, Exam exam) {
		ExamsDAO.getInstance().addExamToDAO(student, exam);
	}

	public void cancelExam(Student student, Exam exam) {
		ExamsDAO.getInstance().removeExamFromDAO(student, exam);
	}

	public void addAbsence(Student student, Absence absence) {
		AbsencesDAO.getInstance().addAbsenceToDAO(student, absence);
	}

	public void removeGrade(Student student, Grade grade) {
		GradesDAO.getInstance().removeGradeFromDAO(student, grade);
	}
}
