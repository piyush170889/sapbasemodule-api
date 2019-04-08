package com.sapbasemodule.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnAuthorizedException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6979525714252965272L;

}
