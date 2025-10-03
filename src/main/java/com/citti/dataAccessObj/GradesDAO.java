package com.citti.dataAccessObj;

import com.citti.model.Grade;
import com.citti.model.Student;
import com.citti.model.User;
import com.citti.util.LocalDateAdapter;
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

import static com.citti.util.Constants.GRADES_FILE;


public class GradesDAO {

	private static final GradesDAO instance = new GradesDAO();
	private Map<Integer, List<Grade>> grades = new HashMap<>();
	private static final String FILE_PATH = GRADES_FILE;

	private final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).setPrettyPrinting().create();

	public GradesDAO() {
		loadFromFile();
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

		for (User user : UsersDAO.getInstance().getAllUsers()) {
			if (user instanceof Student student) {
				if (grades.containsKey(student.getId())) {
					if (!grades.isEmpty()) {
						for (Grade grade_ : student.getGrades()) {
							student.removeGrade(grade_);
						}
						for (Grade grade : grades.get(student.getId())) {
							student.addGrade(grade);
						}
					}
				}
			}
		}
	}

	public void addGradeToDAO(Student student, Grade grade) {
		student.addGrade(grade);
		grades.put(student.getId(), new ArrayList<>(student.getGrades()));
	}

	public void removeGradeFromDAO(Student student, Grade grade) {
		student.getGrades().remove(grade);
		grades.get(student.getId()).remove(grade);

		System.out.println(grades.get(student.getId()));
		System.out.println(student.getGrades());
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
