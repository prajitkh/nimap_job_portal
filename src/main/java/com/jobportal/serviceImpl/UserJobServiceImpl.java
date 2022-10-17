package com.jobportal.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jobportal.dto.Email;
import com.jobportal.dto.IListUserDto;
import com.jobportal.dto.IUserJobList;
import com.jobportal.dto.UserJobDto;
import com.jobportal.entity.JobEntity;
import com.jobportal.entity.UserEntity;
import com.jobportal.entity.UserJob;
import com.jobportal.excetpion.ResourceNotFoundException;
import com.jobportal.repositories.JobReposiotry;
import com.jobportal.repositories.UserJobRepository;
import com.jobportal.repositories.UserRepository;
import com.jobportal.repositories.UserRoleRepository;
import com.jobportal.serviceInterface.EmailServiceInterface;
import com.jobportal.serviceInterface.UserJobInterface;
import com.jobportal.utils.Pagination;

@Service
public class UserJobServiceImpl implements UserJobInterface {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserJobInterface userJobInterface;
	@Autowired
	private EmailServiceInterface emailServiceInterface;

	@Autowired
	private UserJobRepository userJobRepository;
	@Autowired
	private JobReposiotry jobReposiotry;

	@Autowired
	private EmailServiceImpl emailServiceImpl;
	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public void applyJobs(Long id, UserJobDto userJobDto) throws Exception {

		UserEntity userEntity = this.userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Enter valid user id"));

		JobEntity job = this.jobReposiotry.findById(userJobDto.getJobId())
				.orElseThrow(() -> new ResourceNotFoundException("Enter job id not found"));

		UserJob userJob = this.userJobRepository.findByUserIdAndJobId(id, userJobDto.getJobId());

		if (userJob != null) {

			throw new ResourceNotFoundException("Already applied ");
		}

		UserJob userJobs = new UserJob();
		userJobs.setUser(userEntity);
		userJobs.setJob(job);

		this.userJobRepository.save(userJobs);

		UserEntity userEntity2 = this.userRepository.findById(id).orElseThrow();
		String email = userEntity2.getEmail();

		JobEntity jobEntity = this.jobReposiotry.findById(userJobDto.getJobId())
				.orElseThrow(() -> new ResourceNotFoundException("enter valid job id "));

		Email userEntity1 = this.userJobRepository.findAllUserEmail().get(0);

		emailServiceInterface.sendSimpleMessage(email, "Apna jobs",
				"Job applied sucessfully for " + jobEntity.getJobTitle());

		emailServiceInterface.sendSimpleMessage(userEntity1.getEmail(), "Apna jobs", "Candidate Applied for job"
				+ "Job title    " + jobEntity.getJobTitle() + "Candidate Email " + userEntity2.getEmail());
	}

	@Override
	public Page<IUserJobList> getUsersJobs(Long id, String pageNo, String pageSize) {
		Page<IUserJobList> iUserJobList;
		Pageable pageable = new Pagination().getPagination(pageNo, pageSize);

		if (id == null) {
			iUserJobList = this.userJobRepository.findByOrderByIdDesc(pageable, IUserJobList.class);
		} else {
			iUserJobList = this.userJobRepository.findByUserId(id, pageable, IListUserDto.class);
		}
		return iUserJobList;
	}
}
