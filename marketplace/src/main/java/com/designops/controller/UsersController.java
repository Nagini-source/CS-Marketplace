package com.designops.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.designops.exception.UserAlreadyExistsException;
import com.designops.exception.UserNotFoundException;
import com.designops.model.Project;
import com.designops.model.ProjectUser;
import com.designops.model.Role;
import com.designops.model.Users;
import com.designops.repository.ProjectRepository;
import com.designops.repository.ProjectUserRepository;
//import com.designops.repository.ProjectUserRepository;
import com.designops.repository.RoleRepository;
import com.designops.repository.UsersRepository;
import com.designops.utility.Constants;
import com.designops.utility.Email;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UsersController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ProjectUserRepository projectUserRepository;
//	
	@Autowired
	private ProjectRepository projectRepository;

	@GetMapping("/user/{id}")
	public ResponseEntity<Users> getUserByID(@PathVariable(value = "id") Integer userId) throws UserNotFoundException {
		Users foundUsers = usersRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found for this id :: " + userId));
		return new ResponseEntity<Users>(foundUsers, HttpStatus.OK);
	}

	@GetMapping("/user")
	public ResponseEntity<List<Users>> getAllUsers() throws UserNotFoundException {

		List<Users> usersList = usersRepository.findAll();
		if (usersList.isEmpty()) {
			throw new UserNotFoundException("There are no users present");
		}
		return new ResponseEntity<List<Users>>(usersList, HttpStatus.OK);
	}

	@PostMapping("/user")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Users> addUser(@Valid @RequestBody Users users) throws AddressException, MessagingException {
		if (usersRepository.findByemail(users.getEmail()) == null) {
			Users user = new Users();
			ProjectUser projectuser ;
			user.setEmail(users.getEmail());
			user.setUsername(users.getEmail());
			user.setName(users.getName());
			user.setIsActive("1");
			String encrptedRandomPwd = passwordEncoder.encode(users.getPassword());
			user.setPassword(encrptedRandomPwd);
			Users insertedUser = usersRepository.save(user);
			for (int i = 0; i < users.getProjectList().size(); i++) {
				projectuser= new ProjectUser();
				projectuser.setProjectid(users.getProjectList().get(i).getProjectid());
				projectuser.setUserid(user.getUserid());
				projectuser.setRole_id(42);
				projectUserRepository.save(projectuser);
			}
//			List<Project> newProjectList = users.getProjectList();
//			List<Project> existingProjectList = new ArrayList<>();
//			for (int i = 0; i < newProjectList.size(); i++) {
//				Project existingProject = projectRepository.findByprojectid(newProjectList.get(i).getProjectid());
//				existingProjectList.add(existingProject);
//			}
//			user.setProjectList(existingProjectList);
			
		    //user.setRole(users.getRole());
			
			//Email.sendEmail(Constants.fromMail, users.getEmail(), Constants.host, users.getPassword());
			
			return new ResponseEntity<Users>(insertedUser, HttpStatus.CREATED);
		} else {
			throw new UserAlreadyExistsException("User already exists with this email :: " + users.getEmail());
		}

	}

	@PutMapping("/users/{id}")
	public ResponseEntity<Users> updateUser(@Valid @PathVariable("id") int userId, @RequestBody Users users)
			throws UserNotFoundException {
	Users presentUser = usersRepository.findById(userId);
//				.orElseThrow(() -> new UserNotFoundException("User not found for this id :: " + userId));
		presentUser.setEmail(users.getEmail());
		presentUser.setName(users.getName());
		presentUser.setProjectList(users.getProjectList());
		final Users updatedEmployee = usersRepository.save(presentUser);
		return new ResponseEntity<Users>(updatedEmployee, HttpStatus.OK);
	}

	@PutMapping("/forgotpassword")
	public String forgotPassword(@Valid @RequestBody Users users) throws UserNotFoundException {
		Users presentUser = usersRepository.findByemail(users.getEmail());
		if (presentUser != null) {
			//presentUser.setEmail(users.getEmail());
			presentUser.setPassword(passwordEncoder.encode(users.getPassword()));
			Email.sendEmail(Constants.fromMail, users.getEmail(), Constants.host, users.getPassword());
			return "One Time Password is sent to registered email id, use this and generate new password.";
		} else {
			return "User not found with this email id, Please enter registered email id";
		}
	}
	
//	@PutMapping("/changepassword")
//	public String changePassword(@Valid @RequestBody Users users) throws UserNotFoundException {
//		Users presentUser = usersRepository.findByemail(users.getEmail());
//		if (presentUser != null) {
//			if(passwordEncoder.matches(users.getOldPassword(), presentUser.getPassword()))
//			{
//				presentUser.setPassword(users.getPassword());
//				return "Password Reset Successfully !";
//			}
//			{
//				return "One Time Password did not match ";
//			}		
//		} else {
//			return "User not found with this email id, Please enter registered email id";
//		}
//	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable(value = "id") Integer userId)
			throws UserNotFoundException {
		Users employee = usersRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found for this id :: " + userId));
		usersRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return new ResponseEntity<Map<String, Boolean>>(response, HttpStatus.ACCEPTED);
	}

//	@DeleteMapping("/users")
//	public ResponseEntity<String> deleteAllUsers()throws UserNotFoundException {
//		try
//		{
//		   usersRepository.deleteAll();
//		}
//		catch(Exception e)
//		{
//			throw new UserNotFoundException("There are no users present");
//		}
//		return  new ResponseEntity<String>("Deleted All Users",HttpStatus.OK);
//	}

	
	@GetMapping("/login")
	public String get()
	{
//		CustomUserDetails cust= new CustomUserDetails();
//		String abc=cust.getUsers().getName();
//		return abc;
		
		return "HIIII";
	}
}