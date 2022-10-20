package com.jobportal.serviceInterface;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.jobportal.dto.IUserJobList;
import com.jobportal.dto.UserJobDto;

@Service
public interface UserJobInterface {

	void applyJobs(Long id, UserJobDto userJobDto) throws Exception;

	Page<IUserJobList> getCandidateAppliedJobList(Long userId, String search, String pageNo, String pageSize);

	Page<IUserJobList> getUsersAppliedJobList(Long jobId, String pageNumber, String pageSize);

	Page<IUserJobList> getUsersJobs(Long jobId, String pageNumber, String pageSize);

}
