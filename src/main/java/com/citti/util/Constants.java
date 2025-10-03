package com.citti.util;

import com.citti.model.Grade;
import com.citti.model.Teacher;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Constants {

	public enum LOGINSTATE {
		SUCCESS,
		FAILURE
	}

	public enum GRADE_VALUE {
		A,
		B,
		C,
		D,
		F
	}

	public enum Role {
		ADMIN,
		TEACHER,
		STUDENT,
		PRINCIPAL
	}

	public enum SERVICE {
		AdminService,
		TeacherService,
		StudentService,
		PrincipalService
	}

	public static final int INVALID_INPUT = -50;
	public static final LocalDate INVALID_DATE = null;
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

	public static Grade toGrade(String grade, Teacher teacher, String subjectName, LocalDate date) {
		return switch (grade) {
			case "A" -> new Grade(GRADE_VALUE.A, teacher, subjectName, date);
			case "B" -> new Grade(GRADE_VALUE.B, teacher, subjectName, date);
			case "C" -> new Grade(GRADE_VALUE.C, teacher, subjectName, date);
			case "D" -> new Grade(GRADE_VALUE.D, teacher, subjectName, date);
			case "F" -> new Grade(GRADE_VALUE.F, teacher, subjectName, date);
			default -> throw new IllegalArgumentException("Invalid grade: " + grade);
		};
	}
}
