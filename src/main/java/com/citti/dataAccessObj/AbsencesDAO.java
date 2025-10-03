package com.citti.dataAccessObj;

import com.citti.model.Absence;
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


public class AbsencesDAO {

	private static final AbsencesDAO instance = new AbsencesDAO();
	private Map<Student, List<Absence>> absences = new HashMap<>();
	private static final String FILE_PATH = "data/absences.json";

	private final Gson gson = GsonUtil.gson;

	public AbsencesDAO() {
		loadFromFile();
	}

	public void saveToFile() {
		String json = gson.toJson(absences);
		try (FileWriter writer = new FileWriter(FILE_PATH)) {
			writer.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadFromFile() {
		try {
			String json = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
			absences = gson.fromJson(json, new TypeToken<Map<Student, List<Absence>>>(){}.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	public void addAbsenceToDAO(Student student, Absence absence) {
		student.addAbsence(absence);
		absences.put(student, student.getAbsences());
	}

	public void removeAbsenceFromDAO(Student student, Absence absence) {
		student.removeAbsence(absence);
		absences.remove(student);
	}

	public List<Absence> getAbsencesForStudent(Student student) {
		return absences.get(student);
	}

	public static AbsencesDAO getInstance() {
		return instance;
	}
}
