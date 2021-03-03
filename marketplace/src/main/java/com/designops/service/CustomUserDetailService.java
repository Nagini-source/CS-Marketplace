package com.designops.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.designops.model.Users;

import com.designops.repository.UsersRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	private UsersRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users users = repository.findByUsername(username);
	    
		CustomUserDetails userDetails = null;
		if(users !=null)
		{ 
			userDetails = new CustomUserDetails();
			userDetails.setUsers(users);
			System.out.println("users="+users.getEmail());
			
				}
		else {
			throw new UsernameNotFoundException("user not exits with the name" + username);
		}
		
	
		return userDetails;
	}

}