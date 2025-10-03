package com.citti.model;

import java.time.LocalDate;


public class Absence {

	public LocalDate date;
	public Teacher teacher;

	public Absence(LocalDate date, Teacher teacher) {
		this.date = date;
		this.teacher = teacher;
	}
}
