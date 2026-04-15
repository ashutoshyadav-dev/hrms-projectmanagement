package com.ncm.hrms.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ncm.hrms.dto.request.EmployeeAssignmentRequest;
import com.ncm.hrms.dto.response.EmployeeAssignmentResponse;
import com.ncm.hrms.entity.Employee;
import com.ncm.hrms.entity.EmployeeAssignment;
import com.ncm.hrms.entity.Modules;
import com.ncm.hrms.entity.Project;
import com.ncm.hrms.enums.ProjectStatus;
import com.ncm.hrms.repository.EmployeeAssignmentRepository;
import com.ncm.hrms.repository.EmployeeRepository;
import com.ncm.hrms.repository.ModulesRepository;
import com.ncm.hrms.repository.ProjectRepository;

@Service
public class EmployeeAssignmentService {

	private static final Logger log = LoggerFactory.getLogger(EmployeeAssignmentService.class);

	private final EmployeeAssignmentRepository empAssRepo;
	private final EmployeeRepository empRepo;
	private final ProjectRepository proRepo;
	private final ModulesRepository modRepo;
	private final NotificationService notiSer;

	public EmployeeAssignmentService(EmployeeAssignmentRepository empAssRepo, EmployeeRepository empRepo,
			ProjectRepository proRepo, ModulesRepository modRepo, NotificationService notiSer) {
		super();
		this.empAssRepo = empAssRepo;
		this.empRepo = empRepo;
		this.proRepo = proRepo;
		this.modRepo = modRepo;
		this.notiSer = notiSer;
	}

	public EmployeeAssignmentResponse saveEmpAssign(EmployeeAssignmentRequest empAssReq) {

		EmployeeAssignment assignment = mapRequestToEntity(empAssReq);

		EmployeeAssignment saved = empAssRepo.save(assignment);
		try {
			notiSer.createNotification(assignment.getEmployee().getId(),
					"Project " + assignment.getProject().getProjectName() + "with module "
							+ assignment.getModule().getName() + "assign to you");
		} catch (Exception e) {
			log.error("Notification failed for employeeId {}", assignment.getEmployee().getId(), e);
		}

		return mapToResponse(saved);
	}

	public List<EmployeeAssignmentResponse> getEmpAssignProjectByEmail(String email) {

		List<EmployeeAssignment> assignments = empAssRepo.findByEmployee_Email(email);

		return assignments.stream().map(this::mapToResponse).toList();
	}

	public List<EmployeeAssignmentResponse> getAllEmpAssignProject() {
		List<EmployeeAssignment> listEmpAssign = empAssRepo.findAll();
		List<EmployeeAssignmentResponse> listEmpAssigRes = new ArrayList<>();
		for (EmployeeAssignment empAs : listEmpAssign) {
			listEmpAssigRes.add(mapToResponse(empAs));
		}
		return listEmpAssigRes;
	}

	public List<EmployeeAssignmentResponse> getAllEmpAssignProjectAndModule(Long projectId, Long moduleId) {
		List<EmployeeAssignment> listEmpAssign = empAssRepo.findByProject_ProjectIdAndModules_Id(projectId, moduleId);
		List<EmployeeAssignmentResponse> listEmpAssigRes = new ArrayList<>();
		for (EmployeeAssignment empAs : listEmpAssign) {
			listEmpAssigRes.add(mapToResponse(empAs));
		}
		return listEmpAssigRes;
	}

	public EmployeeAssignment mapRequestToEntity(EmployeeAssignmentRequest empAssReq) {

		Employee employee = empRepo.findById(empAssReq.getEmployeeId())
				.orElseThrow(() -> new RuntimeException("Employee not found"));

		Project project = proRepo.findById(empAssReq.getProjectId())
				.orElseThrow(() -> new RuntimeException("Project not found"));

		Modules module = modRepo.findById(empAssReq.getModuleId())
				.orElseThrow(() -> new RuntimeException("Module not found"));

		EmployeeAssignment empAssign = new EmployeeAssignment();

		empAssign.setEmployee(employee);
		empAssign.setProject(project);
		empAssign.setModule(module);
		empAssign.setAssignedDate(LocalDate.now());
		empAssign.setProjectStatus(ProjectStatus.ACTIVE);

		return empAssign;
	}

	private EmployeeAssignmentResponse mapToResponse(EmployeeAssignment assignment) {

		EmployeeAssignmentResponse dto = new EmployeeAssignmentResponse();

		dto.setEmployeeId(assignment.getEmployee().getId());
		dto.setEmployeeName(assignment.getEmployee().getName());
		dto.setProjectId(assignment.getProject().getProjectId());
		dto.setProjectName(assignment.getProject().getProjectName());
		dto.setModuleName(assignment.getModule().getName());
		dto.setAssignedDate(assignment.getAssignedDate());
		dto.setProjectStatus(assignment.getProjectStatus());
		dto.setAssignmentId(assignment.getId());
		dto.setModuleId(assignment.getModule().getId());
		dto.setHoursWorked(assignment.getHoursWorked());
		dto.setProjectStartDate(assignment.getProject().getStartDate());
		dto.setProjectEndDate(assignment.getProject().getEndDate());
		dto.setProjectDescription(assignment.getProject().getDescription());
		dto.setModuledescription(assignment.getModule().getDescription());

		return dto;
	}

}
