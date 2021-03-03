package com.designops.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.designops.model.Role;
import com.designops.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired  
	RoleRepository roleRepository;
	public List<Role> getAllRoles() {
		    
		List<Role> roles = new ArrayList<Role>();  
		roleRepository.findAll().forEach(role -> roles.add(role)); 
		return roles;  
		
	}
	public Role getRoleById(Integer id)   
	{  
	return roleRepository.findById(id).get();  
	} 
	public void delete(Integer id)   
	{  
	roleRepository.deleteById(id);  
	} 
	
	public void save(Role role) {
		// TODO Auto-generated method stub
		roleRepository.save(role);
	}
	
	public ResponseEntity<Object> update(Role role, Integer roleId) {
	Optional<Role> roleOptional= roleRepository.findById(roleId);
	if(!roleOptional.isPresent())
		return ResponseEntity.notFound().build();
	role.setId(roleId);
	roleRepository.save(role);
	return ResponseEntity.noContent().build();
	
	}
	public Role findById(Integer roleId) {
		// TODO Auto-generated method stub
		return roleRepository.findById(roleId).get();
	}

}
