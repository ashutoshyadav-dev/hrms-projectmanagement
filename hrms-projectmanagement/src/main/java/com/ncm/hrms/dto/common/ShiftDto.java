package com.ncm.hrms.dto.common;

import java.time.LocalTime;
import java.util.List;

import com.ncm.hrms.entity.Employee;

public class ShiftDto {

	private Long id;
	private String name;

	private LocalTime startTime;

	private LocalTime endTime;

	private LocalTime flexibleStartLimit;

	private Long requiredWorkHours;

	private Boolean flexible;

	private List<Employee> employees;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public LocalTime getFlexibleStartLimit() {
		return flexibleStartLimit;
	}

	public void setFlexibleStartLimit(LocalTime flexibleStartLimit) {
		this.flexibleStartLimit = flexibleStartLimit;
	}

	public Boolean getFlexible() {
		return flexible;
	}

	public void setFlexible(Boolean flexible) {
		this.flexible = flexible;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public Long getRequiredWorkHours() {
		return requiredWorkHours;
	}

	public void setRequiredWorkHours(Long requiredWorkHours) {
		this.requiredWorkHours = requiredWorkHours;
	}

}
