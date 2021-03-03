package com.designops.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.designops.model.Project;
import com.designops.model.Users;

public interface ProjectRepository extends JpaRepository<Project, Integer>{
  
	Project findByprojectid(int projectid);
//	Project findByProjectname(String projectname);
	List<Project> findByProjectname(String projectname);
	}
