package com.ncm.hrms.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ncm.hrms.entity.Employee;
import com.ncm.hrms.entity.LeaveRequest;
import com.ncm.hrms.enums.LeaveStatus;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

	List<LeaveRequest> findByEmployee_Id(Long employeeId);

	Optional<Employee> findByEmployee(Employee employee);

	Optional<Employee> findByLeaveStatus(LeaveStatus pending);

	List<LeaveRequest> findByEmployeeAndLeaveStatus(Employee employee, LeaveStatus status);

	LeaveRequest findByEmployee_IdAndAppliedDate(Long employeeId, LocalDate appliedDate);

}
