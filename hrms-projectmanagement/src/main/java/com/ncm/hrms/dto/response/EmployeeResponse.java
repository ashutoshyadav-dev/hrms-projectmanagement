package com.ncm.hrms.dto.response;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.ncm.hrms.entity.Address;
import com.ncm.hrms.entity.EmployeeAssignment;
import com.ncm.hrms.entity.LeaveRequest;
import com.ncm.hrms.entity.Modules;
import com.ncm.hrms.enums.EmpRole;
import com.ncm.hrms.enums.EmpStatus;

public class EmployeeResponse {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String education;
    private EmpRole role;

    private DesignationResponse designation;

    private LocalDate hireDate;
    private LocalDate dateOfBirth;
    private LocalDateTime createdAt;

    private Address currentAddress;
    private Address permanentAddress;
    private boolean sameAsPermanent;

    private EmpStatus status;

    private List<EmployeeTechnologyResponse> technologies;

  private List<EmployeeAssignment> assignments;
    
    private List<LeaveRequest> leaveRequests ;
    private List<Modules> modules;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public DesignationResponse getDesignation() {
		return designation;
	}

	public void setDesignation(DesignationResponse designation) {
		this.designation = designation;
	}

	public LocalDate getHireDate() {
		return hireDate;
	}

	public void setHireDate(LocalDate hireDate) {
		this.hireDate = hireDate;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Address getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(Address currentAddress) {
		this.currentAddress = currentAddress;
	}

	public Address getPermanentAddress() {
		return permanentAddress;
	}

	public void setPermanentAddress(Address permanentAddress) {
		this.permanentAddress = permanentAddress;
	}

	public boolean isSameAsPermanent() {
		return sameAsPermanent;
	}

	public void setSameAsPermanent(boolean sameAsPermanent) {
		this.sameAsPermanent = sameAsPermanent;
	}

	public EmpStatus getStatus() {
		return status;
	}

	public void setStatus(EmpStatus status) {
		this.status = status;
	}

	public List<EmployeeTechnologyResponse> getTechnologies() {
		return technologies;
	}

	public void setTechnologies(List<EmployeeTechnologyResponse> technologies) {
		this.technologies = technologies;
	}

	public List<EmployeeAssignment> getAssignments() {
		return assignments;
	}

	public void setAssignments(List<EmployeeAssignment> assignments) {
		this.assignments = assignments;
	}

	public List<LeaveRequest> getLeaveRequests() {
		return leaveRequests;
	}

	public void setLeaveRequests(List<LeaveRequest> leaveRequests) {
		this.leaveRequests = leaveRequests;
	}

	public List<Modules> getModules() {
		return modules;
	}

	public void setModules(List<Modules> modules) {
		this.modules = modules;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public EmpRole getRole() {
		return role;
	}

	public void setRole(EmpRole role) {
		this.role = role;
	}


    
	
    
    
}

