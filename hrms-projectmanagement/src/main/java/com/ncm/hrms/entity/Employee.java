package com.ncm.hrms.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ncm.hrms.enums.EmpRole;
import com.ncm.hrms.enums.EmpStatus;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Past;

@Entity
@Table(name = "employees")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	@JsonIgnore
	private String password;

	@Column(length = 15)
	private String phoneNumber;

	private String education;

	@Past
	private LocalDate dateOfBirth;

	private LocalDateTime lastLogin;

	@ManyToOne
	@JsonIgnoreProperties({ "employees", "hibernateLazyInitializer", "handler" })
	private Designation designation;

	@Column(name = "hire_date")
	private LocalDate hireDate;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@Embedded
	private Address currentAddress;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "addressLine1", column = @Column(name = "current_address_line1")),
			@AttributeOverride(name = "addressLine2", column = @Column(name = "current_address_line2")),
			@AttributeOverride(name = "city", column = @Column(name = "current_city")),
			@AttributeOverride(name = "district", column = @Column(name = "current_district")),
			@AttributeOverride(name = "state", column = @Column(name = "current_state")),
			@AttributeOverride(name = "country", column = @Column(name = "current_country")),
			@AttributeOverride(name = "pincode", column = @Column(name = "current_pincode")) })
	private Address permanentAddress;

	@Column(nullable = false)
	private boolean sameAsPermanent;

	@Enumerated(EnumType.STRING)
	private EmpStatus status;

	@Enumerated(EnumType.STRING)
	private EmpRole role;

	@OneToMany(mappedBy = "employee", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = false)
	@JsonIgnoreProperties({ "employee" })
	private List<EmployeeTechnology> technologies = new ArrayList<>();

	@OneToMany(mappedBy = "employee", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = false)
	@JsonIgnoreProperties({ "employee" })
	private List<EmployeeAssignment> assignments = new ArrayList<>();

	@OneToMany(mappedBy = "employee", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = false)
	@JsonIgnoreProperties({ "employee" })
	private List<LeaveRequest> leaveRequests = new ArrayList<>();

	@ManyToOne
	@JsonIgnoreProperties({ "employees" })
	private Shift shift;

	@OneToMany(mappedBy = "employee", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JsonIgnoreProperties({ "employee" })
	private List<Attendance> attendances = new ArrayList<>();

	@OneToMany(mappedBy = "employee", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JsonIgnoreProperties({ "employee" })
	private List<AttendanceLog> attendanceLogs = new ArrayList<>();

	@OneToMany(mappedBy = "employee", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JsonIgnoreProperties({ "employee" })
	private List<Document> document = new ArrayList<>();

	public Employee() {
		super();
	}

	public Employee(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public Employee(String name, String email, String password, String phoneNumber, String education,
			Designation designation, LocalDate hireDate, Address currentAddress, Address permanentAddress,
			boolean sameAsPermanent, EmpStatus status, EmpRole role, List<EmployeeTechnology> technologies,
			List<EmployeeAssignment> assignments, List<LeaveRequest> leaveRequests) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.education = education;
		this.designation = designation;
		this.hireDate = hireDate;
		this.currentAddress = currentAddress;
		this.permanentAddress = permanentAddress;
		this.sameAsPermanent = sameAsPermanent;
		this.status = status;
		this.role = role;
		this.technologies = technologies;
		this.assignments = assignments;
		this.leaveRequests = leaveRequests;
	}

	public Employee(String name, String email, String password, String phoneNumber, String education,
			Designation designation, LocalDate hireDate, Address currentAddress, Address permanentAddress,
			boolean sameAsPermanent, EmpStatus status) {

		this.name = name;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.education = education;
		this.designation = designation;
		this.hireDate = hireDate;
		this.currentAddress = currentAddress;
		this.permanentAddress = permanentAddress;
		this.sameAsPermanent = sameAsPermanent;
		this.status = status;
	}

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

	public EmpRole getRole() {
		return role;
	}

	public void setRole(EmpRole role) {
		this.role = role;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public Designation getDesignation() {
		return designation;
	}

	public void setDesignation(Designation designation) {
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

	public List<EmployeeTechnology> getTechnologies() {
		return technologies;
	}

	public void setTechnologies(List<EmployeeTechnology> technologies) {
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

	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Shift getShift() {
		return shift;
	}

	public void setShift(Shift shift) {
		this.shift = shift;
	}

	public List<Attendance> getAttendances() {
		return attendances;
	}

	public void setAttendances(List<Attendance> attendances) {
		this.attendances = attendances;
	}

	public List<AttendanceLog> getAttendanceLogs() {
		return attendanceLogs;
	}

	public void setAttendanceLogs(List<AttendanceLog> attendanceLogs) {
		this.attendanceLogs = attendanceLogs;
	}

}
