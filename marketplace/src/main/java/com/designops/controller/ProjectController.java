package com.designops.controller;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.designops.exception.ResourceNotFoundException;
import com.designops.exception.UserNotFoundException;
import com.designops.model.Project;
import com.designops.model.ProjectUser;
import com.designops.model.Users;
import com.designops.repository.ProjectRepository;
import com.designops.repository.ProjectUserRepository;
import com.designops.repository.RoleRepository;
//import com.designops.repository.ProjectUserRepository;
import com.designops.repository.UsersRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/marketplace")
public class ProjectController {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ProjectUserRepository projectUserRepository;

	@GetMapping("/projects")
	public ResponseEntity<List<Project>> getAllProject() throws ResourceNotFoundException {

		List<Project> projectList = projectRepository.findAll();
		if (projectList.isEmpty()) {
			throw new ResourceNotFoundException("There are no projects present");
		}
		return new ResponseEntity<List<Project>>(projectList, HttpStatus.OK);
	}

	@PostMapping("/users/{user_id}/projects")
	public void addProjectToUser(@PathVariable(value = "user_id") Integer user_id,
			@Valid @RequestBody Project project) {
		Users user = usersRepository.findById(user_id)
				.orElseThrow(() -> new UserNotFoundException("User not found for this id :: " + user_id));
		user.getProjectList().add(project);
		project.getUsers().add(user);
		usersRepository.save(user);

	}

	@PostMapping("/projects")
	public ResponseEntity<Project> addProject(@Valid @RequestBody Project project)
			throws AddressException, MessagingException {
		Project proj = new Project();
		ProjectUser projectuser ;
		proj.setProjectname(project.getProjectname());
		proj.setProjecttype(project.getProjecttype());
		proj.setDescription(project.getDescription());
		Project insertedProject = projectRepository.save(proj);
		
		for (int i = 0; i < project.getUsers().size(); i++) {
			projectuser= new ProjectUser();
			projectuser.setProjectid(proj.getProjectid());
			projectuser.setUserid(project.getUsers().get(i).getUserid());
			projectuser.setRole_id(project.getRoles().get(i).getId());
			projectUserRepository.save(projectuser);
		}
		/*
		 * List<Users> newUsersList = project.getUsers(); List<Users> existingUserList =
		 * new ArrayList<>(); for (int i = 0; i < newUsersList.size(); i++) { Users
		 * existingUser = usersRepository.findByemail(newUsersList.get(i).getEmail());
		 * existingUserList.add(existingUser); } proj.setUsers(existingUserList);
		 * List<Role> newRoleList = project.getRoles(); List<Role> existingRoleList =
		 * new ArrayList<>(); for (int i = 0; i < newRoleList.size(); i++) { List<Role>
		 * existingRole = roleRepository.findByName(newRoleList.get(i).getName()); for
		 * (int j = 0; j < existingRole.size(); j++) {
		 * existingRoleList.add(existingRole.get(j)); } }
		 * proj.setRoles(existingRoleList);
		 */
		
		return new ResponseEntity<Project>(insertedProject, HttpStatus.CREATED);
	}

//	@DeleteMapping("/roles/{roleId}/permissions/{permissionId}")
//	public void deleteProjectFromUser(@PathVariable(value = "roleId") Integer roleId,
//			@PathVariable(value = "permissionId") Integer permissionId) {
//		Role role = roleService.findById(roleId);
//		Permission permission = permissionService.findById(permissionId);
//		role.getPermissions().remove(permission);
//		roleService.save(role);
//
//	}
}
