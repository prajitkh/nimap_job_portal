package com.jobportal.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jobportal.dto.IListPermissionDto;
import com.jobportal.dto.PermissionRequestDto;
import com.jobportal.entity.PermissionEntity;
import com.jobportal.excetpion.ResourceNotFoundException;
import com.jobportal.repositories.PermissionRepository;
import com.jobportal.repositories.RolePermissionRepository;
import com.jobportal.repositories.RoleRepository;
import com.jobportal.serviceInterface.PermissionInterface;
import com.jobportal.utils.Pagination;

@Service
public class PermissionServiceImpl implements PermissionInterface {

	@Autowired
	PermissionRepository permissionRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	private RolePermissionRepository rolePermissionRepository;

	// add permission
	@Override
	public PermissionRequestDto addPermission(PermissionRequestDto dto) {
		PermissionEntity permissionEntity = new PermissionEntity();
		permissionEntity.setActionName(dto.getActionName());
		permissionEntity.setBaseUrl(dto.getBaseUrl());
		permissionEntity.setDescription(dto.getDescription());
		permissionEntity.setMethod(dto.getMethod());
		permissionRepository.save(permissionEntity);
		return dto;

	}

	// get all permission
	@Override
	public Page<IListPermissionDto> getAllPermissions(String search, String pageNo, String pageSize) {

		Pageable pagingPageable = new Pagination().getPagination(pageNo, pageSize);

		if (search == "" || search == null || search.length() == 0) {
			return this.permissionRepository.findByOrderByIdDesc(pagingPageable, IListPermissionDto.class);
		}

		return this.permissionRepository.findByactionNameContaining(search, pagingPageable, IListPermissionDto.class);

	}

	// get permission by id
	@Override
	public List<IListPermissionDto> getPermissionById(Long id) {

		List<IListPermissionDto> iListPermissionDto;

		return iListPermissionDto = this.permissionRepository.findById(id, IListPermissionDto.class);

	}

// update permission  by id
	@Override
	public PermissionRequestDto updatePermission(PermissionRequestDto dto, Long id) throws ResourceNotFoundException {
		PermissionEntity permissionEntity = this.permissionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Permission not found."));
		permissionEntity.setActionName(dto.getActionName());
		permissionEntity.setBaseUrl(dto.getBaseUrl());
		permissionEntity.setDescription(dto.getDescription());
		permissionEntity.setMethod(dto.getMethod());
		this.permissionRepository.save(permissionEntity);

		return dto;
	}

	// delete permission by id
	@Override
	public void deletePermission(Long id) {
		permissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Permission not found."));
		permissionRepository.deleteById(id);

	}

	@Override
	public List<IListPermissionDto> getAllPermissions() {
		List<IListPermissionDto> iListPermissionDtos = this.permissionRepository
				.findByOrderByIdDesc(IListPermissionDto.class);
		return iListPermissionDtos;
	}

}
