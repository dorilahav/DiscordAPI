package com.dorilahav.api.utils;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Time {
	
	public static final DateTimeFormatter SHORT_DATE_FORMATTER = 
			DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
	
	public static final DateTimeFormatter MEDIUM_DATE_FORMATTER = 
			DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
	
	public static final DateTimeFormatter LONG_DATE_FORMATTER = 
			DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
	
	public static final DateTimeFormatter FULL_DATE_FORMATTER = 
			DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);

}
