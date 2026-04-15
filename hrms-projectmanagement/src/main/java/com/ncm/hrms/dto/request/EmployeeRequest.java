
package com.ncm.hrms.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.ncm.hrms.dto.common.AddressDto;
import com.ncm.hrms.entity.EmployeeAssignment;
import com.ncm.hrms.entity.LeaveRequest;
import com.ncm.hrms.enums.EmpStatus;

public class EmployeeRequest {

	private String name;
	private String email;
	private String password;
	private String phoneNumber;
	private String education;

	private Long designationId;

	private LocalDate hireDate;
	private LocalDate dateOfBirth;

	private AddressDto currentAddress;
	private AddressDto permanentAddress;

	private boolean sameAsPermanent;

	private EmpStatus status;

	private List<EmployeeTechnologyRequest> technologies;

	private List<EmployeeAssignment> assignments;

	private List<LeaveRequest> leaveRequests;

	private Long shiftId;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Long getDesignationId() {
		return designationId;
	}

	public void setDesignationId(Long designationId) {
		this.designationId = designationId;
	}

	public LocalDate getHireDate() {
		return hireDate;
	}

	public void setHireDate(LocalDate hireDate) {
		this.hireDate = hireDate;
	}

	public AddressDto getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(AddressDto currentAddress) {
		this.currentAddress = currentAddress;
	}

	public AddressDto getPermanentAddress() {
		return permanentAddress;
	}

	public void setPermanentAddress(AddressDto permanentAddress) {
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

	public List<EmployeeTechnologyRequest> getTechnologies() {
		return technologies;
	}

	public void setTechnologies(List<EmployeeTechnologyRequest> technologies) {
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

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Long getShiftId() {
		return shiftId;
	}

	public void setShiftId(Long shiftId) {
		this.shiftId = shiftId;
	}

}
