package com.jobportal.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jobportal.dto.IListPermissionDto;
import com.jobportal.entity.PermissionEntity;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {
	Page<IListPermissionDto> findByOrderByIdDesc(Pageable pagingPageable, Class<IListPermissionDto> class1);

	Page<IListPermissionDto> findByactionNameContaining(String search, Pageable pagingPageable,
			Class<IListPermissionDto> class1);

	List<PermissionEntity> findByIdIn(List<Long> permissionId);

	List<IListPermissionDto> findByOrderByIdDesc(Class<IListPermissionDto> class1);

	List<IListPermissionDto> findById(Long id, Class<IListPermissionDto> class1);

	@Query(value = "select p.action_name from permissions p left join role_permission rp on rp.permission_id=p.id\r\n"
			+ "left join user_role ur on ur.role_id=rp.role_id\r\n"
			+ "left join users  u on u.id=ur.user_id where u.id=:id", nativeQuery = true)
	ArrayList<String> findbyUserIdPermission(Long id);

}
