package com.jobportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.dto.ErrorResponseDto;
import com.jobportal.dto.IUserJobList;
import com.jobportal.dto.SuccessResponseDto;
import com.jobportal.dto.UserJobDto;
import com.jobportal.excetpion.ResourceNotFoundException;
import com.jobportal.serviceInterface.UserJobInterface;
import com.jobportal.utils.CommanFuncation;

@RestController
@RequestMapping("/user-jobs")
public class UserJobController {

	@Autowired
	private UserJobInterface userJobInterface;

	@PostMapping
	public ResponseEntity<?> applyMultipleJobs(@RequestAttribute(CommanFuncation.CUSTUM_ATTRIBUTE_USER_ID) Long userId,
			@RequestBody UserJobDto userJobDto) throws Exception {
		try {

			this.userJobInterface.applyJobs(userId, userJobDto);

			return new ResponseEntity<>(new SuccessResponseDto("Job applied sucessfully", "Sucess"),
					HttpStatus.CREATED);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(new ErrorResponseDto("You already applied for this position", "failed"),
					HttpStatus.BAD_REQUEST);
		}
	}

// See a list of jobs they have applied
	@GetMapping("/userId")
	public ResponseEntity<?> getAppliedJobList(@RequestAttribute(CommanFuncation.CUSTUM_ATTRIBUTE_USER_ID) Long userId,
			@RequestParam(defaultValue = "1") String pageNo, @RequestParam(defaultValue = "25") String pageSize) {

		Page<IUserJobList> page = this.userJobInterface.getCandidateAppliedJobList(userId, pageSize, pageNo, pageSize);

		return new ResponseEntity<>(new SuccessResponseDto("Job list", "Successfull", page.getContent()),
				HttpStatus.OK);

	}

//View jobs applied only to the specific jobs posted by the recruite
	@GetMapping("/reqId")
	public ResponseEntity<?> getUserAppliedJobList(
			@RequestAttribute(CommanFuncation.CUSTUM_ATTRIBUTE_USER_ID) Long userId,
			@RequestParam(defaultValue = "1") String PageNo, @RequestParam(defaultValue = "5") String PageSize) {

		Page<IUserJobList> iListUserDto = this.userJobInterface.getUsersAppliedJobList(userId, PageNo, PageSize);

		return new ResponseEntity<>(new SuccessResponseDto("User job list", "Sucess", iListUserDto.getContent()),
				HttpStatus.OK);

	}

	@GetMapping()
	public ResponseEntity<?> getUserJobs(@RequestAttribute(CommanFuncation.CUSTUM_ATTRIBUTE_USER_ID) Long userId,
			@RequestParam(defaultValue = "1") String PageNo, @RequestParam(defaultValue = "5") String PageSize) {

		Page<IUserJobList> iListUserDto = this.userJobInterface.getUsersJobs(userId, PageNo, PageSize);

		return new ResponseEntity<>(
				new SuccessResponseDto("Get All User job applied list", "Sucess", iListUserDto.getContent()),
				HttpStatus.OK);

	}
}
