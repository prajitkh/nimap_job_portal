package com.jobportal.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jobportal.dto.IListUserDto;
import com.jobportal.dto.UserPersonalInfoDto;
import com.jobportal.entity.UserEntity;
import com.jobportal.excetpion.ResourceNotFoundException;
import com.jobportal.repositories.UserRepository;
import com.jobportal.serviceInterface.UserInterface;
import com.jobportal.utils.Pagination;

@Service
public class UserServiceImpl implements UserInterface {
	@Autowired
	private UserRepository userRepository;

	@Override
	public void upateUser(UserPersonalInfoDto userPersonalInfoDto, Long id) {
		UserEntity userEntity = this.userRepository.findById(id).orElseThrow();

		userEntity.setEducation(userPersonalInfoDto.getEducation());
		userEntity.setExperience(userPersonalInfoDto.getExperience());
		userEntity.setMobileNumber(userPersonalInfoDto.getMobileNumber());

		this.userRepository.save(userEntity);

	}

	@Override
	public Page<IListUserDto> getAllUsers(String search, String pageNo, String PageSize) {
		Page<IListUserDto> page;
		Pageable pageable = new Pagination().getPagination(pageNo, PageSize);
		if ((search == "") || (search == null) || (search.length() == 0)) {
			page = this.userRepository.findByOrderByIdDesc(pageable, IListUserDto.class);
		}
		page = this.userRepository.findByNameContainingIgnoreCase(search, pageable, IListUserDto.class);

		return page;
	}

	@Override
	public void deleteUser(Long id) throws Exception {

		UserEntity userEntity = this.userRepository.findById(id).orElseThrow(() -> new Exception("user id not found"));
		this.userRepository.delete(userEntity);
	}

	@Async
	@Override
	public void useBulkUpload(MultipartFile file) throws Exception, ResourceNotFoundException {
		List<UserEntity> arrayList = new ArrayList<>();

		XSSFCell cells;

		XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);
		int cell = worksheet.getRow(0).getPhysicalNumberOfCells();

		for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
			UserEntity userEntity = new UserEntity();
			XSSFRow row = worksheet.getRow(i);

			for (int j = 0; j <= cell; j++) {
				cells = row.getCell(j);
				System.err.println("cells " + cells);
				switch (j) {

				case 0:

					if (cells != null) {

						if (cells.getStringCellValue().matches("^[\\w\\s]*$")) {
							userEntity.setName(cells.getStringCellValue());

						} else {
							throw new ResourceNotFoundException("UserName Cell at row " + (i + 1) + " is not valid.");

						}

					} else {
						throw new ResourceNotFoundException("UserName Cell at row " + (i + 1) + " is empty.");
					}

					break;
				case 1:
					if (cells != null) {

						// if (Validator.isValidforEmail(cells.getStringCellValue())) {

						userEntity.setEmail(cells.getStringCellValue());
					} else {
						throw new ResourceNotFoundException("Email Cell at row " + (i + 1) + " is not valid .");
					}

//					} else {
//
//						throw new ResourceNotFoundException("Email Cell at row " + (i + 1) + " is empty.");
//
//					}

					break;

				case 2:
					if (cells != null) {
						userEntity.setEducation(cells.getStringCellValue());
					} else {
						throw new ResourceNotFoundException("Email Cell at row " + (i + 1) + " is not valid .");
					}
					break;

				case 3:
					if (cells != null) {
						String mobileNumber;
						if (cells.getCellType() == CellType.NUMERIC) {
							long mobileNumberValue = (long) cells.getNumericCellValue();
							mobileNumber = String.valueOf(mobileNumberValue);
						} else if (cells.getCellType() == CellType.STRING) {
							mobileNumber = cells.getStringCellValue();
						} else {
							throw new ResourceNotFoundException(
									"Invalid phoneNumber value at row " + (i + 1) + ": " + cells.getStringCellValue());
						}

						userEntity.setMobileNumber(mobileNumber);

					} else {
						userEntity.setMobileNumber("");

					}

					break;
				}

			}
			arrayList.add(userEntity);

		}
		System.err.println("dfgh");
		this.userRepository.saveAll(arrayList);

	}
}
