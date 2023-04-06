package com.jobportal;

import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.jobportal.repositories.UserRepository;
import com.jobportal.serviceImpl.UserServiceImpl;

public class Test {
	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserServiceImpl userService;

	@org.junit.jupiter.api.Test
	@DisplayName("Testing testMethod ....")
	public void testMethod() {
		System.out.println("run ");

	}

//	@org.junit.jupiter.api.Test
//	@DisplayName("Testing setUp ....")
//	public void setUp() {
//		String expect = "HELLO";
//		String actual = "HELLO";
//		System.out.println("Set up");
//
//		Assertions.assertEquals(expect, actual);
//	}

}
