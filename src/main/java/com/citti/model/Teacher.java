package com.citti.model;

import com.citti.dataAccessObj.AbsencesDAO;
import com.citti.dataAccessObj.ExamsDAO;
import com.citti.dataAccessObj.GradesDAO;
import com.citti.util.Constants.Role;
import com.citti.util.LoginInfo;

import java.util.Objects;


public class Teacher extends User {

	public Teacher(int id, String firstName, String lastName, Role role, LoginInfo loginInfo) {
		super(id, firstName, lastName, role, loginInfo);
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Teacher teacher = (Teacher) o;
		return Objects.equals(getFirstName(), teacher.getFirstName()) &&
				Objects.equals(getLastName(), teacher.getLastName()) &&
				getRole().equals(teacher.getRole());
	}
}
