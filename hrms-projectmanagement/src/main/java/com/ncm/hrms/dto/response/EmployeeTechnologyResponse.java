package com.ncm.hrms.dto.response;

import com.ncm.hrms.enums.ProficiencyLevel;

public class EmployeeTechnologyResponse {

	private Long technologyId;
	private String technologyName;
	private Long employeeId;
	private Integer experienceInMonths;
	private ProficiencyLevel proficiency;
	private String usageDescription;

	public EmployeeTechnologyResponse() {
		super();
	}

	public EmployeeTechnologyResponse(Long technologyId, String technologyName, Long employeeId,
			Integer experienceInMonths, ProficiencyLevel proficiency, String usageDescription) {
		super();
		this.technologyId = technologyId;
		this.technologyName = technologyName;
		this.employeeId = employeeId;
		this.experienceInMonths = experienceInMonths;
		this.proficiency = proficiency;
		this.usageDescription = usageDescription;
	}

	public Long getTechnologyId() {
		return technologyId;
	}

	public void setTechnologyId(Long technologyId) {
		this.technologyId = technologyId;
	}

	public Integer getExperienceInMonths() {
		return experienceInMonths;
	}

	public void setExperienceInMonths(Integer experienceInMonths) {
		this.experienceInMonths = experienceInMonths;
	}

	public ProficiencyLevel getProficiency() {
		return proficiency;
	}

	public void setProficiency(ProficiencyLevel proficiency) {
		this.proficiency = proficiency;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getUsageDescription() {
		return usageDescription;
	}

	public void setUsageDescription(String usageDescription) {
		this.usageDescription = usageDescription;
	}

	public String getTechnologyName() {
		return technologyName;
	}

	public void setTechnologyName(String technologyName) {
		this.technologyName = technologyName;
	}

}
