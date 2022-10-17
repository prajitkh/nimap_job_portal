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

import com.jobportal.dto.IUserJobList;
import com.jobportal.dto.SuccessResponseDto;
import com.jobportal.dto.UserJobDto;
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
		// try {
		// Long userEntity = CommanFuncationl

		this.userJobInterface.applyJobs(userId, userJobDto);

		return new ResponseEntity<>(new SuccessResponseDto("Job applied sucessfully", "Sucess"), HttpStatus.CREATED);
//		} catch (ResourceNotFoundException e) {
//			return new ResponseEntity<>(new ErrorResponseDto("You already applied for this position", "check new jo"),
//					HttpStatus.BAD_REQUEST);
//		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getAlLUsers(@RequestParam(defaultValue = "") Long id,
			@RequestParam(defaultValue = "1") String PageNo, @RequestParam(defaultValue = "5") String PageSize) {

		Page<IUserJobList> iListUserDto = this.userJobInterface.getUsersJobs(id, PageNo, PageSize);

		return new ResponseEntity<>(new SuccessResponseDto("Job", "Sucess", iListUserDto.getContent()),
				HttpStatus.CREATED);

	}
}
