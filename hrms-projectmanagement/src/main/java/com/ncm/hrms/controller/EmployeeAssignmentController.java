package com.ncm.hrms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ncm.hrms.dto.request.EmployeeAssignmentRequest;
import com.ncm.hrms.dto.response.EmployeeAssignmentResponse;
import com.ncm.hrms.service.EmployeeAssignmentService;

@RestController
@RequestMapping("/employee-assignments")
public class EmployeeAssignmentController {

	private final EmployeeAssignmentService empAssignSer;

	public EmployeeAssignmentController(EmployeeAssignmentService empAssignSer) {
		super();
		this.empAssignSer = empAssignSer;
	}

	@PostMapping
	public ResponseEntity<?> saveEmpAssignProject(@RequestBody EmployeeAssignmentRequest empAssignReq) {

		empAssignSer.saveEmpAssign(empAssignReq);

		return ResponseEntity.ok("Project added");
	}

	@GetMapping
	public ResponseEntity<List<EmployeeAssignmentResponse>> getAllEmpAssignProject() {
		return ResponseEntity.ok(empAssignSer.getAllEmpAssignProject());
	}

	@GetMapping("/byEmail")
	public ResponseEntity<List<EmployeeAssignmentResponse>> getEmpAssignProjectByEmail(Authentication authentication) {
		String email = authentication.getName();
		return ResponseEntity.ok(empAssignSer.getEmpAssignProjectByEmail(email));
	}

	@GetMapping("/assignments")
	public ResponseEntity<List<EmployeeAssignmentResponse>> getAllEmpAssignByModuleAndProject(
			@RequestParam Long projectId, @RequestParam Long moduleId) {
		return ResponseEntity.ok(empAssignSer.getAllEmpAssignProjectAndModule(projectId, moduleId));

	}

}
