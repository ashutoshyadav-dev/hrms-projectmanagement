package com.ncm.hrms.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ncm.hrms.dto.request.ModulesRequest;
import com.ncm.hrms.dto.request.ProjectRequest;
import com.ncm.hrms.dto.response.ModulesResponse;
import com.ncm.hrms.dto.response.ProjectResponse;
import com.ncm.hrms.entity.Employee;
import com.ncm.hrms.entity.Modules;
import com.ncm.hrms.entity.Project;
import com.ncm.hrms.enums.ProjectStatus;
import com.ncm.hrms.repository.EmployeeRepository;
import com.ncm.hrms.repository.ModulesRepository;
import com.ncm.hrms.repository.ProjectRepository;

@Service
@Transactional
public class ProjectService {

	private final ProjectRepository projectRepository;
	private final EmployeeRepository employeeRepository;
	private final ModulesRepository modulesRepository;
//    private final EmployeeAssignmentRepository assignmentRepository;

	public ProjectService(ProjectRepository projectRepository, EmployeeRepository employeeRepository,
			ModulesRepository modulesRepository) {
		this.projectRepository = projectRepository;
		this.employeeRepository = employeeRepository;
		this.modulesRepository = modulesRepository;

	}

	public ProjectResponse createProject(ProjectRequest request) {
		if (request.getEndDate().isBefore(request.getStartDate())) {
			throw new IllegalArgumentException("Project end date cannot be before start date");
		}

		Project project = new Project();
		project.setProjectName(request.getProjectName());
		project.setDescription(request.getDescription());
		project.setStartDate(request.getStartDate());
		project.setEndDate(request.getEndDate());
		project.setStatus(ProjectStatus.ACTIVE);

		return mapToProjectResponse(projectRepository.save(project));
	}

	@Transactional(readOnly = true)
	public ProjectResponse getProjectById(Long id) {
		Project project = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
		return mapToProjectResponse(project);
	}

	@Transactional(readOnly = true)
	public List<ProjectResponse> getAllProjects() {
		return projectRepository.findAll().stream().map(this::mapToProjectResponse).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<ProjectResponse> getActiveProjects() {
		return projectRepository.findByStatus(ProjectStatus.ACTIVE).stream().map(this::mapToProjectResponse)
				.collect(Collectors.toList());
	}

//    public EmployeeAssignmentResponse assignEmployeeToProject(Long employeeId,Long projectId,Long moduleId) {
//
//        Employee employee = employeeRepository.findById(employeeId)
//                .orElseThrow(() -> new RuntimeException("Employee not found"));
//
//        Project project = projectRepository.findById(projectId)
//                .orElseThrow(() -> new RuntimeException("Project not found"));
//
//        Modules module = modulesRepository.findById(moduleId)
//                .orElseThrow(() -> new RuntimeException("Module not found"));
//
//        if (!module.getProject().getProjectId().equals(project.getProjectId())) {
//            throw new IllegalStateException("Module does not belong to this project");
//        }
//
//        if (assignmentRepository.existsByEmployeeAndProject(employee, project)) {
//            throw new IllegalStateException("Employee already assigned to this project");
//        }
//
//        EmployeeAssignment assignment = new EmployeeAssignment();
//        assignment.setEmployee(employee);
//        assignment.setProject(project);
//        assignment.setModule(module);
//        assignment.setAssignedDate(LocalDate.now());
//        assignment.setProjectStatus(ProjectStatus.ACTIVE);
//        
//      
//        return mapToAssignmentResponse(assignmentRepository.save(assignment));
//    }

//    public ModulesResponse createModule(ModulesRequest dto) {
//        Project project = projectRepository.findById(dto.getProjectId())
//                .orElseThrow(() -> new RuntimeException("Project not found"));
//
//        Modules module = new Modules();
//        module.setName(dto.getName());
//        module.setProject(project);
//
//        return mapToModulesResponse(modulesRepository.save(module));
//    }

	public ModulesResponse createModule(ModulesRequest dto) {

		if (dto.getEmployeeId() == null) {
			throw new RuntimeException("Employee ID is required");
		}

		if (dto.getProjectId() == null) {
			throw new RuntimeException("Project ID is required");
		}

		Project project = projectRepository.findById(dto.getProjectId())
				.orElseThrow(() -> new RuntimeException("Project not found with ID: " + dto.getProjectId()));

		Employee employee = employeeRepository.findById(dto.getEmployeeId())
				.orElseThrow(() -> new RuntimeException("Employee not found with ID: " + dto.getEmployeeId()));

		Modules module = new Modules();
		module.setName(dto.getName());
		module.setDescription(dto.getDescription());
		module.setProject(project);
		module.setEmployee(employee);

		Modules savedModule = modulesRepository.save(module);

		System.out.println(" Module created successfully with ID: " + savedModule.getId());

		return mapToModulesResponse(savedModule);
	}

	@Transactional(readOnly = true)
	public List<ModulesResponse> getProjectModules(Long projectId) {
		return modulesRepository.findByProjectProjectId(projectId).stream().map(this::mapToModulesResponse)
				.collect(Collectors.toList());
	}

	private ProjectResponse mapToProjectResponse(Project project) {
		ProjectResponse dto = new ProjectResponse();
		dto.setProjectId(project.getProjectId());
		dto.setProjectName(project.getProjectName());
		dto.setDescription(project.getDescription());
		dto.setStartDate(project.getStartDate());
		dto.setEndDate(project.getEndDate());
		dto.setStatus(project.getStatus());
		return dto;
	}

//    private EmployeeAssignmentResponse mapToAssignmentResponse(EmployeeAssignment assignment) {
//        EmployeeAssignmentResponse dto = new EmployeeAssignmentResponse();
//        dto.setEmployeeId(assignment.getEmployee().getId());
//        dto.setEmployeeName(assignment.getEmployee().getName());
//        dto.setProjectId(assignment.getProject().getProjectId());
//        dto.setProjectName(assignment.getProject().getProjectName());
//        dto.setModuleName(
//                assignment.getModule() != null ? assignment.getModule().getName() : null
//        );
//        dto.setAssignedDate(assignment.getAssignedDate());
//        dto.setProjectStatus(assignment.getProjectStatus());
//        return dto;
//    }

	private ModulesResponse mapToModulesResponse(Modules module) {
		ModulesResponse dto = new ModulesResponse();
		dto.setId(module.getId());
		dto.setName(module.getName());
		dto.setDescription(module.getDescription());
		dto.setProjectId(module.getProject().getProjectId());
		dto.setEmployeeId(module.getEmployee().getId());
		return dto;
	}
}
