package com.ncm.hrms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ncm.hrms.entity.Employee;
import com.ncm.hrms.enums.EmpStatus;

//we use here JpaSpecification because It allows dynamic filtering
//We can filter by name, phone, email, status optionally
//we can use param also but not dynamic

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

	Optional<Employee> findByEmail(String email);

	boolean existsByEmail(String email);

	Optional<Employee> findByNameContainingIgnoreCase(String name);

	List<Employee> findByStatus(EmpStatus status);

	@Query("SELECT e FROM Employee e LEFT JOIN FETCH e.technologies LEFT JOIN FETCH e.assignments WHERE e.id = :id")
	Optional<Employee> findByIdWithDetails(@Param("id") Long id);

	@Query("SELECT e FROM Employee e LEFT JOIN FETCH e.technologies LEFT JOIN FETCH e.assignments WHERE e.email = :email")
	Optional<Employee> findByEmailWithDetails(@Param("email") String email);

}
