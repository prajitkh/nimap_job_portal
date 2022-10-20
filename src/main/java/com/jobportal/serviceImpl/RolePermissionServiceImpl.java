package com.jobportal.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jobportal.dto.IListRolePermission;
import com.jobportal.dto.RolePermissionDto;
import com.jobportal.entity.PermissionEntity;
import com.jobportal.entity.RoleEntity;
import com.jobportal.entity.RolePermissionEntity;
import com.jobportal.excetpion.ResourceNotFoundException;
import com.jobportal.repositories.PermissionRepository;
import com.jobportal.repositories.RolePermissionRepository;
import com.jobportal.repositories.RoleRepository;
import com.jobportal.serviceInterface.RolePermissionInterface;
import com.jobportal.utils.Pagination;

@Service
public class RolePermissionServiceImpl implements RolePermissionInterface {
	@Autowired
	private RolePermissionRepository rolePermissionRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PermissionRepository permissionRepository;

	@Override
	public RolePermissionDto addRolePermission(RolePermissionDto rolePermissionDto) {
		RolePermissionEntity rolePermissionEntity = this.rolePermissionRepository
				.findByRolePermissionId(rolePermissionDto.getRoleId(), rolePermissionDto.getPermissionId());

		if (rolePermissionEntity == null) {
			RoleEntity roleEntity = this.roleRepository.findById(rolePermissionDto.getRoleId())
					.orElseThrow(() -> new ResourceNotFoundException("Role Not Found"));

			PermissionEntity permissionEntity = permissionRepository.findById(rolePermissionDto.getPermissionId())
					.orElseThrow(() -> new ResourceNotFoundException("permission Not Found"));

			ArrayList<RolePermissionEntity> rolePermissionEntityList = new ArrayList<RolePermissionEntity>();

			RolePermissionEntity newRolePermissionEntity = new RolePermissionEntity();
			newRolePermissionEntity.setRole(roleEntity);
			newRolePermissionEntity.setPermission(permissionEntity);
			rolePermissionEntityList.add(newRolePermissionEntity);
			rolePermissionRepository.saveAll(rolePermissionEntityList);

		} else {
			throw new ResourceNotFoundException("resource already exists");

		}

		return null;
	}

	@Override
	public RolePermissionDto updateRoleOrPermission(Long id, RolePermissionDto rolePermissionDto) {

		RolePermissionEntity rolePermissionEntity = this.rolePermissionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not Found"));

		RoleEntity roleEntity = this.roleRepository.findById(rolePermissionDto.getRoleId())
				.orElseThrow(() -> new ResourceNotFoundException("Role Does Not Found"));

		PermissionEntity permissionEntity = this.permissionRepository.findById(rolePermissionDto.getPermissionId())
				.orElseThrow(() -> new ResourceNotFoundException("Permission Not Found"));

		rolePermissionEntity.setRole(roleEntity);
		rolePermissionEntity.setPermission(permissionEntity);
		rolePermissionRepository.save(rolePermissionEntity);

		return rolePermissionDto;
	}

	@Override
	public void deleteRolePermission(Long id) {
		this.rolePermissionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not Found For this id"));
		this.rolePermissionRepository.deleteById(id);

	}

	@Override
	public List<IListRolePermission> getRolePermissionById(Long id) {
		List<IListRolePermission> iListRolePermission;
		return iListRolePermission = this.rolePermissionRepository.findById(id, IListRolePermission.class);
	}

	@Override
	public Page<IListRolePermission> getAllRolePermission(String search, String pageNumber, String pageSize) {
		Pageable paging = new Pagination().getPagination(pageNumber, pageSize);

		Page<IListRolePermission> iListRolePermission;
		iListRolePermission = rolePermissionRepository.findByOrderByIdAsc(paging, IListRolePermission.class);

		return iListRolePermission;
	}
}
