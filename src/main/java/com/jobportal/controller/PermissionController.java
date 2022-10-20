package com.jobportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.dto.ErrorResponseDto;
import com.jobportal.dto.IListPermissionDto;
import com.jobportal.dto.PermissionRequestDto;
import com.jobportal.dto.SuccessResponseDto;
import com.jobportal.excetpion.ResourceNotFoundException;
import com.jobportal.serviceInterface.PermissionInterface;

@RestController
@RequestMapping("/permission")
public class PermissionController {

	@Autowired
	private PermissionInterface permissionInterface;

	@PostMapping()
	public ResponseEntity<?> addPermission(@RequestBody PermissionRequestDto permissionDto) {

		try {
			permissionDto = this.permissionInterface.addPermission(permissionDto);
			return new ResponseEntity<>(
					new SuccessResponseDto("permission Added Successfully", "Permission Added", "Data added"),
					HttpStatus.ACCEPTED);
		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto("Permission already exist", "Please add new Permission"),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updatePermission(@RequestBody PermissionRequestDto permissionDto, @PathVariable long a) {
		try {
			permissionDto = this.permissionInterface.updatePermission(permissionDto, null);
			return new ResponseEntity<>(
					new SuccessResponseDto(" permission updated sucessfully", "permission updated !!", permissionDto),
					HttpStatus.CREATED);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(new ErrorResponseDto("permission Id Not Found  ", "Something went wrong"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePermission(@PathVariable(name = "id") Long id) throws ResourceNotFoundException {
		try {
			permissionInterface.deletePermission(id);
			return ResponseEntity.ok(new SuccessResponseDto("Deleted Succesfully", "Deleted", id));
		} catch (Exception e) {

			return ResponseEntity.ok(new ErrorResponseDto(e.getMessage(), "Enter Valid Id"));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getPermissionById(@PathVariable Long id) {
		try {
			List<IListPermissionDto> iListPermissionDto = this.permissionInterface.getPermissionById(id);
			return new ResponseEntity<>(new SuccessResponseDto(" Permission:", "Success", iListPermissionDto),
					HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "permission not found "),
					HttpStatus.BAD_REQUEST);

		}
	}

	@GetMapping()
	public ResponseEntity<?> getAllPermission(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "1") String pageNo, @RequestParam(defaultValue = "5") String PageSize) {
		Page<IListPermissionDto> permission = this.permissionInterface.getAllPermissions(search, pageNo, PageSize);

		if (permission.getTotalElements() != 0) {
			return new ResponseEntity<>(new SuccessResponseDto("All permissions", "Success", permission.getContent()),
					HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "Data Not Found"), HttpStatus.NOT_FOUND);
	}
}
