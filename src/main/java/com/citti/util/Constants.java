package com.citti.util;

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
	public static final int INVALID_INPUT = -50;
	public static final LocalDate INVALID_DATE = null;
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
}
