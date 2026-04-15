package com.ncm.hrms.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ncm.hrms.enums.ProficiencyLevel;

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
public class EmployeeTechnology {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "employee_id", nullable = false)
	@JsonIgnoreProperties({ "technologies", "assignments", "leaveRequests", "password" })
	private Employee employee;

	@ManyToOne
	@JoinColumn(name = "technology_id", nullable = false)
	@JsonIgnoreProperties({ "employees" })
	private Technology technology;

	@Column(nullable = false)
	private Integer experienceInMonths;

	@Enumerated(EnumType.STRING)
	private ProficiencyLevel proficiency;

	@Column(length = 1000)
	private String usageDescription;

	public EmployeeTechnology() {
		super();
	}

	public EmployeeTechnology(Employee employee, Technology technology, int experienceInMonths,
			ProficiencyLevel proficiency) {
		super();
		this.employee = employee;
		this.technology = technology;
		this.experienceInMonths = experienceInMonths;
		this.proficiency = proficiency;
	}

	public EmployeeTechnology(Long id, Employee employee, Technology technology, Integer experienceInMonths,
			ProficiencyLevel proficiency, String usageDescription) {
		super();
		this.id = id;
		this.employee = employee;
		this.technology = technology;
		this.experienceInMonths = experienceInMonths;
		this.proficiency = proficiency;
		this.usageDescription = usageDescription;
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

	public Technology getTechnology() {
		return technology;
	}

	public void setTechnology(Technology technology) {
		this.technology = technology;
	}

	public int getExperienceInMonths() {
		return experienceInMonths;
	}

	public void setExperienceInMonths(int experienceInMonths) {
		this.experienceInMonths = experienceInMonths;
	}

	public ProficiencyLevel getProficiency() {
		return proficiency;
	}

	public void setProficiency(ProficiencyLevel proficiency) {
		this.proficiency = proficiency;
	}

	public String getUsageDescription() {
		return usageDescription;
	}

	public void setUsageDescription(String usageDescription) {
		this.usageDescription = usageDescription;
	}

	public void setExperienceInMonths(Integer experienceInMonths) {
		this.experienceInMonths = experienceInMonths;
	}

}