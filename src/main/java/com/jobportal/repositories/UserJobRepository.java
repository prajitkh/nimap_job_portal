package com.jobportal.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jobportal.dto.Email;
import com.jobportal.dto.IListUserDto;
import com.jobportal.dto.IUserJobList;
import com.jobportal.entity.UserJob;

public interface UserJobRepository extends JpaRepository<UserJob, Long> {

	UserJob findByUserIdAndJobId(Long userId, Long long1);

	Page<IUserJobList> findByOrderByIdDesc(Pageable pageable, Class<IUserJobList> class1);

	Page<IUserJobList> findByUserId(Long id, Pageable pageable, Class<IListUserDto> class1);

	// UserJob findByUserId(Long userId);

//	UserJob findByUserIdAndJobId(Long userId, Long long1);

	// void saveAll(ArrayList<UserJob> jobs);
	// @Query(value = "select u.id as user_id,u.user_name ,uj.job_id from users u
	// inner join user_job uj on u.id=uj.user_id where user_id=:user_id",
	// nativeQuery = true)
	// List<IUserJobList> findByUserIdOrderByIdDesc(@Param("user_id") Long user_id);
	// @Query(value = "select u.id as user_id,u.email ,uu.created_by as creates from
	// users u inner join job uu on u.id=uu.created_by ", nativeQuery = true)

	@Query(value = "select u.id as  user_id,u.email ,uu.created_by from users u inner join job uu on u.id=uu.created_by", nativeQuery = true)
	List<Email> findAllUserEmail();
}
