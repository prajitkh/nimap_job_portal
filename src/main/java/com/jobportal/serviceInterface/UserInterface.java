package com.jobportal.serviceInterface;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jobportal.dto.IListUserDto;
import com.jobportal.dto.UserPersonalInfoDto;

@Service
public interface UserInterface {

	void upateUser(UserPersonalInfoDto personalInfoDto, Long id);

	Page<IListUserDto> getAllUsers(String search, String pageNo, String PageSize);

	void deleteUser(Long id) throws Exception;

	void useBulkUpload(MultipartFile file) throws Exception;

	List<IListUserDto> exportUserToExcel(HttpServletResponse response) throws IOException;
}
