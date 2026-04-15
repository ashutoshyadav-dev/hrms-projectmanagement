package com.ncm.hrms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ncm.hrms.entity.Shift;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {

}
