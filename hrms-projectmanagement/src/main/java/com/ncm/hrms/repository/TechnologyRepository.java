package com.ncm.hrms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ncm.hrms.entity.Technology;

@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Long> {

	Optional<Technology> findByName(String name);

	boolean existsByName(String name);

	boolean existsByNameIgnoreCase(String name);

}
