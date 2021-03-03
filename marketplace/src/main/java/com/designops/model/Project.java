package com.designops.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "projects")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Project {

////	public Project(String project_name) {
////	 project_name=this.project_name;
////	}
//	
//	public Project() {}
	
	@Id
	@GeneratedValue
	@Column(name="projectid")
	private Integer projectid;
	
	@Column(name="projectname")
	private String projectname;
	
//	@OneToMany(mappedBy="projectList",cascade = CascadeType.ALL)
//	private List<Users> users =new ArrayList<>();
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "projectuser", joinColumns = @JoinColumn(name = "projectid"), inverseJoinColumns = @JoinColumn(name = "userid"))
	private List<Users> users =new ArrayList<>();
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "projectuser", joinColumns = @JoinColumn(name = "projectid"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles =new ArrayList<>();
	
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	private String projecttype;
	
	private String description;
	
	public Integer getProjectid() {
		return projectid;
	}
	public void setProjectid(Integer projectid) {
		this.projectid = projectid;
	}
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	public List<Users> getUsers() {
		return users;
	}
	public void setUsers(List<Users> users) {
		this.users = users;
	}
	public String getProjecttype() {
		return projecttype;
	}
	public void setProjecttype(String projecttype) {
		this.projecttype = projecttype;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
