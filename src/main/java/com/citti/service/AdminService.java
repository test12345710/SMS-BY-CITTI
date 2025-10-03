package com.citti.service;

import com.citti.model.*;


public record AdminService(Admin admin) {


	public void expelStudent(Student student) {
		admin.expelStudent(student);
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

	public String changeStudentEntryCode(Student student) {
		return admin.changeEntryCode(student);
	}
}
