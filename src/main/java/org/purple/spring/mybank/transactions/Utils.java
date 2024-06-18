package org.purple.spring.mybank.transactions;

public class Utils {

	public static String getFileExtension(String filename) {
		if(! filename.contains(".")) {
			return "";
		}
		return filename.substring(filename.lastIndexOf("."));
	}

	

}
