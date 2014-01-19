package org.oiga.estructura.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FileNamingUtils {
	public static final SimpleDateFormat FILE_DATE_PREFIX_FORMAT = new SimpleDateFormat("yyyy_MM");
	public static final String CULTURA_UNAM_FILE_NAME = "_cultura_unam_%s.json";
	
	public static String getName(String format){
		return  String.format(format, FILE_DATE_PREFIX_FORMAT.format(new Date()));
	}
	public static String getName(String format, Date date){
		return  String.format(format, FILE_DATE_PREFIX_FORMAT.format(date));
	}
	public static String getCulturaUnamFileName(String prefix){
		return getName(prefix+CULTURA_UNAM_FILE_NAME);
	}
	public static String getCulturaUnamFileName(String prefix, Date date){
		return getName(prefix+CULTURA_UNAM_FILE_NAME);
	}
}
