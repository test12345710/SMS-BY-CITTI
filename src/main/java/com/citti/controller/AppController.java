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


public record AppController(UsersDAO usersDAO, GradesDAO gradesDAO, AbsencesDAO absencesDAO, ExamsDAO examsDAO, AuthService authService) {

	public void routeUser(User user) {

		switch (user.getRole()) {
			case STUDENT:
				Student student = (Student) user;
				StudentService serviceS = new StudentService(student);

				System.out.println("Welcome, " + student.getFullName() + "!");
				while (true) {
					System.out.println("\nStudent Menu:");
					System.out.println("1. View Grades");
					System.out.println("2. View Absences");
					System.out.println("3. View Upcoming Exams");
					System.out.println("4. Logout");
					int choice = InputUtil.readInt("Enter your choice: ", 1, 4);
					switch (choice) {
						case 1:
							List<Grade> grades = serviceS.getGrades();
							if (grades.isEmpty()) {
								System.out.println("You currently don't have any grades.");
								break;
							}
							System.out.println("Your current grades are: ");
							for (Grade grade : grades) {
								System.out.println(grade.subjectName() + ":" + grade.grade());
							}
							break;
						case 2:
							List<Absence> absences = serviceS.getAbsences();
							if (absences.isEmpty()) {
								System.out.println("You currently don't have any absences.");
								break;
							}
							System.out.println("Your current absences are: ");
							for (Absence absence : absences) {
								System.out.println("On " + absence.date() + " from " + absence.teacher());
							}
							break;
						case 3:
							List<Exam> exams = serviceS.getUpcomingExams();
							if (exams.isEmpty()) {
								System.out.println("You currently don't have any upcoming exams.");
								break;
							}
							System.out.println("Your current upcoming exams are: ");
							for (Exam exam : exams) {
								System.out.println("On " + exam.date() + " with " + exam.teacher());
							}
							break;
						case 4:
							System.out.println("Logging out...");
							return;
					}
				}
			case TEACHER:
				Teacher teacher = (Teacher) user;
				TeacherService serviceT = new TeacherService(teacher);

				System.out.println("Welcome, " + teacher.getFullName() + "!");
				while (true) {
					System.out.println("\nTeacher Menu:");
					System.out.println("1. Assign Grade");
					System.out.println("2. Add Absence");
					System.out.println("3. Announce Exam");
					System.out.println("4. Logout");
					int choice = InputUtil.readInt("Enter your choice: ", 1, 3);
					switch (choice) {
						case 1:
							Student studentG = (Student) usersDAO.findUserInDAO(readInput("Enter student name: "));

							LocalDate dateG = null;
							while (dateG == null) {
								try {
									dateG = parseDate(readInput("Enter date: "));
								} catch (Exception e) {
									System.out.println("Invalid date. Please try again.");
								}
							}

							Grade grade = toGrade(readInput("Enter grade: "), teacher, readInput("Enter subject name: "), dateG);
							serviceT.assignGrade(studentG, grade);
							break;
						case 2:
							Student studentA = (Student) usersDAO.findUserInDAO(readInput("Enter student name: "));
							LocalDate dateA = null;
							while (dateA == null) {
								try {
									dateA = parseDate(readInput("Enter date: "));
								} catch (Exception e) {
									System.out.println("Invalid date. Please try again.");
								}
							}

							Absence absence = new Absence(dateA, teacher);
							serviceT.addAbsence(studentA, absence);
							break;
						case 3:
							LocalDate dateE = null;
							while (dateE == null) {
								try {
									dateE = parseDate(readInput("Enter date: "));
								} catch (Exception e) {
									System.out.println("Invalid date. Please try again.");
								}
							}

							Student studentE = (Student) usersDAO.findUserInDAO(readInput("Enter student name: "));
							Exam exam = new Exam(dateE, readInput("Enter subject name: "), teacher);
							serviceT.announceExam(studentE, exam);
							break;
						case 4:
							System.out.println("Logging out...");
							return;
					}
				}
			case ADMIN:
				Admin admin = (Admin) user;
				AdminService serviceA = new AdminService(admin);

				System.out.println("Welcome, " + admin.getFullName() + "!");

				while (true) {
					System.out.println("\nAdmin Menu:");
					System.out.println("1. Fire teacher");
					System.out.println("2. Expel Student");
					System.out.println("3. Modify Grade");
					System.out.println("4. Revoke Absence");
					System.out.println("5. Change Entry Code");
					System.out.println("6. Logout");
					int choice = InputUtil.readInt("Enter your choice: ", 1, 4);
					switch (choice) {
						case 1:
							Teacher teacherF = (Teacher) usersDAO.findUserInDAO(readInput("Enter teacher name: "));
							serviceA.fireTeacher(teacherF);
							System.out.println("Teacher fired!");
							break;
						case 2:
							Student studentE = (Student) usersDAO.findUserInDAO(readInput("Enter student name: "));
							serviceA.expelStudent(studentE);
							System.out.println("Student expelled!");
							break;
						case 3:
							Student studentG = (Student) usersDAO.findUserInDAO(readInput("Enter student name: "));
							Teacher teacherG = (Teacher) usersDAO.findUserInDAO(readInput("Enter teacher name: "));
							GRADE_VALUE oldGrade = GRADE_VALUE.valueOf(InputUtil.readInput("Enter old grade: "));
							GRADE_VALUE newGrade = GRADE_VALUE.valueOf(InputUtil.readInput("Enter new grade: "));
							Grade oldGradeObj = studentG.getGrade(oldGrade, teacherG);
							serviceA.changeStudentGrade(studentG, oldGradeObj, new Grade(newGrade, teacherG, oldGradeObj.subjectName(), oldGradeObj.date()));
							System.out.println("Grade modified!");
							break;
						case 4:
							Student studentA = (Student) usersDAO.findUserInDAO(readInput("Enter student name: "));
							Teacher teacherA = (Teacher) usersDAO.findUserInDAO(readInput("Enter teacher name: "));
							Absence absenceA = new Absence(parseDate(readInput("Enter date: ")), teacherA);
							serviceA.revokeStudentAbsence(studentA, absenceA);
							System.out.println("Absence revoked!");
							break;
						case 5:
							Student studentCE = (Student) usersDAO.findUserInDAO(readInput("Enter student name: "));
							String newEntryCode = serviceA.changeStudentEntryCode(studentCE);
							System.out.println("Entry code changed to "+ newEntryCode + "!");
							break;
						case 6:
							System.out.println("Logging out...");
							return;
					}
				}
			case PRINCIPAL:
				Principal principal = (Principal) user;
				PrincipalService serviceP = new PrincipalService(principal);

				while (true) {
					System.out.println("\nPrincipal Menu:");
					System.out.println("1. Fire teacher");
					System.out.println("2. Expel Student");
					System.out.println("3. Modify Grade");
					System.out.println("4. Revoke Absence");
					System.out.println("5. Change Entry Code");
					System.out.println("6. Make an announcement");
					System.out.println("7. View All Student Grades");
					System.out.println("8. View All Student Absences");
					System.out.println("9. View All Student Upcoming Exams");
					System.out.println("10. Logout");
					int choice = InputUtil.readInt("Enter your choice: ", 1, 6);
					switch (choice) {
						case 1:
							Teacher teacherF = (Teacher) usersDAO.findUserInDAO(readInput("Enter teacher name: "));
							serviceP.fireTeacher(teacherF);
							System.out.println("Teacher fired!");
							break;
						case 2:
							Student studentE = (Student) usersDAO.findUserInDAO(readInput("Enter student name: "));
							serviceP.expelStudent(studentE);
							System.out.println("Student expelled!");
							break;
						case 3:
							Student studentG = (Student) usersDAO.findUserInDAO(readInput("Enter student name: "));
							Teacher teacherG = (Teacher) usersDAO.findUserInDAO(readInput("Enter teacher name: "));
							GRADE_VALUE oldGrade = GRADE_VALUE.valueOf(InputUtil.readInput("Enter old grade: "));
							GRADE_VALUE newGrade = GRADE_VALUE.valueOf(InputUtil.readInput("Enter new grade: "));
							Grade oldGradeObj = studentG.getGrade(oldGrade, teacherG);
							serviceP.changeStudentGrade(studentG, oldGradeObj, new Grade(newGrade, teacherG, oldGradeObj.subjectName(), oldGradeObj.date()));
							System.out.println("Grade modified!");
							break;
						case 4:
							Student studentA = (Student) usersDAO.findUserInDAO(readInput("Enter student name: "));
							Teacher teacherA = (Teacher) usersDAO.findUserInDAO(readInput("Enter teacher name: "));
							Absence absenceA = new Absence(parseDate(readInput("Enter date: ")), teacherA);
							serviceP.revokeStudentAbsence(studentA, absenceA);
							System.out.println("Absence revoked!");
							break;
						case 5:
							Student studentCE = (Student) usersDAO.findUserInDAO(readInput("Enter student name: "));
							String newEntryCode = serviceP.changeStudentEntryCode(studentCE);
							System.out.println("Entry code changed to "+ newEntryCode + "!");
							break;
						case 6:
							String announcement = readInput("Enter announcement: ");
							Role role = Role.valueOf(readInput("To which group? "));
							serviceP.announceTo(role, announcement);
							break;
						case 7:
							Map<Student, List<Grade>> grades = serviceP.getAllGrades();
							for (Map.Entry<Student, List<Grade>> entry : grades.entrySet()) {
								System.out.println(entry.getKey().getFullName() + ": ");
								for (Grade grade : entry.getValue()) {
									System.out.println("   " + grade.subjectName() + ": " + grade.grade());
								}
							}
							break;
						case 8:
							Map<Student, List<Absence>> absences = serviceP.getAllAbsences();
							for (Map.Entry<Student, List<Absence>> entry : absences.entrySet()) {
								System.out.println(entry.getKey().getFullName() + ": ");
								for (Absence absence : entry.getValue()) {
									System.out.println("   " + absence.date() + " from " + absence.teacher());
								}
							}
							break;
						case 9:
							Map<Student, List<Exam>> exams = serviceP.getAllUpcomingExams();
							for (Map.Entry<Student, List<Exam>> entry : exams.entrySet()) {
								System.out.println(entry.getKey().getFullName() + ": ");
								for (Exam exam : entry.getValue()) {
									System.out.println("   " + exam.date() + " with " + exam.teacher());
								}
							}
							break;
						case 10:
							System.out.println("Logging out...");
							return;
					}
				}
			default:
				System.out.println("Unknown role. Cannot route user.");
		}
	}
}