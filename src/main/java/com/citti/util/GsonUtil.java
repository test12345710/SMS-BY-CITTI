package com.citti.util;

import com.citti.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;

public class GsonUtil {

	public static final Gson gson = new GsonBuilder()
			.registerTypeAdapterFactory(
					RuntimeTypeAdapterFactory.of(User.class, "type")
							.registerSubtype(Admin.class, "admin")
							.registerSubtype(Student.class, "student")
							.registerSubtype(Teacher.class, "teacher")
							.registerSubtype(Principal.class, "principal")
			)
			.registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
			.setPrettyPrinting()
			.create();


}