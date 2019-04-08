package com.sapbasemodule.utils;

import java.text.ParseException;

public class Test {

	public static void main(String[] args) throws ParseException {

		/*
		 * DateFormat dfIst = new SimpleDateFormat("dd MMM yyyy hh:mm a");
		 * dfIst.setTimeZone(TimeZone.getTimeZone("IST"));
		 * 
		 * String currentTs = dfIst.format(new Date());
		 */

		System.out.println(new CommonUtility().getUtcTsFromIst("2019-02-10 13:37:00"));
	}

}
