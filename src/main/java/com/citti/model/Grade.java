package com.citti.model;

import com.citti.util.Constants.GRADE_VALUE;

import java.time.LocalDate;


public record Grade(GRADE_VALUE grade, Teacher teacher, String subjectName, LocalDate date) { }
