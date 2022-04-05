/*
 * NullConvert.java    1.00    2022-04-05
 */

package com.funix.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Helper class for safety converting value and 
 * avoiding NullPoiterException.
 * @author Giang_Nhat_Truong
 *
 */
public class NullConvert {
	
	/**
	 * Convert string null value to empty string.
	 * @param string
	 * @return
	 */
	public static String toString(String string) {
		return string == null ? "" : string;
	}

	/**
	 * Convert Date value to LocalDate value.
	 * @param date
	 * @return
	 */
	public static LocalDate toLocalDate(Date date) {
		return date == null ? null : date.toLocalDate();
	}

	/**
	 * Convert String date value to LocalDate value
	 * with specify date pattern.
	 * @param date
	 * @return
	 */
	public static LocalDate toLocalDate(String date) {
		DateTimeFormatter datetimeFormat = DateTimeFormatter
				.ofPattern("yyyy-MM-dd");
		return (date == null || date.equals("")) ? 
										null : 
										LocalDate.parse(date, datetimeFormat);
	}

	/**
	 * Convert String number value to double value.
	 * @param number
	 * @return
	 */
	public static double toDouble(String number) {
		return (number == null || number.equals("")) ? 
										0 : 
										Double.parseDouble(number);
	}

	/**
	 * Convert String number value to integer value.
	 * @param number
	 * @return
	 */
	public static int toInt(String number) {
		return (number == null || number.equals("")) ? 
										0 : 
										Integer.parseInt(number);
	}

}
