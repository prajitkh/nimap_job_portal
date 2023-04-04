package com.jobportal;

import static org.junit.Assert.assertArrayEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

public class Test {
	@org.junit.jupiter.api.Test
	@DisplayName("Testing testMethod ....")
	public void testMethod() {
		System.out.println("run ");

		assertArrayEquals("", "");
	}

	@BeforeEach
	@DisplayName("Testing setUp ....")
	public void setUp() {
		System.out.println("Set up");
	}
}
