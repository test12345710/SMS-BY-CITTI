package com.citti.dataAccessObj;

import com.citti.model.Grade;
import com.citti.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GradesDAO {
	private final Map<Student, List<Grade>> grades = new HashMap<>();

	public void addGradeToDAO(Student student, Grade grade) {
		student.addGrade(grade);
		grades.put(student, student.getGrades());
	}

	public void removeGradeFromDAO(Student student, Grade grade) {
		student.removeGrade(grade);
		grades.remove(student);
	}

	public List<Grade> getGradesForStudent(Student student) {
		return grades.get(student);
	}

	public void updateGradeInDAO(Student student, Grade oldGrade, Grade newGrade) {
		student.removeGrade(oldGrade);
		student.addGrade(newGrade);
		grades.put(student, student.getGrades());

	}
}
