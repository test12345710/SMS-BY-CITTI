package com.citti.dataAccessObj;

import com.citti.model.Absence;
import com.citti.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AbsencesDAO {

	private static final AbsencesDAO instance = new AbsencesDAO();
	private final Map<Student, List<Absence>> absences = new HashMap<>();

	public void addAbsenceToDAO(Student student, Absence absence) {
		student.addAbsence(absence);
		absences.put(student, student.getAbsences());
	}

	public void removeAbsenceFromDAO(Student student, Absence absence) {
		student.removeAbsence(absence);
		absences.remove(student);
	}

	public List<Absence> getAbsencesForStudent(Student student) {
		return absences.get(student);
	}

	public static AbsencesDAO getInstance() {
		return instance;
	}
}
