package com.ncm.hrms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.ncm.hrms.dto.request.EmployeeRequest;
import com.ncm.hrms.dto.request.EmployeeTechnologyRequest;
import com.ncm.hrms.dto.response.EmployeeResponse;
import com.ncm.hrms.dto.response.EmployeeTechnologyResponse;
import com.ncm.hrms.service.EmployeeService;
import com.ncm.hrms.service.EmployeeTechnologyService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	private final EmployeeService employeeService;
	private final EmployeeTechnologyService empTechSer;
	private final EmployeeTechnologyService employeeTechnologyService;

	public EmployeeController(EmployeeService employeeService, EmployeeTechnologyService empTechSer,
			EmployeeTechnologyService employeeTechnologyService) {
		super();
		this.employeeService = employeeService;
		this.empTechSer = empTechSer;
		this.employeeTechnologyService = employeeTechnologyService;
	}

	@GetMapping("/profile")
	public EmployeeResponse getProfile(Authentication authentication) {
		return employeeService.getEmployeeProfile(authentication);
	}

	@PutMapping("/upProfile")
	public EmployeeResponse updateProfile(Authentication authentication, @RequestBody EmployeeRequest request) {
		return employeeService.updateEmployeeProfile(authentication, request);
	}

	@GetMapping("/all")
	public List<EmployeeResponse> getAllEmployees() {
		return employeeService.getAllEmployees();
	}

	@PostMapping("/empTechnology")
	public ResponseEntity<?> addSkill(@RequestBody EmployeeTechnologyRequest request, Authentication authentication) {

		String email = authentication.getName();

		empTechSer.addTechnologyByEmail(email, request);

		return ResponseEntity.ok("Skill added");
	}

	@GetMapping("/getEmpTechnology")
	public List<EmployeeTechnologyResponse> getMySkills(Authentication auth) {

		String email = auth.getName();

		return employeeTechnologyService.getSkillsByEmail(email);
	}

}
