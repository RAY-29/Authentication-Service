package com.example.authentication.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="project_manager")
public class ProjectManager {
	
	@Id
	@Column(name="user_id", length = 20)
	private String userId;
	
	@Column(name="password", length = 20)
	private String password;
	
	@Column(name="auth_token")
	private String authToken;
}
