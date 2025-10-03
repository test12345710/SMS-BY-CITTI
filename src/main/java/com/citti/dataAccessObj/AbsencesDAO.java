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

import static com.citti.util.Constants.ABSENCES_FILE;


public class AbsencesDAO {

	private static final AbsencesDAO instance = new AbsencesDAO();
	private Map<Integer, List<Absence>> absences = new HashMap<>();
	private static final String FILE_PATH = ABSENCES_FILE;

	private final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).setPrettyPrinting().create();

	public AbsencesDAO() {
		loadFromFile();
	}

	public void saveToFile() {
		try (FileWriter writer = new FileWriter(FILE_PATH)) {
			gson.toJson(absences, writer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadFromFile() {
		try (FileReader reader = new FileReader(FILE_PATH)) {
			Type type = new TypeToken<Map<Integer, List<Absence>>>(){}.getType();
			Map<Integer, List<Absence>> loaded = gson.fromJson(reader, type);
			if (loaded != null) absences = loaded;
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (User user : UsersDAO.getInstance().getAllUsers()) {
			if (user instanceof Student student) {
				if (absences.containsKey(student.getId())) {
					if (!absences.isEmpty()) {
						for (Absence absence_ : student.getAbsences()) {
							student.removeAbsence(absence_);
						}
						for (Absence absence : absences.get(student.getId())) {
							student.addAbsence(absence);
						}
					}
				}
			}
		}

	}



	public void addAbsenceToDAO(Student student, Absence absence) {
		student.addAbsence(absence);
		absences.put(student.getId(), new ArrayList<>(student.getAbsences()));
	}

	public void removeAbsenceFromDAO(Student student, Absence absence) {
		student.removeAbsence(absence);
		absences.get(student.getId()).remove(absence);
	}

	public List<Absence> getAbsencesForStudent(Student student) {
		return absences.get(student.getId());
	}

	public static AbsencesDAO getInstance() {
		return instance;
	}
}
