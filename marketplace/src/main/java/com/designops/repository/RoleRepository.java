package com.designops.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.designops.model.Role;

	
	@Repository
	public interface RoleRepository extends JpaRepository<Role, Integer> {

		List<Role> findByName(String name);
//		@Query("SELECT r FROM roles r WHERE r.role_id = :roleid")
//		Role findByRoleID(@Param("roleid") int roleid);
	}

