package com.jobportal.serviceInterface;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.jobportal.dto.IUserJobList;
import com.jobportal.dto.UserJobDto;

@Service
public interface UserJobInterface {
//	List<IUserJobList> getUsersJobs(Long user_id);

	Page<IUserJobList> getUsersJobs(Long id, String pageNo, String pageSize);

	void applyJobs(Long id, UserJobDto userJobDto) throws Exception;
}
