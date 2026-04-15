package com.ncm.hrms.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ncm.hrms.enums.ProjectStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class EmployeeAssignment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "employee_id", nullable = false)
	@JsonIgnoreProperties({ "assignments", "leaveRequests", "technologies", "password" })
	private Employee employee;

	@ManyToOne
	@JoinColumn(name = "project_id", nullable = false)
	@JsonIgnoreProperties({ "assignments", "modules" })
	private Project project;

	@ManyToOne
	@JoinColumn(name = "module_id", nullable = false)
	@JsonIgnoreProperties({ "employee", "project" })
	private Modules modules;

	@Column(name = "assigned_date")
	private LocalDate assignedDate;

	@Column(name = "hours_worked", precision = 10, scale = 2)
	private BigDecimal hoursWorked = BigDecimal.ZERO;

	@Enumerated(EnumType.STRING)
	private ProjectStatus projectStatus = ProjectStatus.ACTIVE;

	public EmployeeAssignment() {
		super();
	}

	public EmployeeAssignment(Employee employee, Project project, Modules modules, LocalDate assignedDate,
			BigDecimal hoursWorked, ProjectStatus projectStatus) {
		super();
		this.employee = employee;
		this.project = project;
		this.modules = modules;
		this.assignedDate = assignedDate;
		this.hoursWorked = hoursWorked;
		this.projectStatus = projectStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Modules getModule() {
		return modules;
	}

	public void setModule(Modules module) {
		this.modules = module;
	}

	public LocalDate getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(LocalDate assignedDate) {
		this.assignedDate = assignedDate;
	}

	public BigDecimal getHoursWorked() {
		return hoursWorked;
	}

	public void setHoursWorked(BigDecimal hoursWorked) {
		this.hoursWorked = hoursWorked;
	}

	public ProjectStatus getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(ProjectStatus projectStatus) {
		this.projectStatus = projectStatus;
	}

}
