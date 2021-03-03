package com.designops.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.designops.model.Permission;
import com.designops.repository.PermissionRepository;

@Service
public class PermissionService {

	@Autowired  
	PermissionRepository permissionRepository;
	
	public List<Permission> getAllPermissions() {
	    
		List<Permission> permissions = new ArrayList<Permission>();  
		permissionRepository.findAll().forEach(permission -> permissions.add(permission));  
		return permissions;  
		
	}
	public Permission getPermissionById(Integer id)   
	{  
	return permissionRepository.findById(id).get();  
	} 
	public Permission save(Permission permission) {
	
		 return permissionRepository.save(permission);
		
		
		
	}
	public ResponseEntity<Object> update(Permission permission, Integer permissionId) {
	Optional<Permission> permissionOptional= permissionRepository.findById(permissionId);
	if(!permissionOptional.isPresent())
		return ResponseEntity.notFound().build();
	permission.setId(permissionId);
	permissionRepository.save(permission);
	return ResponseEntity.noContent().build();
	
	} 

	public void delete(Integer id)   
	{  
	permissionRepository.deleteById(id);  
	}
	
	public Permission findById(Integer permissionId) {
		return permissionRepository.findById(permissionId).get();
	}

	
	
}
