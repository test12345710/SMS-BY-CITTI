package com.citti.model;

import com.citti.util.Constants.GRADE_VALUE;
import com.citti.util.Constants.Role;
import com.citti.util.LoginInfo;

import java.util.ArrayList;
import java.util.List;


public class Student extends User {

	private final List<Grade> grades = new ArrayList<>();
	private final List<Absence> absences = new ArrayList<>();
	private final List<Exam> upcoming_exams = new ArrayList<>();

	public Student(String firstName, String lastName, Role role, LoginInfo loginInfo) {
		super(firstName, lastName, role, loginInfo);
	}

	public List<Grade> getGrades() { return grades; }
	public Grade getGrade(GRADE_VALUE g, Teacher t) {
		for (Grade gi : grades) {
			if (gi.grade().equals(g) && gi.teacher() == t) {
				return gi;
			}
		}
		return null;
	}
	public void addGrade(Grade grade) { grades.add(grade); }
	public void removeGrade(Grade grade) { grades.remove(grade); }

	public List<Absence> getAbsences() { return absences; }
	public void addAbsence(Absence absence) { absences.add(absence); }
	public void removeAbsence(Absence absence) { absences.remove(absence); }

	public List<Exam> getUpcomingExams() { return upcoming_exams; }
	public void addUpcomingExam(Exam exam) { upcoming_exams.add(exam); }
	public void removeUpcomingExam(Exam exam) { upcoming_exams.remove(exam); }
}
