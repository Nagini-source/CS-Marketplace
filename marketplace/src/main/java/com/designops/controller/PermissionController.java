package com.designops.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.designops.exception.PermissionNotFoundException;
import com.designops.model.Permission;
import com.designops.model.Role;
import com.designops.repository.RoleRepository;
import com.designops.service.PermissionService;
import com.designops.service.RoleService;

@RestController
public class PermissionController {
	
	@Autowired
	PermissionService permissionService;
	
	@Autowired
	RoleRepository roleRepository;
	
	
	@Autowired
	RoleService roleService;
	
	//creating the permission

		@PostMapping(path="/permissions",consumes={"application/json"},produces={"application/json"})  
		@ResponseBody
		private ResponseEntity<Permission> addPermission(@Valid @RequestBody Permission permission,UriComponentsBuilder builder)   
			{  
				permissionService.save(permission); 
				HttpHeaders headers=new HttpHeaders();
				headers.setLocation(builder.path("/addPermission").buildAndExpand(permission.getId()).toUri());
				return new ResponseEntity<Permission>(headers,HttpStatus.CREATED);
			}  
  

		//updating the permission
		
		@PutMapping("/permissions/{permissionId}")  
		@ResponseBody
		private ResponseEntity<Permission> update(@Valid @RequestBody Permission permission, @PathVariable Integer permissionId)   
		{ 
			if(permissionId<=0)
			{
				throw new PermissionNotFoundException("Invalid permissionId");
			}
			
		permissionService.update(permission,permissionId);  
		return new ResponseEntity<Permission>(permission,HttpStatus.OK);   
		}
	
		
		//getting the list of perissions
		@GetMapping(path="/permissions",produces={"application/json"})  
		private ResponseEntity<List<Permission>> getAllPermissions() 
		{  
			List<Permission> permissions=permissionService.getAllPermissions(); 
			if(permissions.isEmpty())
			{
				throw new PermissionNotFoundException("There are no permissions");
			}
		return  new ResponseEntity<List<Permission>>(permissions,HttpStatus.OK);
		
		}
	
	
	//getting the permission
			@GetMapping(path="/permission/{id}",produces={"application/json"})
			public ResponseEntity<Permission> getPermissionById(@PathVariable("id") Integer permissionId)
			{
				if(permissionId<=0)
				{
					throw new PermissionNotFoundException("Invalid permissionId");
				}
				Permission permission=permissionService.getPermissionById(permissionId);
				return new ResponseEntity<Permission>(permission,HttpStatus.OK);
				
			}
			
				
	
	
	//deleting the permission
	
	@DeleteMapping("/permission/{permissionid}")
	@ResponseBody
	private ResponseEntity<Void> delete(@PathVariable int permissionid)   
	{  
		if(permissionid<=0)
		{
			throw new PermissionNotFoundException("Invalid permissionId");
		}
		permissionService.delete(permissionid);
	return new ResponseEntity<Void>(HttpStatus.ACCEPTED);  
	}  

					
	 @PostMapping("/roles/{roleId}/permissions")
	    public void  addPermissionToRole(@PathVariable (value = "roleId") Integer roleId,
	                                 @Valid @RequestBody Permission permission) { 
		 
         Role role=roleService.findById(roleId);
         role.getPermissions().add(permission);
		permission.getRoles().add(role);
		roleService.save(role);	 
		
	    }		
		
	 
	 @DeleteMapping("/roles/{roleId}/permissions/{permissionId}")
	    public void  deletePermissionFromRole(@PathVariable (value = "roleId") Integer roleId,
	                                 @PathVariable(value = "permissionId") Integer permissionId) { 
		 
      Role role=roleService.findById(roleId);
      Permission permission= permissionService.findById(permissionId);
      role.getPermissions().remove(permission);
      roleService.save(role);
		
	
		}  
	


}
	


