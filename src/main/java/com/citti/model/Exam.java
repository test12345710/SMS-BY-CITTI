package com.citti.model;

import java.time.LocalDate;


public class Exam {

	public LocalDate date;
	public String subjectName;
	public Teacher teacher;

	public Exam(LocalDate date, String subjectName, Teacher teacher) {
		this.date = date;
		this.subjectName = subjectName;
		this.teacher = teacher;
	}
}
