package org.oiga.estructura.parser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateParser {
	
	public boolean isParseable(String text){
		return true;
	}
	
	public Map parse(String text){
		HashMap result = new HashMap();
		String dateRegexp = "(\\d{2}/\\d{2}/\\d{4}) de (\\d{2}:\\d{2}) a (\\d{2}:\\d{2})";
		DateTimeFormatter fechaFmt = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTimeFormatter horaFmt = DateTimeFormat.forPattern("HH:mm");
		Pattern pattern = Pattern.compile(dateRegexp);
		Matcher m = pattern.matcher(text);
		List<Interval> fechas = new ArrayList<Interval>();
		while(m.matches()){
			LocalDate fecha = LocalDate.parse(m.group(1), fechaFmt);
			LocalTime horaInicio = LocalTime.parse(m.group(2),horaFmt); 
			LocalTime horaFin = LocalTime.parse(m.group(3),horaFmt);
			DateTime fechaInicio = fecha.toDateTime(horaInicio);
			DateTime fechaFin = fecha.toDateTime(horaFin); 
			Interval intervalo = new Interval(fechaInicio, fechaFin);
			fechas.add(intervalo);
		}
		return result;
	}

}
