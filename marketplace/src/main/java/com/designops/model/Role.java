package com.designops.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role {
	
	   
	
	@Id
	@GeneratedValue
	private Integer role_id;
	
	private String name;
	private String roleDescription;

	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name ="role_permissions", joinColumns = @JoinColumn (name="role_id"), inverseJoinColumns = @JoinColumn (name="permission_id"))
	private Set<Permission> permissions = new HashSet<>();
	
	
	public Set<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}
	
	//@NotNull(message="First name cannot be missing or empty")
	public Integer getId() {
		return role_id;
	}
	
	public void setId(Integer id) {
		this.role_id = id;
	}
	
	@Size(min=2, max=20,message="Entered name is not valid")
	@NotBlank(message="Name is mandatory")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/*public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}*/
	@Size(min=2, max=30,message="Entered description is not valid")
	public String getRoleDescription() {
		return roleDescription;
	}
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
	
}
