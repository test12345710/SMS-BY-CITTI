package com.citti.dataAccessObj;

import com.citti.model.Grade;
import com.citti.model.Student;
import com.citti.model.Teacher;
import com.citti.util.Constants.GRADE_VALUE;
import com.citti.util.Constants.Role;
import com.citti.util.LocalDateAdapter;
import com.citti.util.LoginInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GradesDAO {

	private static final GradesDAO instance = new GradesDAO();
	private Map<Integer, List<Grade>> grades = new HashMap<>();
	private static final String FILE_PATH = "data/grades.json";

	private final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).setPrettyPrinting().create();

	public GradesDAO() {
		loadFromFile();

		addGradeToDAO((Student) UsersDAO.getInstance().findUserInDAO("Citti Dabozo"), new Grade(GRADE_VALUE.A, new Teacher("John", "Doe",
				Role.TEACHER,
				new LoginInfo("john", "123")), "Math", LocalDate.now()));
	}

	public void saveToFile() {
		try (FileWriter writer = new FileWriter(FILE_PATH)) {
			gson.toJson(grades, writer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadFromFile() {
		try (FileReader reader = new FileReader(FILE_PATH)) {
			Type type = new TypeToken<Map<Integer, List<Grade>>>(){}.getType();
			Map<Integer, List<Grade>> loaded = gson.fromJson(reader, type);
			if (loaded != null) grades = loaded;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addGradeToDAO(Student student, Grade grade) {
		student.addGrade(grade);
		grades.put(student.getId(), new ArrayList<>(student.getGrades()));
	}

	public void removeGradeFromDAO(Student student, Grade grade) {
		student.removeGrade(grade);
		grades.remove(student.getId());
	}

	public List<Grade> getGradesForStudent(Student student) {
		return grades.get(student.getId());
	}

	public void updateGradeInDAO(Student student, Grade oldGrade, Grade newGrade) {
		student.removeGrade(oldGrade);
		student.addGrade(newGrade);
		grades.put(student.getId(), student.getGrades());

	}

	public static GradesDAO getInstance() {
		return instance;
	}
}
