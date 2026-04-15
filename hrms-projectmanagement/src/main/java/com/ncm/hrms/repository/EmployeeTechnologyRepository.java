package com.ncm.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ncm.hrms.entity.Employee;
import com.ncm.hrms.entity.EmployeeTechnology;
import com.ncm.hrms.entity.Technology;

@Repository
public interface EmployeeTechnologyRepository extends JpaRepository<EmployeeTechnology, Long> {

	List<EmployeeTechnology> findByEmployee_Id(Long employeeId);

	boolean existsByEmployee_IdAndTechnology_Id(Long employeeId, Long technologyId);

	EmployeeTechnology findByEmployeeAndTechnology(Employee employee, Technology technology);

	List<EmployeeTechnology> findByEmployee(Employee employee);
}
