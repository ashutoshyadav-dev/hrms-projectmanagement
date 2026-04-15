package com.ncm.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ncm.hrms.entity.Employee;
import com.ncm.hrms.entity.EmployeeAssignment;
import com.ncm.hrms.entity.Project;

@Repository
public interface EmployeeAssignmentRepository extends JpaRepository<EmployeeAssignment, Long> {

	List<EmployeeAssignment> findByProject_ProjectId(Long projectId);

	List<EmployeeAssignment> findByEmployee(Employee employee);

	List<EmployeeAssignment> findByProject(Project project);

	@Query("SELECT ea FROM EmployeeAssignment ea WHERE ea.employee.id = :employeeId AND ea.project.id = :projectId")
	List<EmployeeAssignment> findByEmployeeIdAndProjectId(@Param("employeeId") Long employeeId,
			@Param("projectId") Long projectId);

	boolean existsByEmployeeAndProject(Employee employee, Project project);

	List<EmployeeAssignment> findByEmployee_Email(String email);

//List<EmployeeAssignment> findByProjectIdAndModuleId(Long projectId, Long moduleId);

	List<EmployeeAssignment> findByProject_ProjectIdAndModules_Id(Long projectId, Long moduleId);

}
