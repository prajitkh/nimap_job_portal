package com.jobportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.entity.UserJob;

public interface UserJobRepository extends JpaRepository<UserJob, Long> {

	UserJob findByUserIdAndJobId(Long userId, Long long1);

//	UserJob findByUserIdAndJobId(Long userId, Long long1);

	// void saveAll(ArrayList<UserJob> jobs);

}
