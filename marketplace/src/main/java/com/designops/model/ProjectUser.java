package com.designops.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "projectuser")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectUser {

	@Id
	@GeneratedValue
	private Integer projectuserroleid;
	
	private Integer projectid;
	
	private Integer userid;
	
	private Integer role_id;
	
	private String rolename;
	
	private String email;
	
	private String username;
	
	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getProjectuserroleid() {
		return projectuserroleid;
	}

	public void setProjectuserroleid(Integer projectuserroleid) {
		this.projectuserroleid = projectuserroleid;
	}

	public Integer getRole_id() {
		return role_id;
	}

	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}

	public Integer getProjectid() {
		return projectid;
	}

	public void setProjectid(Integer projectid) {
		this.projectid = projectid;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

}
