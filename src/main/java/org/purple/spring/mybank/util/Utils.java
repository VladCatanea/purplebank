package org.purple.spring.mybank.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import org.purple.spring.mybank.errors.TransactionException;

public class Utils {

	public static String getFileExtension(String filename) {
		if(! filename.contains(".")) {
			return "";
		}
		return filename.substring(filename.lastIndexOf("."));
	}

	public static Calendar stringToCal(String date)
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		try{
			cal.setTime(sdf.parse(date));
		}catch(ParseException e) {
			throw new TransactionException("Calendar Parse Error", e);
		}
		return cal;
	}
}
