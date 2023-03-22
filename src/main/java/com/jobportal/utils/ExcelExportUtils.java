package com.jobportal.utils;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jobportal.dto.IListUserDto;

public class ExcelExportUtils {
	private XSSFWorkbook workbook;

	private XSSFSheet sheet;
	private List<IListUserDto> list;

	public ExcelExportUtils(XSSFWorkbook workbook, XSSFSheet sheet, List<IListUserDto> list) {
		super();
		this.workbook = workbook;
		this.sheet = sheet;
		this.list = list;

	}

	public ExcelExportUtils() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExcelExportUtils(List<IListUserDto> listUserDtos) {
		super();
		list = listUserDtos;

		workbook = new XSSFWorkbook();

	}

	private void createCell(Row row, int columnCount, Object value, CellStyle style, boolean isBold) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);

		if (value instanceof Long) {
			cell.setCellValue((Long) value);
		} else if (value instanceof String) {
			cell.setCellValue((String) value);
		}

		if (isBold && columnCount == 0) { // apply bold font for first cell
			XSSFFont font = workbook.createFont();
//			font.setFontHeight(14);
//			font.setBold(true);
			style.setFont(font);

			cell.setCellStyle(style);
		} else {
			cell.setCellStyle(style);
		}
	}

	private void createHeaderRow() {
		sheet = workbook.createSheet("Users List");

		Row row = sheet.createRow(0);
		CellStyle style = workbook.createCellStyle();

		createCell(row, 0, "Name", style, true);

		createCell(row, 1, "Email", style, true);
		createCell(row, 2, "Gender", style, true);
		createCell(row, 3, "PhoneNumber", style, true);
		createCell(row, 4, "RoleName", style, true);
		// createCell(row, 5, "Level", style, true);
//		createCell(row, 6, "Designation", style, true);
//		createCell(row, 7, "Department", style, true);
		createCell(row, 5, "CareerAspiration", style, true);
	}

	private void generateUsersData() {

		int rowCount = 1;

		CellStyle style = workbook.createCellStyle();

		XSSFFont font = workbook.createFont();
//		font.setFontHeight(12);
//		style.setFont(font);
		for (IListUserDto iListUserDto : list) {

			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;

			createCell(row, columnCount++, iListUserDto.getName(), style, false);
			createCell(row, columnCount++, iListUserDto.getEmail(), style, false);
//			createCell(row, columnCount++, iListUserDto.getGender(), style, false);
//			createCell(row, columnCount++, iListUserDto.getPhoneNumber(), style, false);
//			createCell(row, columnCount++, iListUserDto.getRoleName(), style, false);
			// createCell(row, columnCount++, iListUserDto.getLevelName(), style, false);
//			createCell(row, columnCount++, iListUserDto.getDepartmentName(), style, false);
//			createCell(row, columnCount++, iListUserDto.getDesignationName(), style, false);
			// createCell(row, columnCount++, iListUserDto.getCareerAspiration(), style,
			// false);

		}
	}

	public void exportDataToExcel(HttpServletResponse response) throws IOException {
		createHeaderRow();
		generateUsersData();
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();

	}

}
