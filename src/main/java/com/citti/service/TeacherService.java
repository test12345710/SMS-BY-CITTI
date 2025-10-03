package com.citti.service;

import com.citti.model.*;


public record TeacherService(Teacher teacher) {

	public void assignGrade(Student student, Grade grade) {
		teacher.assignGrade(student, grade);
	}

	public void addAbsence(Student student, Absence absence) {
		teacher.addAbsence(student, absence);
	}

	public void announceExam(Student student, Exam exam) {
		teacher.announceExam(student, exam);
	}
}
