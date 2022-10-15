package com.jobportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.dto.SuccessResponseDto;
import com.jobportal.dto.UserJobDto;
import com.jobportal.entity.UserEntity;
import com.jobportal.repositories.UserRepository;
import com.jobportal.serviceInterface.EmailServiceInterface;
import com.jobportal.serviceInterface.UserJobInterface;

@RestController
@RequestMapping("/user-jobs")
public class UserJobController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserJobInterface userJobInterface;
	@Autowired
	private EmailServiceInterface emailServiceInterface;

	@PostMapping
	public ResponseEntity<?> applyMultipleJobs(@RequestBody UserJobDto userJobDto) throws Exception {
		// try {
		Long userEntity = userJobDto.getUserId();
		UserEntity userEntity2 = this.userRepository.findById(userEntity).orElseThrow();
		String email = userEntity2.getEmail();
		this.userJobInterface.applyMultipleJob(userJobDto);

		emailServiceInterface.sendSimpleMessage(email, "Apna jobs", "Job applied sucessfully");
		return new ResponseEntity<>(new SuccessResponseDto("Job applied sucessfully", "Sucess"), HttpStatus.CREATED);
//		} catch (ResourceNotFoundException e) {
//			return new ResponseEntity<>(new ErrorResponseDto("You already applied for this position", "check new jo"),
//					HttpStatus.BAD_REQUEST);
//		}
	}
}
