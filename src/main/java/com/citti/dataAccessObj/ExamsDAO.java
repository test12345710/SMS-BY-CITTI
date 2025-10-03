package com.citti.dataAccessObj;

import com.citti.model.Exam;
import com.citti.model.Student;
import com.citti.model.Teacher;
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


public class ExamsDAO {

	private static final ExamsDAO instance = new ExamsDAO();
	private Map<Integer, List<Exam>> exams = new HashMap<>();
	private static final String FILE_PATH = "data/exams.json";

	private final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).setPrettyPrinting().create();

	public ExamsDAO() {
		loadFromFile();
		addExamToDAO((Student) UsersDAO.getInstance().findUserInDAO("Citti Dabozo"), new Exam(LocalDate.now(), "Math", new Teacher("John", "Doe", Role.TEACHER,
				new LoginInfo("john", "123"))));
	}

	public void saveToFile() {
		try (FileWriter writer = new FileWriter(FILE_PATH)) {
			gson.toJson(exams, writer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadFromFile() {
		try (FileReader reader = new FileReader(FILE_PATH)) {
			Type type = new TypeToken<Map<Integer, List<Exam>>>(){}.getType();
			Map<Integer, List<Exam>> loaded = gson.fromJson(reader, type);
			if (loaded != null) exams = loaded;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addExamToDAO(Student student, Exam exam) {
		student.addUpcomingExam(exam);
		exams.put(student.getId(), new ArrayList<>(student.getUpcomingExams()));
	}

	public void removeExamFromDAO(Student student, Exam exam) {
		student.removeUpcomingExam(exam);
		exams.remove(student.getId());
	}

	public List<Exam> getExamsForStudent(Student student) {
		return exams.get(student.getId());
	}

	public static ExamsDAO getInstance() {
		return instance;
	}
}
