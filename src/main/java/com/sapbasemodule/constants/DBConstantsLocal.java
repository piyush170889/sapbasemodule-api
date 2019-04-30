package com.sapbasemodule.constants;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("local")
@Component
public class DBConstantsLocal {
	
	public static final String DB_SERVER_IP = "localhost";
	
	public static final String DB_NAME = "Jagtap";
	
	public static final String DB_USERNM = "sa";
	
	public static final String DB_PASS = "replete@123";

}
