package com.citti.model;

import com.citti.util.Constants.GRADE_VALUE;

import java.time.LocalDate;


public class Grade {

	public GRADE_VALUE gradeval;
	public Teacher teacher;
	public String subjectName;
	public LocalDate date;

	public Grade(GRADE_VALUE gradeval, Teacher teacher, String subjectName, LocalDate date) {
		this.gradeval = gradeval;
		this.teacher = teacher;
		this.subjectName = subjectName;
		this.date = date;

	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Grade grade = (Grade) o;
		return gradeval == grade.gradeval &&
				subjectName.equals(grade.subjectName) &&
				teacher.equals(grade.teacher) &&
				date.equals(grade.date);
	}

	@Override
	public int hashCode() {
		int result = gradeval.hashCode();
		result = 31 * result + subjectName.hashCode();
		result = 31 * result + teacher.hashCode();
		result = 31 * result + date.hashCode();
		return result;
	}
}
