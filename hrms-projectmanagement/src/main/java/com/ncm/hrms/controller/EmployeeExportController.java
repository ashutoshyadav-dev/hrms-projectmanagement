package com.ncm.hrms.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.ncm.hrms.dto.response.EmployeeResponse;
import com.ncm.hrms.service.EmployeeExportService;
import com.ncm.hrms.service.EmployeeService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/export")
public class EmployeeExportController {

	private final EmployeeService employeeService;
	private final EmployeeExportService exportService;

	public EmployeeExportController(EmployeeService employeeService, EmployeeExportService exportService) {
		this.employeeService = employeeService;
		this.exportService = exportService;
	}

	@GetMapping
	public void exportEmployees(@RequestParam(defaultValue = "csv") String format, HttpServletResponse response)
			throws IOException {

		List<EmployeeResponse> employees = employeeService.getAllEmployees();

		switch (format.toLowerCase()) {

		case "csv":
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=employees.csv");
			exportService.exportToCSV(employees, response.getWriter());
			break;

		case "excel":
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment; filename=employees.xlsx");
			exportService.exportToExcel(employees, response);
			break;

		case "pdf":
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=employees.pdf");
			exportService.exportToPDF(employees, response);
			break;

		default:
			throw new IllegalArgumentException("Invalid format");
		}
	}
}