package com.example.authentication.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.authentication.model.ProjectManager;
import com.example.authentication.repository.ProjectManagerRepository;

@Service
public class ProjectManagerService implements UserDetailsService{

	@Autowired
	private ProjectManagerRepository projectManagerRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<ProjectManager> projectManager = projectManagerRepository.findById(username);
		if(projectManager.isEmpty()) {
			throw new UsernameNotFoundException("User with username/email "+username+" is not found.");
		}
		return new User(projectManager.get().getUserId(),projectManager.get().getPassword(),new ArrayList<>());
	}
	public void saveUser(ProjectManager projectManager){
		projectManagerRepository.save(projectManager);
	}
}
