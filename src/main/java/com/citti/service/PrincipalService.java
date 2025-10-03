package com.citti.service;

import com.citti.model.*;

import com.citti.util.Constants.Role;
import java.util.List;
import java.util.Map;


public record PrincipalService(Principal principal) {

	public Map<Student, List<Grade>> getAllGrades() {
		return principal.getAllGrades();
	}

	public Map<Student, List<Absence>> getAllAbsences() {
		return principal.getAllAbsences();
	}

	public Map<Student, List<Exam>> getAllUpcomingExams() {
		return principal.getAllUpcomingExams();
	}

	public void assignTimeTable() {
		principal.assignTimeTable();
	}

	public void announceTo(Role role, String message) {
		principal.announceTo(role, message);
	}

	public void expelStudent(Student student) {
		principal.expelStudent(student);
	}

	public void fireTeacher(Teacher teacher) {
		principal.fireTeacher(teacher);
	}

	public void changeStudentGrade(Student student, Grade currentGrade, Grade newGrade) {
		principal.modifyGrade(student, currentGrade, newGrade);
	}

	public void revokeStudentAbsence(Student student, Absence absence) {
		principal.removeAbsence(student, absence);
	}

	public String changeStudentEntryCode(Student student) {
		return principal.changeEntryCode(student);
	}
}
