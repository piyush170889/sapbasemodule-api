package com.sapbasemodule.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class Test {

	public static void main(String[] args) throws ParseException {

		Calendar cal = Calendar.getInstance();
		
		Date firstDate = cal.getTime();
		
		cal.add(Calendar.DATE, -4);
		Date secondDate = cal.getTime();
		
		long diff = firstDate.getTime() - secondDate.getTime();
        System.out.println ("Days: " + diff / 1000 / 60 / 60 / 24);
        
	}

}
