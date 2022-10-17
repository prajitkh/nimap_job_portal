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
import com.jobportal.dto.IListJobDto;
import com.jobportal.dto.JobDto;
import com.jobportal.dto.SuccessResponseDto;
import com.jobportal.serviceInterface.JobInterface;
import com.jobportal.utils.CommanFuncation;

@RestController
@RequestMapping("/jobs")
public class JobController {
	@Autowired
	private JobInterface jobInterface;

	@PostMapping()
	public ResponseEntity<?> addJobs(@RequestAttribute(CommanFuncation.CUSTUM_ATTRIBUTE_USER_ID) Long id,
			@RequestBody JobDto jobDto) {
		try {

			this.jobInterface.addJobs(id, jobDto);

			return new ResponseEntity<>(new SuccessResponseDto("job added succefully", "Success", jobDto),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "Please enter valid job data"),
					HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping
	public ResponseEntity<?> getAlLJobs(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "1") String PageNo, @RequestParam(defaultValue = "5") String PageSize) {
		System.err.println(1);
		Page<IListJobDto> iListUserDto = this.jobInterface.getAllJobs(search, PageNo, PageSize);
		System.err.println(2);
		return new ResponseEntity<>(new SuccessResponseDto("All jobs", "Success", iListUserDto.getContent()),
				HttpStatus.OK);

	}

}
