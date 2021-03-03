package com.designops.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.designops.model.ProjectUser;
import com.designops.model.Users;

public interface ProjectUserRepository extends JpaRepository<ProjectUser, Integer>{
	
}
