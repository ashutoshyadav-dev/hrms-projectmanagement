package com.ncm.hrms.controller;

import java.util.List;

//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ncm.hrms.dto.common.EmployeeDropdownDto;
import com.ncm.hrms.dto.request.EmployeeRequest;
import com.ncm.hrms.dto.response.EmployeeResponse;
import com.ncm.hrms.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

//    @GetMapping("/employees")
//    public List<EmployeeResponse> getAllEmployees() {
//        return adminService.getAllEmployees();
//    }

	// Basic pagination without filter

//    @GetMapping("/employees")
//    public Page<EmployeeResponse> getAllEmployees(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "5") int size,
//            @RequestParam(defaultValue = "id") String sortBy,
//            @RequestParam(defaultValue = "asc") String direction
//    ) {
//        Sort sort = direction.equalsIgnoreCase("asc")
//                ? Sort.by(sortBy).ascending()
//                : Sort.by(sortBy).descending();
//
//        Pageable pageable = PageRequest.of(page, size, sort);
//
//        return adminService.getAllEmployees(pageable);
//    }

	// Pagination with filter
	@GetMapping("/employees")
	public Page<EmployeeResponse> getAllEmployees(

			@RequestParam(required = false) String search,

			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "asc") String direction) {

		Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable pageable = PageRequest.of(page, size, sort);

		return adminService.getAllEmployees(search, pageable);
	}

	// _________________________________________________________________________________________________________

	@GetMapping("/empById/{id}")
	public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {
		return ResponseEntity.ok(adminService.getEmployeeById(id));
	}

	@PutMapping("/updateEmp/{id}")
	public EmployeeResponse updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequest request) {
		return adminService.updateEmployee(id, request);
	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	@GetMapping("/employees/dropdown")
	public List<EmployeeDropdownDto> getEmployeeDropdown() {
		return adminService.getEmployeeDropdown();
	}
}
