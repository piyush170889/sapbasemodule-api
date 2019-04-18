package com.sapbasemodule.constants;

public class Constants {
	public static final String APPLICATION_JSON = "application/json";
	
	public static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
	
	public static final String BASE_MAPPING = "/";
	
	public static final String BASE_POINTCUT = "execution(* com.sapbasemodule.*.*.*(..))";
	
	public static final String EXCEPTION_POINTCUT = "execution(* com.sapbasemodule.restcontrollers.*.*(..))";
	
	public static final String ERROR_OCCURED = "Error Occured";
	
	public static final String ERROR_PAGE_URL = "error/error";
	
	public static final String SUCCESS_OK = "OK";

	public static final String ERROR_MSSG_LABEL = "errorMssg";
	
	public static final String ERROR = "ERROR";
	
	public static final String SUCCESS_MSSG_LABEL = "successMssg";
	
	public static final String SMS_GATEWAY_TWILIO="Twilio";
	
	public static final String SMS_GATEWAY_MSSG_91="Mssg91";
	
	public static final byte IS_ACTIVE = 1;
	
	public static final String CREATED_BY_CRONS = "CRON";

	public static final String FIREBASE_DB_URL = "https://geotracker-86b5d.firebaseio.com/";

	public static final String FIREBASE_DB_NODE_NAME = "aaditInfra";

	public static final String JSON_EXT = ".json";

	public static final String URL_ADD_NODE = FIREBASE_DB_URL + FIREBASE_DB_NODE_NAME + JSON_EXT;

	public static final String IST_TIMEZONE = "Asia/Kolkata";

	public static final String UTC_TIMEZONE = "UTC";

	public static final String DB_SERVER_IP = "192.168.0.114";
	
	public static final String DB_NAME = "New Demo";
	
	public static final String DB_USERNM = "sa";
	
	public static final String DB_PASS = "jbsadmin@123";
	
}
