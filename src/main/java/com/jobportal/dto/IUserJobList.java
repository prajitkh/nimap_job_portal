package com.jobportal.dto;

public interface IUserJobList {
	Long user_name();

//	Long getJob_Id();
//
	String getEmail();
//	public Long getId();

	// public IListUserDto getUser();

	public IListJobDto getJob();
}
