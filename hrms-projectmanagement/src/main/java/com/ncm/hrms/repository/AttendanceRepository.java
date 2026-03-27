package com.ncm.hrms.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ncm.hrms.entity.Attendance;




@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

	Optional<Attendance> findByEmployeeIdAndDate(Long employeeId, LocalDate date);

	
	@Query("SELECT a FROM Attendance a WHERE a.employee.id = ?1 AND a.date BETWEEN ?2 AND ?3")
	List<Attendance> findAllAttendanceByEmployeeIdAndRange(Long empId, LocalDate start, LocalDate end);

	

}
