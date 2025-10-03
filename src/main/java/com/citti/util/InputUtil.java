package com.citti.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import static com.citti.util.Constants.*;


public class InputUtil {
	public final static Scanner sc = new Scanner(System.in);

	public static String readInput(String prompt) {
		System.out.print(prompt);
		return sc.nextLine().trim();
	}

	public static int readInt(String prompt, int min, int max) {
		System.out.print(prompt);
		int choice;
		try {
			choice = Integer.parseInt(sc.nextLine().trim());
			if (choice >= min && choice <= max) {
				return choice;
			} else {
				return INVALID_INPUT;
			}
		} catch (NumberFormatException e) {
			return INVALID_INPUT;
		}
	}

	public static LocalDate parseDate(String input) {
		try {
			if (input == null || input.trim().isEmpty()) {
				return INVALID_DATE;
			}
			return LocalDate.parse(input.trim(), DATE_FORMATTER);
		} catch (DateTimeParseException e) {
			return INVALID_DATE;
		}
	}
	
	public static LocalDate readDate(String prompt) {
		while (true) {
			try {
				String input = readInput(prompt);
				if (input.trim().isEmpty()) {
					return INVALID_DATE;
				}
				LocalDate date = parseDate(input);
				if (date != INVALID_DATE) {
					return date;
				}
			} catch (Exception e) {
				return INVALID_DATE;
			}
		}
	}
}

