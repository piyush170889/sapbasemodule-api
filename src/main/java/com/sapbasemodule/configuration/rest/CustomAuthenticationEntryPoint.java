package com.sapbasemodule.configuration.rest;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * Returns a 401 error code (Unauthorized) to the client.
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Autowired
	Properties configProperties;
	
	@Autowired
	Properties responseMessageProperties;
	
    private final Logger log = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException ae) throws IOException, ServletException {

        log.info("Pre-authenticated entry point called. Rejecting access");
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
        String errorJson = "{ "
				+ "\"responseMessage\": { "
				+ "\"status\": \""+ HttpStatus.UNAUTHORIZED.toString() + "\""
				+ ",\"message\": \"" + responseMessageProperties.getProperty("616") + "\""
				+ ",\"apiVersion\": \"" + configProperties.getProperty("api.version") 
				+ "\"} "
				+ "}";
		  
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	    response.getOutputStream().println(errorJson);
//	    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, errorJson);
    }
}