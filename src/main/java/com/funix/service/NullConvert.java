package com.funix.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NullConvert {
	public static String toString(String string) {
		return string == null ? "" : string;
	}

	public static LocalDate toLocalDate(Date date) {
		return date == null ? null : date.toLocalDate();
	}

	public static LocalDate toLocalDate(String date) {
		DateTimeFormatter datetimeFormat = DateTimeFormatter
				.ofPattern("yyyy-MM-dd");
		return (date == null || date.equals("")) ? 
										null : 
										LocalDate.parse(date, datetimeFormat);
	}

	public static double toDouble(String number) {
		return (number == null || number.equals("")) ? 
										0 : 
										Double.parseDouble(number);
	}

	public static int toInt(String number) {
		return (number == null || number.equals("")) ? 
										0 : 
										Integer.parseInt(number);
	}

}
