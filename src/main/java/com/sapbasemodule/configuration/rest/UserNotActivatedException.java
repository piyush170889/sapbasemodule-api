package com.sapbasemodule.configuration.rest;

import org.springframework.security.core.AuthenticationException;

public class UserNotActivatedException extends AuthenticationException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7430729908883446443L;

	public UserNotActivatedException(String msg, Throwable t) {
        super(msg, t);
    }

    public UserNotActivatedException(String msg) {
        super(msg);
    }
}
