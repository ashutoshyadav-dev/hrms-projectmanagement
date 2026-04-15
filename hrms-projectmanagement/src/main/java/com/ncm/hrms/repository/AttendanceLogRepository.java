package com.ncm.hrms.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ncm.hrms.entity.AttendanceLog;

@Repository
public interface AttendanceLogRepository extends JpaRepository<AttendanceLog, Long> {

	@Query("""
			SELECT l FROM AttendanceLog l
			WHERE l.employee.id = :employeeId
			AND l.timestamp BETWEEN :startOfDay AND :endOfDay
			""")
	List<AttendanceLog> findTodayLogsByEmployee(Long employeeId, LocalDateTime startOfDay, LocalDateTime endOfDay);
}