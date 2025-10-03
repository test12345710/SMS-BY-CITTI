package com.citti.model;

import java.time.LocalDate;


public record Exam(LocalDate date, String subjectName, Teacher teacher) { }
