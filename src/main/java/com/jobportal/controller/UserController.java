package com.jobportal.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jobportal.configuration.KafkaProducer;
import com.jobportal.dto.ErrorResponseDto;
import com.jobportal.dto.IListUserDto;
import com.jobportal.dto.SuccessResponseDto;
import com.jobportal.dto.UserPersonalInfoDto;
import com.jobportal.serviceInterface.UserInterface;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserInterface userInterface;
	@Autowired
	private KafkaProducer kafkaProducer;

	@PatchMapping("/{id}")
	public ResponseEntity<?> upddateUserInformation(@RequestBody UserPersonalInfoDto personalInfoDto,
			@PathVariable Long id) {
		try {
			this.userInterface.upateUser(personalInfoDto, id);
			return new ResponseEntity<>(new SuccessResponseDto("update user information sucessfully", "success"),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto("enter valid information", "check valid data"),
					HttpStatus.OK);
		}
	}

	// @PreAuthorize("hasRole('getAlLUsers')")
	@GetMapping
	public ResponseEntity<?> getAlLUsers(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "1") String PageNo, @RequestParam(defaultValue = "5") String PageSize) {

		Page<IListUserDto> iListUserDto = this.userInterface.getAllUsers(search, PageNo, PageSize);
		return new ResponseEntity<>(new SuccessResponseDto("All users", "success", iListUserDto.getContent()),
				HttpStatus.OK);

	}

	@PreAuthorize("hasRole('deleteUserById')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
		try {
			userInterface.deleteUser(id);
			return new ResponseEntity<>(new SuccessResponseDto("User deleted succefully", "Deleted"), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "Please enter valid user id"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/upload")
	public ResponseEntity<?> uploadUser(MultipartFile file) {
		try {
			userInterface.useBulkUpload(file);
			return new ResponseEntity<>(new SuccessResponseDto("User uplaod succefully", "o"), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "Please enter valid inforamtion"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/export")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/vnd.ms-excel");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=Users_Information.xlsx";
		response.setHeader(headerKey, headerValue);

		userInterface.exportUserToExcel(response);
	}

	@PostMapping("/kafka")
	public ResponseEntity<?> uploadUser(@RequestBody String request) {
		try {
			kafkaProducer.addUsersToUsersMainTable(request);
			return new ResponseEntity<>(new SuccessResponseDto("User uplaod succefully", "o"), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "Please enter valid inforamtion"),
					HttpStatus.BAD_REQUEST);
		}
	}
}