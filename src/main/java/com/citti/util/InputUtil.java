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
		while (true) {
			System.out.print(prompt);

			try {
				int choice = Integer.parseInt(sc.nextLine().trim());
				if (choice >= min && choice <= max) {
					return choice;
				} else {
					System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
			}
		}
	}

	public static LocalDate parseDate(String input) throws InvalidInputError {
		try {
			if (input == null || input.trim().isEmpty()) {
				throw new InvalidInputError();
			}
			return LocalDate.parse(input.trim(), DATE_FORMATTER);
		} catch (DateTimeParseException e) {
			throw new InvalidInputError();
		}
	}
	
	public static LocalDate readDate(String prompt) throws InvalidInputError {
		return parseDate(readInput(prompt));
	}
}

