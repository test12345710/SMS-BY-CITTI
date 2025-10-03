package com.citti.controller;

import com.citti.dataAccessObj.AbsencesDAO;
import com.citti.dataAccessObj.ExamsDAO;
import com.citti.dataAccessObj.GradesDAO;
import com.citti.dataAccessObj.UsersDAO;
import com.citti.model.*;
import com.citti.service.*;
import com.citti.util.Constants.GRADE_VALUE;
import com.citti.util.Constants.Role;
import com.citti.util.InputUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.citti.util.Constants.toGrade;
import static com.citti.util.InputUtil.parseDate;
import static com.citti.util.InputUtil.readInput;

public record AppController(
		UsersDAO usersDAO,
		GradesDAO gradesDAO,
		AbsencesDAO absencesDAO,
		ExamsDAO examsDAO,
		AuthService authService
) {

	public void routeUser(User user) {
		switch (user.getRole()) {
			case STUDENT -> handleStudent((Student) user);
			case TEACHER -> handleTeacher((Teacher) user);
			case ADMIN -> handleAdmin((Admin) user);
			case PRINCIPAL -> handlePrincipal((Principal) user);
			default -> System.out.println("Unknown role. Cannot route user.");
		}
	}

	// ==================== STUDENT ====================
	private void handleStudent(Student student) {
		StudentService service = new StudentService(student);
		System.out.println("Welcome, " + student.getFullName() + "!");

		while (true) {
			printMenu("Student Menu", "View Grades", "View Absences", "View Upcoming Exams", "Logout");
			int choice = InputUtil.readInt("Enter your choice: ", 1, 4);

			switch (choice) {
				case 1 -> printGrades(service.getGrades());
				case 2 -> printAbsences(service.getAbsences());
				case 3 -> printExams(service.getUpcomingExams());
				case 4 -> { System.out.println("Logging out..."); return; }
			}
		}
	}

	// ==================== TEACHER ====================
	private void handleTeacher(Teacher teacher) {
		TeacherService service = new TeacherService(teacher);
		System.out.println("Welcome, " + teacher.getFullName() + "!");

		while (true) {
			printMenu("Teacher Menu", "Assign Grade", "Add Absence", "Announce Exam", "Remove Grade", "Cancel Exam", "Logout");
			int choice = InputUtil.readInt("Enter your choice: ", 1, 6);

			switch (choice) {
				case 1 -> assignGrade(service, teacher);
				case 2 -> addAbsence(service, teacher);
				case 3 -> announceExam(service, teacher);
				case 4 -> removeGrade(service, teacher);
				case 5 -> cancelExam(service, teacher);
				case 6 -> { System.out.println("Logging out..."); return; }
			}
		}
	}

	// ==================== ADMIN ====================
	private void handleAdmin(Admin admin) {
		AdminService service = new AdminService(admin);
		System.out.println("Welcome, " + admin.getFullName() + "!");

		while (true) {
			printMenu("Admin Menu", "Fire Teacher", "Expel Student", "Modify Grade", "Revoke Absence", "Change Entry Code", "Logout");
			int choice = InputUtil.readInt("Enter your choice: ", 1, 6);

			switch (choice) {
				case 1 -> fireTeacher(service);
				case 2 -> expelStudent(service);
				case 3 -> modifyGrade(service);
				case 4 -> revokeAbsence(service);
				case 5 -> changeEntryCode(service);
				case 6 -> { System.out.println("Logging out..."); return; }
			}
		}
	}

	// ==================== PRINCIPAL ====================
	private void handlePrincipal(Principal principal) {
		PrincipalService service = new PrincipalService(principal);
		System.out.println("Welcome, " + principal.getFullName() + "!");

		while (true) {
			printMenu("Principal Menu",
					"Fire Teacher", "Expel Student", "Modify Grade", "Revoke Absence",
					"Change Entry Code", "Make Announcement", "View All Grades", "View All Absences",
					"View All Exams", "Logout"
			);

			int choice = InputUtil.readInt("Enter your choice: ", 1, 10);

			switch (choice) {
				case 1 -> fireTeacher(service);
				case 2 -> expelStudent(service);
				case 3 -> modifyGrade(service);
				case 4 -> revokeAbsence(service);
				case 5 -> changeEntryCode(service);
				case 6 -> makeAnnouncement(service);
				case 7 -> viewAllGrades(service);
				case 8 -> viewAllAbsences(service);
				case 9 -> viewAllExams(service);
				case 10 -> { System.out.println("Logging out..."); return; }
			}
		}
	}

	// ==================== HELPER METHODS ====================

	private void printMenu(String title, String... options) {
		System.out.println("\n" + title + ":");
		for (int i = 0; i < options.length; i++) {
			System.out.println((i + 1) + ". " + options[i]);
		}
	}

	private void printGrades(List<Grade> grades) {
		if (grades.isEmpty()) System.out.println("No grades available.");
		else grades.forEach(g -> System.out.println(g.subjectName() + ": " + g.grade()));
	}

	private void printAbsences(List<Absence> absences) {
		if (absences.isEmpty()) System.out.println("No absences.");
		else absences.forEach(a -> System.out.println("On " + a.date() + " from " + a.teacher()));
	}

	private void printExams(List<Exam> exams) {
		if (exams.isEmpty()) System.out.println("No upcoming exams.");
		else exams.forEach(e -> System.out.println("On " + e.date() + " with " + e.teacher()));
	}

	private Student readStudent() {
		return (Student) usersDAO.findUserInDAO(readInput("Enter student name: "));
	}

	private Teacher readTeacher() {
		return (Teacher) usersDAO.findUserInDAO(readInput("Enter teacher name: "));
	}

	private LocalDate readDate() {
		while (true) {
			try {
				return parseDate(readInput("Enter date (yyyy-MM-dd): "));
			} catch (Exception e) {
				System.out.println("Invalid date. Try again.");
			}
		} // bG1f
	}

	// ==================== TEACHER ACTIONS ====================
	private void assignGrade(TeacherService service, Teacher teacher) {
		Student student = readStudent();
		LocalDate date = readDate();
		Grade grade = toGrade(readInput("Enter grade: "), teacher, readInput("Enter subject name: "), date);
		service.assignGrade(student, grade);
		System.out.println("Grade " + grade.grade() + " assigned to " + student.getFullName());
	}

	private void addAbsence(TeacherService service, Teacher teacher) {
		Student student = readStudent();
		LocalDate date = readDate();
		Absence absence = new Absence(date, teacher);
		service.addAbsence(student, absence);
		System.out.println("Absence added to " + student.getFullName());
	}

	private void announceExam(TeacherService service, Teacher teacher) {
		Student student = readStudent();
		LocalDate date = readDate();
		Exam exam = new Exam(date, readInput("Enter subject name: "), teacher);
		service.announceExam(student, exam);
		System.out.println("Exam announced for " + student.getFullName());
	}

	private void removeGrade(TeacherService service, Teacher teacher) {
		Student student = readStudent();
		LocalDate date = readDate();
		Grade grade = new Grade(GRADE_VALUE.valueOf(readInput("Enter grade: ")), teacher, readInput("Enter subject name: "), date);
		service.removeGrade(student, grade);
		System.out.println("Grade removed from " + student.getFullName());
	}

	private void cancelExam(TeacherService service, Teacher teacher) {
		Student student = readStudent();
		Exam exam = new Exam(readDate(), readInput("Enter subject name: "), teacher);
		service.cancelExam(student, exam);
		System.out.println("Exam cancelled for " + student.getFullName());
	}

	// ==================== ADMIN/PRINCIPAL ACTIONS ====================
	private void fireTeacher(Object service) {
		Teacher teacher = readTeacher();
		if (service instanceof AdminService adminService) {
			adminService.fireTeacher(teacher);
		} else if (service instanceof PrincipalService principalService) {
			principalService.fireTeacher(teacher);
		}
	}

	private void expelStudent(Object service) {
		Student student = readStudent();
		if (service instanceof AdminService adminService) {
			adminService.expelStudent(student);
		} else if (service instanceof PrincipalService principalService) {
			principalService.expelStudent(student);
		}
	}

	private void modifyGrade(Object service) {
		Student student = readStudent();
		Teacher teacher = readTeacher();
		GRADE_VALUE oldGrade = GRADE_VALUE.valueOf(readInput("Enter old grade: "));
		GRADE_VALUE newGrade = GRADE_VALUE.valueOf(readInput("Enter new grade: "));
		Grade oldGradeObj = student.getGrade(oldGrade, teacher);
		if (service instanceof AdminService adminService) {
			adminService.changeStudentGrade(student, oldGradeObj,
					new Grade(newGrade, teacher, oldGradeObj.subjectName(), oldGradeObj.date()));
		} else if (service instanceof PrincipalService principalService) {
			principalService.changeStudentGrade(student, oldGradeObj,
					new Grade(newGrade, teacher, oldGradeObj.subjectName(), oldGradeObj.date()));
		}
	}

	private void revokeAbsence(Object service) {
		Student student = readStudent();
		Teacher teacher = readTeacher();
		Absence absence = new Absence(readDate(), teacher);
		if (service instanceof AdminService adminService) {
			adminService.revokeStudentAbsence(student, absence);
		} else if (service instanceof PrincipalService principalService) {
			principalService.revokeStudentAbsence(student, absence);
		}
	}

	private void changeEntryCode(Object service) {
		Student student = readStudent();
		String newEntryCode = "";
		if (service instanceof AdminService adminService) {
			newEntryCode = adminService.changeStudentEntryCode(student);
		} else if (service instanceof PrincipalService principalService) {
			newEntryCode = principalService.changeStudentEntryCode(student);
		}
		System.out.println("Entry code changed to " + newEntryCode + ". Don't forget it!");
	}

	// Principal-specific
	private void makeAnnouncement(PrincipalService service) {
		String announcement = readInput("Enter announcement: ");
		Role role = Role.valueOf(readInput("To which group? "));
		service.announceTo(role, announcement);
		System.out.println("Announcement sent!");
	}

	private void viewAllGrades(PrincipalService service) {
		Map<Student, List<Grade>> grades = service.getAllGrades();
		grades.forEach((s, gList) -> {
			System.out.println(s.getFullName() + ": ");
			gList.forEach(g -> System.out.println("   " + g.subjectName() + ": " + g.grade()));
		});
	}

	private void viewAllAbsences(PrincipalService service) {
		Map<Student, List<Absence>> absences = service.getAllAbsences();
		absences.forEach((s, aList) -> {
			System.out.println(s.getFullName() + ": ");
			aList.forEach(a -> System.out.println("   " + a.date() + " from " + a.teacher()));
		});
	}

	private void viewAllExams(PrincipalService service) {
		Map<Student, List<Exam>> exams = service.getAllUpcomingExams();
		exams.forEach((s, eList) -> {
			System.out.println(s.getFullName() + ": ");
			eList.forEach(e -> System.out.println("   " + e.date() + " with " + e.teacher()));
		});
	}
}
