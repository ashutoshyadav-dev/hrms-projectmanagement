package com.ncm.hrms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ncm.hrms.dto.request.EmployeeTechnologyRequest;
import com.ncm.hrms.dto.response.EmployeeTechnologyResponse;
import com.ncm.hrms.entity.Employee;
import com.ncm.hrms.entity.EmployeeTechnology;
import com.ncm.hrms.entity.Technology;
import com.ncm.hrms.repository.EmployeeRepository;
import com.ncm.hrms.repository.EmployeeTechnologyRepository;
import com.ncm.hrms.repository.TechnologyRepository;

@Service
public class EmployeeTechnologyService {

	private final EmployeeTechnologyRepository empTechRepo;
	private final TechnologyRepository techRepo;
	private final EmployeeRepository empRepo;

	public EmployeeTechnologyService(EmployeeTechnologyRepository empTechRepo, TechnologyRepository techRepo,
			EmployeeRepository emprepo) {
		super();
		this.empTechRepo = empTechRepo;
		this.techRepo = techRepo;
		this.empRepo = emprepo;
	}

//	public void addTechnologyByEmail(String email, EmployeeTechnologyRequest request) {
//
//        Employee employee = empRepo.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("Employee not found"));
//
//        Technology technology = techRepo.findById(request.getTechnologyId())
//                .orElseThrow(() -> new RuntimeException("Technology not found"));
//
//        EmployeeTechnology empTech = new EmployeeTechnology();
//        empTech.setEmployee(employee);
//        empTech.setTechnology(technology);
//        empTech.setExperienceInMonths(request.getExperienceInMonths());
//        empTech.setProficiency(request.getProficiency());
//        empTech.setUsageDescription(request.getUsageDescription());
//
//        empTechRepo.save(empTech);
//    }

	// Why I comment above code and write this because above code always creates a
	// new technology assign to employee
	// even if the employee add the same technology multiple times with different
	// experience like if employee add java with 24 months experience
	// then again add java with 15 months experience but the below one never allow
	// duplication but it update the existing values

	public void addTechnologyByEmail(String email, EmployeeTechnologyRequest request) {

		Employee employee = empRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("Employee not found"));

		Technology technology = techRepo.findById(request.getTechnologyId())
				.orElseThrow(() -> new RuntimeException("Technology not found"));

		EmployeeTechnology existing = empTechRepo.findByEmployeeAndTechnology(employee, technology);

		if (existing != null) {

			existing.setExperienceInMonths(request.getExperienceInMonths());
			existing.setProficiency(request.getProficiency());
			existing.setUsageDescription(request.getUsageDescription());

			empTechRepo.save(existing);
			return;
		}

		EmployeeTechnology empTech = new EmployeeTechnology();
		empTech.setEmployee(employee);
		empTech.setTechnology(technology);
		empTech.setExperienceInMonths(request.getExperienceInMonths());
		empTech.setProficiency(request.getProficiency());
		empTech.setUsageDescription(request.getUsageDescription());

		empTechRepo.save(empTech);
	}

	public List<EmployeeTechnologyResponse> getSkillsByEmail(String email) {

		Employee employee = empRepo.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException("Employee not found"));

		List<EmployeeTechnology> empTechList = empTechRepo.findByEmployee(employee);

		List<EmployeeTechnologyResponse> responseList = new ArrayList<>();

		for (EmployeeTechnology et : empTechList) {
			responseList.add(mapToResponse(et));
		}

		return responseList;
	}

	private EmployeeTechnologyResponse mapToResponse(EmployeeTechnology et) {
		EmployeeTechnologyResponse res = new EmployeeTechnologyResponse();

		res.setTechnologyId(et.getTechnology().getId());
		res.setTechnologyName(et.getTechnology().getName());
		res.setExperienceInMonths(et.getExperienceInMonths());
		res.setProficiency(et.getProficiency());
		res.setUsageDescription(et.getUsageDescription());

		return res;
	}

}
