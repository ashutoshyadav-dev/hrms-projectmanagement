package com.ncm.hrms.repository;

import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ncm.hrms.entity.Project;
import com.ncm.hrms.enums.ProjectStatus;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

	Optional<Project> findByStatus(ProjectStatus active);

	List<Project> findByProjectNameContainingIgnoreCase(String name);

	@Query("SELECT p FROM Project p LEFT JOIN FETCH p.modules LEFT JOIN FETCH p.assignments WHERE p.projectId = :id")
	Optional<Project> findByIdWithDetails(@Param("id") Long id);

	boolean existsByProjectName(String name);

}
