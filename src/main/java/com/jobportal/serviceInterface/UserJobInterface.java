package com.jobportal.serviceInterface;

import org.springframework.stereotype.Service;

import com.jobportal.dto.UserJobDto;

@Service
public interface UserJobInterface {
	void applyMultipleJob(UserJobDto userJobDto) throws Exception;
}
