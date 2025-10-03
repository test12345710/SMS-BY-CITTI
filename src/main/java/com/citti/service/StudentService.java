package com.citti.service;

import com.citti.model.Absence;
import com.citti.model.Exam;
import com.citti.model.Grade;
import com.citti.model.Student;

import java.util.List;


public record StudentService(Student student) {

	public List<Grade> getGrades() {
		return student.getGrades();
	}

	public List<Absence> getAbsences() {
		return student.getAbsences();
	}

	public List<Exam> getUpcomingExams() {
		return student.getUpcomingExams();
	}

}
