package com.citti.dataAccessObj;

import com.citti.model.Exam;
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


public class ExamsDAO {

	private static final ExamsDAO instance = new ExamsDAO();
	private Map<Student, List<Exam>> exams = new HashMap<>();
	private static final String FILE_PATH = "data/exams.json";

	private final Gson gson = GsonUtil.gson;

	public ExamsDAO() {
		loadFromFile();
	}

	public void saveToFile() {
		String json = gson.toJson(exams);
		try (FileWriter writer = new FileWriter(FILE_PATH)) {
			writer.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadFromFile() {
		try {
			String json = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
			exams = gson.fromJson(json, new TypeToken<Map<Student, List<Exam>>>(){}.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addExamToDAO(Student student, Exam exam) {
		student.addUpcomingExam(exam);
		exams.put(student, student.getUpcomingExams());
	}

	public void removeExamFromDAO(Student student, Exam exam) {
		student.removeUpcomingExam(exam);
		exams.remove(student);
	}

	public List<Exam> getExamsForStudent(Student student) {
		return exams.get(student);
	}

	public static ExamsDAO getInstance() {
		return instance;
	}
}
