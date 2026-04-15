package com.ncm.hrms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ncm.hrms.entity.Designation;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, Long> {
	Optional<Designation> findByTitle(String title);

	boolean existsByTitle(String title);
}
