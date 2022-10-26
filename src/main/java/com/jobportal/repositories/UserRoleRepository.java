package com.jobportal.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jobportal.entity.UserRoleEntity;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

	UserRoleEntity findByUserIdAndRoleId(Long userId, Long roleId);

	UserRoleEntity findByRoleId(Long roleId);

	@Query(value = "SELECT * FROM user_role u WHERE u.user_id=:user_id", nativeQuery = true)
	ArrayList<UserRoleEntity> getRolesOfUser(@Param("user_id") Long userId);
}
