package com.designops.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.designops.model.Project;
import com.designops.model.Users;

public interface UsersRepository extends JpaRepository<Users, Integer>{
	Users findByemail(String email);
	Users findByUsername(String username);
	 Users findById(int id);
}
