package com.citti.model;

import com.citti.dataAccessObj.AbsencesDAO;
import com.citti.dataAccessObj.GradesDAO;
import com.citti.dataAccessObj.UsersDAO;
import com.citti.util.Constants.Role;
import com.citti.util.LoginInfo;
import com.citti.util.SecurityUtil;


public class Admin extends User {
	private final GradesDAO gradesDAO;
	private final AbsencesDAO absencesDAO;
	private final UsersDAO usersDAO;

	public Admin(int id, String firstName, String lastName, Role role, LoginInfo loginInfo,
	             GradesDAO gradesDAO, AbsencesDAO absencesDAO, UsersDAO usersDAO) {
		super(id, firstName, lastName, role, loginInfo);
		this.gradesDAO = gradesDAO;
		this.absencesDAO = absencesDAO;
		this.usersDAO = usersDAO;
	}

	public void hireTeacher(Teacher teacher) {
		usersDAO.addUserToDAO(teacher);
	}

	public void fireTeacher(Teacher teacher) {
		usersDAO.removeUserFromDAO(teacher);
	}

	public void enrollStudent(Student student) {
		usersDAO.addUserToDAO(student);
	}

	public void expelStudent(Student student) {
		usersDAO.removeUserFromDAO(student);
	}

	public void modifyGrade(Student student, Grade currentGrade, Grade newGrade) {
		gradesDAO.updateGradeInDAO(student, currentGrade, newGrade);
	}

	public void removeAbsence(Student student, Absence absence) {
		absencesDAO.removeAbsenceFromDAO(student, absence);
	}

	public void changeEntryCode(Student student) {
		usersDAO.updateEntryCode(student.getFullName(), SecurityUtil.generateEntryCode());
	}


}
