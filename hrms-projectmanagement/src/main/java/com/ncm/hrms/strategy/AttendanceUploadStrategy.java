package com.ncm.hrms.strategy;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ncm.hrms.dto.response.AdminDocumentResponse;
import com.ncm.hrms.entity.Attendance;
import com.ncm.hrms.entity.Employee;
import com.ncm.hrms.enums.AttendanceStatus;
import com.ncm.hrms.repository.AttendanceRepository;
import com.ncm.hrms.repository.EmployeeRepository;

@Component("ATTENDANCE")
public class AttendanceUploadStrategy implements FileUploadStrategy {

	private final AttendanceRepository attendanceRepo;
	private final EmployeeRepository employeeRepo;

	public AttendanceUploadStrategy(AttendanceRepository attendanceRepo, EmployeeRepository employeeRepo) {
		this.attendanceRepo = attendanceRepo;
		this.employeeRepo = employeeRepo;
	}

	@Override
	public AdminDocumentResponse process(MultipartFile file) {

		List<String> errors = new ArrayList<>();
		List<Attendance> validList = new ArrayList<>();

		int total = 0;

		try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {

			Sheet sheet = workbook.getSheetAt(0);

			for (Row row : sheet) {

				if (row.getRowNum() == 0)
					continue;

				total++;

				try {
					String empIdStr = getCellValue(row.getCell(0));
					String dateStr = getCellValue(row.getCell(1));
					String checkInStr = getCellValue(row.getCell(2));
					String checkOutStr = getCellValue(row.getCell(3));
					String checkInIp = getCellValue(row.getCell(4));
					String checkOutIp = getCellValue(row.getCell(5));
					String statusStr = getCellValue(row.getCell(6));
					String lateStr = getCellValue(row.getCell(7));
					String earlyExitStr = getCellValue(row.getCell(8));

					if (empIdStr == null || empIdStr.isEmpty()) {
						throw new RuntimeException("Employee ID missing");
					}

					Long empId = getLongValue(row.getCell(0));
					Employee emp = employeeRepo.findById(empId)
							.orElseThrow(() -> new RuntimeException("Employee not found"));

					LocalDate date = LocalDate.parse(dateStr);
					LocalDateTime checkIn = LocalDateTime.parse(checkInStr);
					LocalDateTime checkOut = LocalDateTime.parse(checkOutStr);

					AttendanceStatus status = AttendanceStatus.valueOf(statusStr.toUpperCase());

					Boolean late = Boolean.parseBoolean(lateStr);
					Boolean earlyExit = Boolean.parseBoolean(earlyExitStr);

					if (attendanceRepo.existsByEmployeeIdAndDate(empId, date)) {
						errors.add("Row " + (row.getRowNum() + 1) + " - Attendance already exists for employee " + empId
								+ " on " + date);
						continue;
					}

					Duration workingHours = null;
					if (checkIn != null && checkOut != null) {
						workingHours = Duration.between(checkIn, checkOut);
					}

					Attendance att = new Attendance();
					att.setEmployee(emp);
					att.setDate(date);
					att.setCheckInTime(checkIn);
					att.setCheckOutTime(checkOut);
					att.setCheckInIp(checkInIp);
					att.setCheckOutIp(checkOutIp);
					att.setStatus(status);
					att.setLate(late);
					att.setEarlyExit(earlyExit);
					att.setWorkingHours(workingHours);

					validList.add(att);

				} catch (Exception e) {
					errors.add("Row " + (row.getRowNum() + 1) + " - " + e.getMessage());
				}
			}

			attendanceRepo.saveAll(validList);

		} catch (Exception e) {
			throw new RuntimeException("File processing failed", e);
		}

		return new AdminDocumentResponse(total, validList.size(), total - validList.size(), errors);
	}

	private String getCellValue(Cell cell) {
		return (cell == null) ? null : cell.toString().trim();
	}

	private Long getLongValue(Cell cell) {
		if (cell == null)
			return null;

		if (cell.getCellType() == CellType.NUMERIC) {
			return (long) cell.getNumericCellValue();
		}

		return Long.parseLong(cell.getStringCellValue().trim());
	}
}