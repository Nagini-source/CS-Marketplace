package com.designops.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.designops.model.Permission;

	
	@Repository
	public interface PermissionRepository extends JpaRepository<Permission, Integer> {


	}