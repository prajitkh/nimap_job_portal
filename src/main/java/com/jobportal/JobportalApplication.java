package com.jobportal;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class JobportalApplication {
	private static Logger log = Logger.getLogger(JobportalApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(JobportalApplication.class, args);
		log.info("Hello");
	}

}
