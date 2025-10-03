package com.citti.dataAccessObj;

import com.citti.model.*;
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

import static com.citti.util.Constants.EXAMS_FILE;


public class ExamsDAO {

	private static final ExamsDAO instance = new ExamsDAO();
	private Map<Integer, List<Exam>> exams = new HashMap<>();
	private static final String FILE_PATH = EXAMS_FILE;

	private final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).setPrettyPrinting().create();

	public ExamsDAO() {
		loadFromFile();
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

		for (User user : UsersDAO.getInstance().getAllUsers()) {
			if (user instanceof Student student) {
				if (exams.containsKey(student.getId())) {
					if (!exams.get(student.getId()).isEmpty()) {
						for (Exam exam_ : student.getUpcomingExams()) {
							student.removeUpcomingExam(exam_);
						}
						for (Exam exam : exams.get(student.getId())) {
							student.addUpcomingExam(exam);
						}
					}
				}
			}
		}
	}

	public void addExamToDAO(Student student, Exam exam) {
		student.addUpcomingExam(exam);
		exams.put(student.getId(), new ArrayList<>(student.getUpcomingExams()));
	}

	public void removeExamFromDAO(Student student, Exam exam) {
		student.removeUpcomingExam(exam);
		exams.get(student.getId()).remove(exam);
	}

	public List<Exam> getExamsForStudent(Student student) {
		return exams.get(student.getId());
	}

	public static ExamsDAO getInstance() {
		return instance;
	}
}
