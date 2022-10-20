package com.jobportal.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jobportal.dto.Email;
import com.jobportal.dto.IUserJobList;
import com.jobportal.entity.UserJob;

public interface UserJobRepository extends JpaRepository<UserJob, Long> {

	UserJob findByUserIdAndJobId(Long userId, Long long1);

	Page<IUserJobList> findByOrderByIdDesc(Pageable pageable, Class<IUserJobList> class1);

	@Query(value = "select u.id as  user_id,u.email ,uu.created_by from users u inner join job uu on u.id=uu.created_by", nativeQuery = true)
	List<Email> findAllUserEmail();

	Page<IUserJobList> findJobIdByUserIdOrderByIdDesc(Long userId, Pageable pageable, Class<IUserJobList> class1);

	Page<IUserJobList> findByJobIdOrderByIdDesc(Long jobId, Pageable pageable, Class<IUserJobList> class1);

}
