package com.ncm.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ncm.hrms.entity.Modules;

public interface ModulesRepository extends JpaRepository<Modules, Long> {

	List<Modules> findByProjectProjectId(Long projectId);

	List<Modules> findByEmployeeId(Long employeeId);

}
