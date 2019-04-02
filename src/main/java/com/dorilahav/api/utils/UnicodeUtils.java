package com.dorilahav.api.utils;

import java.util.Arrays;

public class UnicodeUtils {
	
	public static String[]
			LETTERS = new String[] {"🇦", "🇧", "🇨", "🇩", "🇪", "🇫", "🇬", "🇭", "🇮", "🇯", "🇰", "🇱", "🇲", "🇳", "🇴", "🇵", "🇶", "🇷", "🇸", "🇹", "🇺", "🇻", "🇼", "🇽", "🇾", "🇿"};
	
	/**
	 * Gets the unicode version of {@code letter}.
	 * @param letter the letter.
	 * @return the unicode version of {@code letter}.
	 */
	public static String getLetter(char letter) {
		int index = letter - 'a';
		
		if (index >= LETTERS.length || index < 0) {
			index = letter - 'A';
			if (index >= LETTERS.length || index < 0)
				throw new IllegalArgumentException("'" + letter + "' is not a valid letter!");
		}
		
		return LETTERS[index];
	}
	
	/**
	 * Converts {@code letter} to {@code char} and gets the unicode version of the character.
	 * @param letter the letter.
	 * @return the unicode version of the character.
	 */
	public static String getLetter(String letter) {
		
		if (letter.isEmpty() || letter.length() > 1)
			throw new IllegalArgumentException("\"" + letter + "\" is not a valid letter!");
		
		return getLetter(letter.charAt(0));
	}
	
	/**
	 * Converts a {@code text} that only contains letters and spaces to its unicode version.
	 * @param text the text.
	 * @return the unicode version of {@code text}.
	 */
	public static String getUnicode(String text) {
		
		if (!text.replace(" ", "").matches("[a-zA-Z]+"))
			throw new IllegalArgumentException("A unicoded text can only contain letters / spaces!");
		
		String[] textSplit = text.split(" ");
		String[] unicodedTextSplit = new String[textSplit.length];
		for (int i = 0; i < textSplit.length; i++) {
			
			String textPart = textSplit[i];
			StringBuilder unicodedPart = new StringBuilder(textPart.length());
			
			for (char character : textPart.toCharArray())
				unicodedPart.append(getLetter(character));
			
			unicodedTextSplit[i] = unicodedPart.toString();
		}
		
		return String.join(" ", unicodedTextSplit);
	}
	
	public static char getLowerCase(String unicode) {
		return (char) ('a' + getLetterPlace(unicode));
	}
	
	public static char getUpperCase(String unicode) {
		return (char) ('A' + getLetterPlace(unicode));
	}
	
	public static int getLetterPlace(String unicode) {
		for (int i = 0; i < LETTERS.length; i++)
			if (unicode.equals(LETTERS[i]))
				return i;
		
		throw new IllegalArgumentException(unicode + " is not a unicode letter!");
	}
	
	public static boolean isLetter(String str) {
		return Arrays.asList(LETTERS).contains(str);
	}

}
