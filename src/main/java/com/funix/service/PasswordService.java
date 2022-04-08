/*
 * PasswordService.java    1.00    2022-04-07
 */

package com.funix.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Helper class for generating random password.
 * @author Giang_Nhat_Truong
 *
 */
public class PasswordService {
	
	/**
	 * Generate random password with default pattern. 
	 * @return
	 */
	public static String generateRandomPassword() {
		return generateRandomPassword(2, 3, 2, 2);
	}
	
	/**
	 * Generate random password with specific number of
	 * random upper-case letters, lower-case letters,
	 * special characters, digits.
	 * @param numberOfUppercaseLetters
	 * @param numberOfLowercaseLetters
	 * @param numberOfSpecialCharacters
	 * @param numberOfDigits
	 * @return
	 */
	public static String generateRandomPassword(
			int numberOfUppercaseLetters,
			int numberOfLowercaseLetters,
			int numberOfSpecialCharacters,
			int numberOfDigits) {
		Random random = new Random();
		List<Character> characterList = new ArrayList<>();
		
		for (int i = 0; i < numberOfUppercaseLetters; i++) {
			characterList.add((char) random.nextInt('A', 'Z' + 1));
		}
		
		for (int i = 0; i < numberOfLowercaseLetters; i++) {
			characterList.add((char) random.nextInt('a', 'z' + 1));
		}
		
		for (int i = 0; i < numberOfSpecialCharacters; i++) {
			characterList.add(getRandomSpecialCharacter());
		}
		
		for (int i = 0; i < numberOfDigits; i++) {
			characterList.add((char) random.nextInt('0', '9' + 1));
		}
		
		return shufflePassword(characterList);
	}
	
	/**
	 * Get random special character.
	 * @return
	 */
	private static char getRandomSpecialCharacter() {
		Random random = new Random();
		String specialCharacters = "@#$!%*?&";
		int randomIndex = random.nextInt(specialCharacters.length());
		return specialCharacters.charAt(randomIndex);
	}
	
	/**
	 * Shuffle character list and return a string
	 * combined of these characters.
	 * @param characterList
	 * @return
	 */
	private static String shufflePassword(List<Character> characterList) {
		StringBuilder stringBuilder = new StringBuilder();
		Collections.shuffle(characterList);
		
		for (char character: characterList) {
			stringBuilder.append(character);
		}
		
		return stringBuilder.toString();
	}
}
