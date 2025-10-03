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

	public enum ACTION {
		ASSIGN_GRADE,
		ADD_ABSENCE,
		ANNOUNCE_EXAM,
		REMOVE_GRADE,
		CANCEL_EXAM,
		FIRE_TEACHER,
		EXPEL_STUDENT,
		MODIFY_GRADE,
		REVOKE_ABSENCE,
		CHANGE_ENTRY_CODE,
		MAKE_ANNOUNCEMENT
	}

	public static final String APP_NAME = "Student Management System";

	public static final String DATA_FOLDER = "data/";
	public static final String USERS_FILE = DATA_FOLDER + "users.json";
	public static final String GRADES_FILE = DATA_FOLDER + "grades.json";
	public static final String ABSENCES_FILE = DATA_FOLDER + "absences.json";
	public static final String EXAMS_FILE = DATA_FOLDER + "exams.json";


	public static final String TYPE_STUDENT_NAME_PROMPT = "Enter student name: ";
	public static final String TYPE_TEACHER_NAME_PROMPT = "Enter teacher name: ";
	public static final String TYPE_SUBJECT_NAME_PROMPT = "Enter subject name: ";



	public static final String TYPE_GRADE_PROMPT = "Enter grade: ";
	public static final String TYPE_OLD_GRADE_PROMPT = "Enter old grade: ";
	public static final String TYPE_NEW_GRADE_PROMPT = "Enter new grade: ";
	public static final String NO_GRADES = "No grades available.";

	public static final String NO_ABSENCES = "No absences.";
	public static final String NO_EXAMS = "No upcoming exams.";


	public static final String TYPE_CHOICE_PROMPT = "Enter choice #: ";

	public static final String INVALID_CREDENTIALS = "Invalid credentials. Try again.";
	public static final String LOGGING_OUT = "Logging out...";

	public static final String ENTRY_CODE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	public static final int ENTRY_CODE_LENGTH = 4;
	public static final String TYPE_ENTRY_CODE_PROMPT = "Enter entry code: ";

	public static final String INVALID_USER_NAME = "No user found with that name. Try again.";
	public static final String INVALID_ROLE = "User doesn't have the correct role. Try again.";
	public static final String TYPE_FULL_NAME_PROMPT = "Enter full name: ";

// H8DR


	public static final String TYPE_DATE_PROMPT = "Enter date (yyyy-MM-dd): ";
	public static final String INVALID_DATE = "Date format incorrect. Try again.";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);


	public static Grade toGrade(String grade, Teacher teacher, String subjectName, LocalDate date) throws InvalidInputError {
		return switch (grade) {
			case "A" -> new Grade(GRADE_VALUE.A, teacher, subjectName, date);
			case "B" -> new Grade(GRADE_VALUE.B, teacher, subjectName, date);
			case "C" -> new Grade(GRADE_VALUE.C, teacher, subjectName, date);
			case "D" -> new Grade(GRADE_VALUE.D, teacher, subjectName, date);
			case "F" -> new Grade(GRADE_VALUE.F, teacher, subjectName, date);
			default -> throw new InvalidInputError();
		};
	}
}
