package com.citti.service;

import com.citti.model.*;


public record AdminService(Admin admin) {

	public void enrollStudent(Student student) {
		admin.enrollStudent(student);
	}

	public void expelStudent(Student student) {
		admin.expelStudent(student);
	}

	public void hireTeacher(Teacher teacher) {
		admin.hireTeacher(teacher);
	}

	public void fireTeacher(Teacher teacher) {
		admin.fireTeacher(teacher);
	}

	public void changeStudentGrade(Student student, Grade currentGrade, Grade newGrade) {
		admin.modifyGrade(student, currentGrade, newGrade);
	}

	public void revokeStudentAbsence(Student student, Absence absence) {
		admin.removeAbsence(student, absence);
	}

	public void changeStudentEntryCode(Student student) {
		admin.changeEntryCode(student);
	}
}
