package com.sapbasemodule.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class Test {

	public static void main(String[] args) throws ParseException {

//		int a = Integer.parseInt("70000");
		double a = 7000.212456789D;
		CommonUtility commonUtility = new CommonUtility();
		System.out.println(commonUtility.round(a, 2));
	}

}
