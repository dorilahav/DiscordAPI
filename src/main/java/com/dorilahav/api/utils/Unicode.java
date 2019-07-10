package com.dorilahav.api.utils;

import java.util.Arrays;

public class Unicode extends UnicodeUtils {

    public static String[]
            LETTERS = UnicodeUtils.LETTERS.clone();

    public static String
            YES = "✔",
            NO = "❌";

    /**
     * Gets the unicode version of {@code letter}.
     *
     * @param letter the letter.
     * @return the unicode version of {@code letter}.
     */
    public static String getLetter(char letter) {
        return UnicodeUtils.getLetter(letter);
    }

    /**
     * Converts a {@code text} that only contains letters and spaces to its unicode version.
     *
     * @param text the text.
     * @return the unicode version of {@code text}.
     */
    public static String getUnicode(String text) {
        return UnicodeUtils.getUnicode(text);
    }

    public static char getLowerCase(String unicode) {

        if (unicode.length() != 1)
            throw new IllegalArgumentException("unicode can only be one letter long!");

        return UnicodeUtils.getLowerCase(unicode);
    }

    public static char getUpperCase(String unicode) {

        if (unicode.length() != 1)
            throw new IllegalArgumentException("unicode can only be one letter long!");

        return UnicodeUtils.getUpperCase(unicode);
    }

    public static int getLetterPlace(String unicode) {

        if (unicode.length() != 1)
            throw new IllegalArgumentException("unicode can only be one letter long!");

        return UnicodeUtils.getLetterPlace(unicode);
    }

    public static boolean isLetter(String unicode) {

        if (unicode.length() != 1)
            throw new IllegalArgumentException("unicode can only be one letter long!");

        return UnicodeUtils.isLetter(unicode);
    }

}
