package com.jobportal.configuration;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(CustomAsyncExceptionHandler.class);

	@Override
	public void handleUncaughtException(Throwable ex, Method method, Object... params) {
		logger.error("Unexpected exception occurred in async method: " + method.getName(), ex);
		System.err.println("Method Name::" + method.getName());
		System.err.println("Exception occurred::" + ex);
		String errorMessage = "Unexpected exception occurred in async method: " + method.getName();
		logger.error(errorMessage, ex);
		throw new RuntimeException(errorMessage, ex);
	}

}
