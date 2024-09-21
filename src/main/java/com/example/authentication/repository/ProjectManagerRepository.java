package com.example.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.authentication.model.ProjectManager;

public interface ProjectManagerRepository extends JpaRepository<ProjectManager, String> {
	
}
