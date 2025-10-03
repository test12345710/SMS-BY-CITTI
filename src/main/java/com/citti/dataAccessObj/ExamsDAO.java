package com.citti.dataAccessObj;

import com.citti.model.Exam;
import com.citti.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ExamsDAO {
	private final Map<Student, List<Exam>> exams = new HashMap<>();

	public void addExamToDAO(Student student, Exam exam) {
		student.upcoming_exams.add(exam);
		exams.put(student, student.upcoming_exams);
	}

	public void removeExamFromDAO(Student student, Exam exam) {
		student.upcoming_exams.remove(exam);
		exams.remove(student);
	}

	public List<Exam> getExamsForStudent(Student student) {
		return exams.get(student);
	}
}
