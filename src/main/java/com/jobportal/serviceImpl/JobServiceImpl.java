package com.jobportal.serviceImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jobportal.dto.IListJobDto;
import com.jobportal.dto.JobDto;
import com.jobportal.entity.JobEntity;
import com.jobportal.repositories.JobReposiotry;
import com.jobportal.serviceInterface.JobInterface;
import com.jobportal.utils.Pagination;

@Service
public class JobServiceImpl implements JobInterface {
	@Autowired
	private JobReposiotry jobReposiotry;
	@PersistenceContext
	private EntityManager manager;

	@Override
	public void addJobs(Long id, JobDto jobDto) {
		System.err.println(1);
		JobEntity jobEntity = new JobEntity();
		jobEntity.setJobTitle(jobDto.getJobTitle());
		jobEntity.setDescription(jobDto.getDescription());
		jobEntity.setCreatedBy(id);
		this.jobReposiotry.save(jobEntity);

	}

	@Override
	public Page<IListJobDto> getAllJobs(String search, String pagNumber, String pageSize) {
		Page<IListJobDto> iJobListDto;

		System.err.println(11);
		Pageable pageable = new Pagination().getPagination(pagNumber, pageSize);
		System.err.println(12);
		if ((search == "") || (search == null) || (search.length() == 0)) {
			System.err.println(13);
			iJobListDto = this.jobReposiotry.findByOrderByIdDesc(pageable, IListJobDto.class);
		}
		System.err.println(14);
		iJobListDto = this.jobReposiotry.findByJobTitleContainingIgnoreCase(search, pageable, IListJobDto.class);
		System.err.println(15);
		return iJobListDto;
	}

	public List<JobEntity> getAllUserEmail() {
		List<JobEntity> employees = manager.createNamedQuery("findUserEmail", JobEntity.class).getResultList();
		return employees;
	}

	@Override
	public void deleteJob(Long id) throws Exception {
		JobEntity jobEntity = this.jobReposiotry.findById(id).orElseThrow(() -> new Exception("job id not found"));
		this.jobReposiotry.delete(jobEntity);
	}
}
