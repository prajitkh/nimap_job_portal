package com.jobportal.serviceInterface;

import java.util.List;

import org.springframework.data.domain.Page;

import com.jobportal.dto.IListPermissionDto;
import com.jobportal.dto.PermissionRequestDto;
import com.jobportal.excetpion.ResourceNotFoundException;

public interface PermissionInterface {

	PermissionRequestDto addPermission(PermissionRequestDto dto);

	void deletePermission(Long id);

	public Page<IListPermissionDto> getAllPermissions(String search, String pageNo, String pageSize);

	public List<IListPermissionDto> getPermissionById(Long id);

	public PermissionRequestDto updatePermission(PermissionRequestDto dto, Long id) throws ResourceNotFoundException;

	public List<IListPermissionDto> getAllPermissions();

}
