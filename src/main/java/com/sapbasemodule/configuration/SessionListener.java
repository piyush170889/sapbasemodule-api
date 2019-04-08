package com.sapbasemodule.configuration;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

public class SessionListener implements HttpSessionListener {

	protected static final Logger LOGGER = Logger.getLogger(SessionListener.class);

	@Override
	public void sessionCreated(HttpSessionEvent se) {

		LOGGER.info("==== Session is created ====");
		se.getSession().setMaxInactiveInterval(600 * 60);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {

		LOGGER.info("==== Session is destroyed ====");
	}

}
