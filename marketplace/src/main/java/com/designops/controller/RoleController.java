package com.designops.controller;

import java.util.List;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.designops.exception.RoleNotFoundException;
import com.designops.model.Role;
import com.designops.service.RoleService;
import com.sun.el.stream.Optional;

@RestController
public class RoleController {
	
	@Autowired
	RoleService roleService;
	
	
	//creating the role
	@PostMapping(path="/roles",consumes={"application/json"},produces={"application/json"})
	@ResponseBody
	private ResponseEntity<Role> addRole(@Valid @RequestBody Role role,UriComponentsBuilder builder)   
	{  
	roleService.save(role); 
	HttpHeaders headers=new HttpHeaders();
	headers.setLocation(builder.path("/roles").buildAndExpand(role.getId()).toUri());
	return new ResponseEntity<Role>(headers,HttpStatus.CREATED);
	}  

	//updating the role
	
	@PutMapping("/roles/{roleId}") 
	@ResponseBody
	private ResponseEntity<Role> update(@Valid @RequestBody Role role,@PathVariable Integer roleId)   
	{ 
		if(roleId<=0)
		{
			throw new RoleNotFoundException("Invalid roleId");
		}
		roleService.update(role,roleId);
		
	return new ResponseEntity<Role>(role,HttpStatus.OK);  
	}
	
	//getting the list of roles
	@GetMapping(path="/roles",produces={"application/json"})  
	private ResponseEntity<List<Role>> getAllRoles()   
	{  
		
		List<Role> roles=roleService.getAllRoles(); 
		
		if(roles.isEmpty())
		{
			throw new RoleNotFoundException("There are no roles");
		}
	return  new ResponseEntity<List<Role>>(roles,HttpStatus.OK);
	}
	
	
	//getting the role
	@GetMapping(path="/role/{roleId}",produces={"application/json"})
	public ResponseEntity<Role> getRoleById(@PathVariable("roleId") Integer roleId)
	{
		if(roleId<=0)
		{
			throw new RoleNotFoundException("Invalid roleId");
		}
		Role role=roleService.getRoleById(roleId);
		return new ResponseEntity<Role>(role,HttpStatus.OK);
		
	}
	
	
	//deleting the role
	
	@DeleteMapping("/role/{roleid}")  
	@ResponseBody
	private ResponseEntity<Void> delete(@PathVariable int roleid)   
	{ 
		if(roleid<=0)
		{
			throw new RoleNotFoundException("Invalid roleId");
		}
	roleService.delete(roleid);
	return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}  


}
