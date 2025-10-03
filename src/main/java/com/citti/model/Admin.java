package com.citti.model;

import com.citti.dataAccessObj.AbsencesDAO;
import com.citti.dataAccessObj.GradesDAO;
import com.citti.dataAccessObj.UsersDAO;
import com.citti.util.Constants.Role;
import com.citti.util.LoginInfo;
import com.citti.auth.SecurityUtil;


public class Admin extends User {

	private final UsersDAO usersDAO = UsersDAO.getInstance();
	private final GradesDAO gradesDAO = GradesDAO.getInstance();
	private final AbsencesDAO absencesDAO = AbsencesDAO.getInstance();

	public Admin(int id, String firstName, String lastName, Role role, LoginInfo loginInfo) {
		super(id, firstName, lastName, role, loginInfo);
	}

	public void fireTeacher(Teacher teacher) {
		usersDAO.removeUserFromDAO(teacher);
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

	public String changeEntryCode(Student student) {
		String newEntryCode = SecurityUtil.generateEntryCode();
		usersDAO.updateEntryCode(student.getFullName(), newEntryCode);
		return newEntryCode;
	}


}
