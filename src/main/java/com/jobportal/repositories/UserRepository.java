package com.jobportal.repositories;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jobportal.dto.IListUserDto;
import com.jobportal.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	Page<IListUserDto> findByOrderByIdDesc(Pageable pageable, Class<IListUserDto> class1);

	Page<IListUserDto> findByNameContainingIgnoreCase(String search, Pageable pageable, Class<IListUserDto> class1);

	UserEntity findByEmail(String emailString);

	@Query(value = "select u.id as Id, u.user_name as Name,u.email as Email from users u ", nativeQuery = true)
	List<IListUserDto> getUsers(HttpServletResponse response, Class<IListUserDto> class1);

}
