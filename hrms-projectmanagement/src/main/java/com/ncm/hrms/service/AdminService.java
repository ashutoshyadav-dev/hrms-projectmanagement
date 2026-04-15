package com.ncm.hrms.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ncm.hrms.dto.common.AddressDto;
import com.ncm.hrms.dto.common.EmployeeDropdownDto;
import com.ncm.hrms.dto.request.EmployeeRequest;
import com.ncm.hrms.dto.response.DesignationResponse;
import com.ncm.hrms.dto.response.EmployeeResponse;
import com.ncm.hrms.dto.response.EmployeeTechnologyResponse;
import com.ncm.hrms.entity.Address;
import com.ncm.hrms.entity.Designation;
import com.ncm.hrms.entity.Employee;
import com.ncm.hrms.entity.Shift;
import com.ncm.hrms.enums.EmpStatus;
import com.ncm.hrms.repository.DesignationRepository;
import com.ncm.hrms.repository.EmployeeRepository;
import com.ncm.hrms.repository.ShiftRepository;

import org.springframework.data.jpa.domain.Specification;
import com.ncm.hrms.specification.EmployeeSpecification;

@Service
public class AdminService {

	private EmployeeRepository employeeRepository;
	private DesignationRepository desigRepo;
	private ShiftRepository shiftRepo;
	private final NotificationService notiSer;

	public AdminService(EmployeeRepository employeeRepository, DesignationRepository desigRepo,
			ShiftRepository shiftRepo, NotificationService notiSer) {
		super();
		this.employeeRepository = employeeRepository;
		this.desigRepo = desigRepo;
		this.shiftRepo = shiftRepo;
		this.notiSer = notiSer;
	}

	public List<EmployeeDropdownDto> getEmployeeDropdown() {
		return employeeRepository.findAll().stream().filter(emp -> emp.getStatus() == EmpStatus.ACTIVE)
				.map(emp -> new EmployeeDropdownDto(emp.getId(), emp.getName())).toList();
	}

//	public List<EmployeeResponse> getAllEmployees() {
//        return employeeRepository.findAll()
//                .stream()
//                .map(this::mapEmployeeToResponse)
//                .collect(Collectors.toList());
//    }

//    Basic Pagination without filter
//    public Page<EmployeeResponse> getAllEmployees(Pageable pageable) {
//        return employeeRepository.findAll(pageable)
//                .map(this::mapEmployeeToResponse);
//    }

	// Pagination with filter
	public Page<EmployeeResponse> getAllEmployees(String search, Pageable pageable) {

		Specification<Employee> spec = EmployeeSpecification.globalSearch(search);

		return employeeRepository.findAll(spec, pageable).map(this::mapEmployeeToResponse);
	}

	// -------------------------------------------------------------------------------------------------------------------------

	public EmployeeResponse getEmployeeById(Long id) {
		Employee emp = employeeRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Employee not found"));
		return mapEmployeeToResponse(emp);
	}

	public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {

		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Employee not found"));

		employee.setName(request.getName());
		employee.setEmail(request.getEmail());
		employee.setPhoneNumber(request.getPhoneNumber());
		employee.setEducation(request.getEducation());
		employee.setHireDate(request.getHireDate());
		employee.setStatus(request.getStatus());
		employee.setSameAsPermanent(request.isSameAsPermanent());

		employee.setCurrentAddress(toAddress(request.getCurrentAddress()));
		employee.setPermanentAddress(toAddress(request.getPermanentAddress()));

		if (request.getDesignationId() != null) {
			Designation designation = desigRepo.findById(request.getDesignationId())
					.orElseThrow(() -> new IllegalArgumentException("Designation not found"));
			employee.setDesignation(designation);
		}

		if (request.getShiftId() != null) {
			Shift shift = shiftRepo.findById(request.getShiftId())
					.orElseThrow(() -> new IllegalArgumentException("Shift not found"));
			employee.setShift(shift);
		}

		Employee updatedEmployee = employeeRepository.save(employee);
		try {
			notiSer.createNotification(id, "Your account is " + employee.getStatus() + " by admin");
		} catch (Exception e) {
			System.out.println("Notification failed with employee id" + id);
		}

		return mapEmployeeToResponse(updatedEmployee);
	}

	// ================================================================================================================================

	private EmployeeResponse mapEmployeeToResponse(Employee employee) {
		EmployeeResponse response = new EmployeeResponse();

		response.setId(employee.getId());
		response.setName(employee.getName());
		response.setEmail(employee.getEmail());
		response.setPhoneNumber(employee.getPhoneNumber());
		response.setEducation(employee.getEducation());
		response.setHireDate(employee.getHireDate());
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
