package com.citti.service;

import com.citti.model.*;

import javax.management.relation.Role;
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
}
