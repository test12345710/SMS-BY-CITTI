package com.citti.dataAccessObj;

import com.citti.model.Exam;
import com.citti.model.Grade;
import com.citti.model.Student;
import com.citti.util.GsonUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GradesDAO {

	private static final GradesDAO instance = new GradesDAO();
	private Map<Student, List<Grade>> grades = new HashMap<>();
	private static final String FILE_PATH = "data/grades.json";

	private final Gson gson = GsonUtil.gson;

	public GradesDAO() {
		loadFromFile();
	}

	public void saveToFile() {
		String json = gson.toJson(grades);
		try (FileWriter writer = new FileWriter(FILE_PATH)) {
			writer.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadFromFile() {
		try {
			String json = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
			grades = gson.fromJson(json, new TypeToken<Map<Student, List<Grade>>>(){}.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addGradeToDAO(Student student, Grade grade) {
		student.addGrade(grade);
		grades.put(student, student.getGrades());
	}

	public void removeGradeFromDAO(Student student, Grade grade) {
		student.removeGrade(grade);
		grades.remove(student);
	}

	public List<Grade> getGradesForStudent(Student student) {
		return grades.get(student);
	}

	public void updateGradeInDAO(Student student, Grade oldGrade, Grade newGrade) {
		student.removeGrade(oldGrade);
		student.addGrade(newGrade);
		grades.put(student, student.getGrades());

	}

	public static GradesDAO getInstance() {
		return instance;
	}
}
