package com.ncm.hrms.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ncm.hrms.dto.common.AddressDto;
import com.ncm.hrms.dto.request.EmployeeRequest;
import com.ncm.hrms.dto.response.DesignationResponse;
import com.ncm.hrms.dto.response.EmployeeResponse;
import com.ncm.hrms.dto.response.EmployeeTechnologyResponse;
import com.ncm.hrms.entity.Address;
import com.ncm.hrms.entity.Designation;
import com.ncm.hrms.entity.Employee;
import com.ncm.hrms.repository.DesignationRepository;
import com.ncm.hrms.repository.EmployeeRepository;

@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;
	private final DesignationRepository designationRepository;

	public EmployeeService(EmployeeRepository employeeRepository, DesignationRepository designationRepository) {
		this.employeeRepository = employeeRepository;
		this.designationRepository = designationRepository;

	}

	@Transactional
	public EmployeeResponse updateEmployeeProfile(Authentication authentication, EmployeeRequest request) {

		String email = authentication.getName();

		Employee employee = employeeRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException("Employee not found"));

		mapRequestToEmployee(employee, request);

		return mapEmployeeToResponse(employee);
	}

	@Transactional(readOnly = true)
	public EmployeeResponse getEmployeeProfile(Authentication authentication) {

		String email = authentication.getName();

		Employee employee = employeeRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException("Employee not found"));

		System.out.println("Hire Date from entity: " + employee.getHireDate());

		return mapEmployeeToResponse(employee);
	}

	@Transactional(readOnly = true)
	public List<EmployeeResponse> getAllEmployees() {

		return employeeRepository.findAll().stream().map(this::mapEmployeeToResponse).collect(Collectors.toList());
	}

	private void mapRequestToEmployee(Employee employee, EmployeeRequest request) {

		employee.setName(request.getName());
		employee.setPhoneNumber(request.getPhoneNumber());
		employee.setEducation(request.getEducation());
		employee.setHireDate(request.getHireDate());
		employee.setDateOfBirth(request.getDateOfBirth());
		employee.setSameAsPermanent(request.isSameAsPermanent());

		if (request.getStatus() != null) {
			employee.setStatus(request.getStatus());
		}

		if (request.getDesignationId() != null) {
			Designation designation = designationRepository.findById(request.getDesignationId())
					.orElseThrow(() -> new IllegalArgumentException("Designation not found"));
			employee.setDesignation(designation);
		}

		employee.setCurrentAddress(toAddress(request.getCurrentAddress()));
		employee.setPermanentAddress(toAddress(request.getPermanentAddress()));

		employee.setAssignments(request.getAssignments());
		employee.setLeaveRequests(request.getLeaveRequests());
	}

	private EmployeeResponse mapEmployeeToResponse(Employee employee) {

		EmployeeResponse response = new EmployeeResponse();

		response.setId(employee.getId());
		response.setName(employee.getName());
		response.setEmail(employee.getEmail());
		response.setPhoneNumber(employee.getPhoneNumber());
		response.setEducation(employee.getEducation());
		response.setHireDate(employee.getHireDate());
		response.setDateOfBirth(employee.getDateOfBirth());
		response.setCreatedAt(employee.getCreatedAt());
		response.setStatus(employee.getStatus());
		response.setSameAsPermanent(employee.isSameAsPermanent());

		response.setCurrentAddress(employee.getCurrentAddress());
		response.setPermanentAddress(employee.getPermanentAddress());

		if (employee.getDesignation() != null) {
			DesignationResponse dr = new DesignationResponse();
			dr.setId(employee.getDesignation().getId());
			dr.setTitle(employee.getDesignation().getTitle());
			response.setDesignation(dr);
		}

		response.setTechnologies(employee.getTechnologies() == null ? Collections.emptyList()
				: employee.getTechnologies().stream().map(t -> {
					EmployeeTechnologyResponse tr = new EmployeeTechnologyResponse();
					tr.setTechnologyId(t.getTechnology().getId());
					tr.setTechnologyName(t.getTechnology().getName());
					tr.setExperienceInMonths(t.getExperienceInMonths());
					tr.setProficiency(t.getProficiency());
					tr.setUsageDescription(t.getUsageDescription());
					return tr;
				}).collect(Collectors.toList()));

		response.setAssignments(employee.getAssignments());
		response.setLeaveRequests(employee.getLeaveRequests());

		return response;
	}

	private Address toAddress(AddressDto dto) {
		if (dto == null)
			return null;

		Address address = new Address();
		address.setAddressLine1(dto.getAddressLine1());
		address.setAddressLine2(dto.getAddressLine2());
		address.setCity(dto.getCity());
		address.setDistrict(dto.getDistrict());
		address.setState(dto.getState());
		address.setCountry(dto.getCountry());
		address.setPincode(dto.getPincode());
		return address;
	}
}
