package com.ncm.hrms.strategy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ncm.hrms.dto.response.AdminDocumentResponse;
import com.ncm.hrms.entity.Employee;
import com.ncm.hrms.enums.EmpRole;
import com.ncm.hrms.enums.EmpStatus;
import com.ncm.hrms.repository.EmployeeRepository;

@Component("EMPLOYEE")
public class EmployeeUploadStrategy implements FileUploadStrategy {

	private final EmployeeRepository employeeRepo;
	private final PasswordEncoder passwordEncoder;

	public EmployeeUploadStrategy(EmployeeRepository employeeRepo, PasswordEncoder passwordEncoder) {
		this.employeeRepo = employeeRepo;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public AdminDocumentResponse process(MultipartFile file) {

		List<String> errors = new ArrayList<>();
		List<Employee> validList = new ArrayList<>();

		int total = 0;

		try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {

			Sheet sheet = workbook.getSheetAt(0);

			for (Row row : sheet) {

				if (row.getRowNum() == 0)
					continue;

				total++;

				try {

					String name = getString(row.getCell(0));
					String email = getString(row.getCell(1));
					String phone = getString(row.getCell(2));
					String dobStr = getString(row.getCell(3));
					String hireDateStr = getString(row.getCell(4));
					String statusStr = getString(row.getCell(5));
					String roleStr = getString(row.getCell(6));
					String password = getString(row.getCell(7));

					if (name == null || name.isEmpty()) {
						throw new RuntimeException("Name is required");
					}

					if (email == null || email.isEmpty()) {
						throw new RuntimeException("Email is required");
					}

					if (!email.contains("@")) {
						throw new RuntimeException("Invalid email format");
					}

					if (employeeRepo.existsByEmail(email)) {
						throw new RuntimeException("Email already exists");
					}

					LocalDate dob = (dobStr != null) ? LocalDate.parse(dobStr) : null;
					LocalDate hireDate = (hireDateStr != null) ? LocalDate.parse(hireDateStr) : null;

					EmpStatus status = (statusStr != null) ? EmpStatus.valueOf(statusStr.toUpperCase()) : null;

					EmpRole role = null;
					if (roleStr != null && !roleStr.isEmpty()) {
						String formattedRole = roleStr.toUpperCase();

						if (!formattedRole.startsWith("ROLE_")) {
							formattedRole = "ROLE_" + formattedRole;
						}

						role = EmpRole.valueOf(formattedRole);
					}

					Employee emp = new Employee();
					emp.setName(name);
					emp.setEmail(email);
					emp.setPhoneNumber(phone);
					emp.setDateOfBirth(dob);
					emp.setHireDate(hireDate);
					emp.setStatus(status);
					emp.setRole(role);

					emp.setPassword(passwordEncoder.encode(password));

					validList.add(emp);

				} catch (Exception e) {
					errors.add("Row " + (row.getRowNum() + 1) + " - " + e.getMessage());
				}
			}

			employeeRepo.saveAll(validList);

		} catch (Exception e) {
			throw new RuntimeException("File processing failed", e);
		}

		return new AdminDocumentResponse(total, validList.size(), total - validList.size(), errors);
	}

	private String getString(Cell cell) {
		if (cell == null)
			return null;

		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue().trim();

		case NUMERIC:
			return String.valueOf((long) cell.getNumericCellValue());

		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());

		default:
			return cell.toString().trim();
		}
	}
}
