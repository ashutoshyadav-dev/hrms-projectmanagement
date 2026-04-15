package com.ncm.hrms.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.ncm.hrms.dto.request.ModulesRequest;
import com.ncm.hrms.dto.request.ProjectRequest;
import com.ncm.hrms.dto.response.ModulesResponse;
import com.ncm.hrms.dto.response.ProjectResponse;
import com.ncm.hrms.service.ProjectService;

@RestController
@RequestMapping("/projects")
@CrossOrigin
public class ProjectController {

	private final ProjectService projectService;

	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}

	@PostMapping
	public ProjectResponse createProject(@RequestBody ProjectRequest request) {
		return projectService.createProject(request);
	}

	@GetMapping("/{id}")
	public ProjectResponse getProjectById(@PathVariable Long id) {
		return projectService.getProjectById(id);
	}

	@GetMapping
	public List<ProjectResponse> getAllProjects() {
		return projectService.getAllProjects();
	}

	@GetMapping("/active")
	public List<ProjectResponse> getActiveProjects() {
		return projectService.getActiveProjects();
	}

//    @PostMapping("/assign")
//    public EmployeeAssignmentResponse assignEmployee(
//            @RequestParam Long employeeId,
//            @RequestParam Long projectId,
//            @RequestParam Long moduleId) {
//
//        return projectService.assignEmployeeToProject(employeeId, projectId, moduleId);
//    }

	@PostMapping("/modules")
	public ModulesResponse createModule(@RequestBody ModulesRequest dto) {
		return projectService.createModule(dto);
	}

	@GetMapping("/{projectId}/modules")
	public List<ModulesResponse> getProjectModules(@PathVariable Long projectId) {
		return projectService.getProjectModules(projectId);
	}
}
