package com.designops.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "permissions")
public class Permission {
	
	@Id
	@Column(name="permission_id")
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String description;
	
	public Permission(Integer id, String name, String description, Set<Role> roles) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.roles = roles;
	}
	
	public Permission( String name, String description) {
		this.name = name;
		this.description = description;
		
	}
	
	public Permission() {
		
	}
	
	@ManyToMany(mappedBy="permissions")
	private Set<Role> roles =new HashSet<>();
	
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Size(min=2, max=20,message="Entered name is not valid")
	@NotBlank(message="Name is mandatory")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Size(min=2,message="Entered description is not valid")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
