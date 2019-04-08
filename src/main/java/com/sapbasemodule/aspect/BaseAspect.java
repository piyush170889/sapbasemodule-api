package com.sapbasemodule.aspect;

import java.util.Calendar;
import java.util.Properties;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.sapbasemodule.constants.Constants;
import com.sapbasemodule.model.BaseWrapper;
import com.sapbasemodule.model.ResponseMessage;

@Aspect
@Component
public class BaseAspect {

	@Autowired
	Properties responseMessageProperties;
	
	@Autowired
	Properties configProperties;
	
	private static final Logger logger = LoggerFactory.getLogger(BaseAspect.class);
	
	@Pointcut(Constants.BASE_POINTCUT)
	public void basePointCut() { }
	
	@Pointcut(Constants.EXCEPTION_POINTCUT)
	public void exceptionPointCut() { }
	
	@Before("basePointCut()")
	public void beforeMethod(JoinPoint jp) {
	}
	
	@After("basePointCut()")
	public void doAfterMethodExecution(JoinPoint jp) {
	}
	
	@AfterThrowing(pointcut="basePointCut()", throwing="exception")
	public void doAfterThrowingException(Exception exception) {
	}
	
	@Around("exceptionPointCut()")
	public Object doAroundExecutionForApp(ProceedingJoinPoint pjp){
		Long startTimestamp = Calendar.getInstance().getTimeInMillis();
		BaseWrapper response = new BaseWrapper();
		try 
		{
			Object o = pjp.proceed();
			if(o instanceof byte[])
			{
				return (byte[])o;
			}else
			{
				response = (BaseWrapper) o;
			}
		} catch (Throwable e) 
		{
			System.out.println("2");
			e.printStackTrace();
			String eMssg = e.getMessage();
			String mssg = null;
			Integer.parseInt(eMssg);
			mssg = responseMessageProperties.getProperty(eMssg);	
			response.setResponseMessage(new ResponseMessage(HttpStatus.PAYMENT_REQUIRED.toString(), 
			mssg, configProperties.getProperty("api.version")));
			return new ResponseEntity<Object>(response, HttpStatus.PAYMENT_REQUIRED);
		}
		if(null != response && null == response.getResponseMessage()) {
			response.setResponseMessage(new ResponseMessage(HttpStatus.OK.toString(), Constants.SUCCESS_OK,
				configProperties.getProperty("api.version")));
		}
		logger.info("Execution Time In Millisecs : " + (Calendar.getInstance().getTimeInMillis() - startTimestamp));
		return response;
	}
	
}
