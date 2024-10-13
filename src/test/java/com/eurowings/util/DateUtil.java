package com.eurowings.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

	public static String geToday() {
		LocalDate date = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return date.format(formatter);
	}

	public static String getTomorrow() {
		LocalDate tomorrow = LocalDate.now().plusDays(1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return tomorrow.format(formatter);
	}

	private static String getPastDate() {
		LocalDate tomorrow = LocalDate.now().minusDays(3);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return tomorrow.format(formatter);
	}

	public static String convertDateFormat(String oldDateString) {
		SimpleDateFormat oldFormatter = new SimpleDateFormat("E, dd/MM/");
		SimpleDateFormat newFormatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = oldFormatter.parse(oldDateString);
			date.setYear(2024 - 1900);
			return newFormatter.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getDate(String value) {
		if (value.equalsIgnoreCase("today".trim())) {
			return DateUtil.geToday();
		} else if (value.equalsIgnoreCase("tomorrow".trim())) {
			return DateUtil.getTomorrow();
		} else if (value.equals("past".trim())) {
			return DateUtil.getPastDate();
		}
		return value;
	}
}
