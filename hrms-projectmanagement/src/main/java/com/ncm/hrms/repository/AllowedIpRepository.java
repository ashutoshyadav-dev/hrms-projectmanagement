package com.ncm.hrms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ncm.hrms.entity.AllowedIp;

@Repository
public interface AllowedIpRepository extends JpaRepository<AllowedIp, Long> {

	Optional<AllowedIp> findByIpAddressAndActiveTrue(String ipAddress);
}
