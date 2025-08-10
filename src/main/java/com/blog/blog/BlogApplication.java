package com.blog.blog;

import com.blog.blog.entities.Role;
import com.blog.blog.payloads.AppConstant;
import com.blog.blog.repositorie.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class BlogApplication implements CommandLineRunner {
	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		// Fix PORT environment variable issue before starting Spring Boot
		String portValue = System.getenv("PORT");
		if (portValue != null && ("$PORT".equals(portValue) || portValue.contains("$PORT"))) {
			System.out.println("WARNING: PORT environment variable contains literal '$PORT'. Setting default port 8080.");
			System.setProperty("server.port", "8080");
		} else if (portValue != null) {
			// Validate that PORT is a valid integer
			try {
				Integer.parseInt(portValue);
				System.setProperty("server.port", portValue);
				System.out.println("INFO: Using PORT from environment: " + portValue);
			} catch (NumberFormatException e) {
				System.out.println("WARNING: Invalid PORT value '" + portValue + "'. Setting default port 8080.");
				System.setProperty("server.port", "8080");
			}
		} else {
			// No PORT set, will use application.properties default
			System.out.println("INFO: No PORT environment variable set, using application.properties default");
		}
		
		SpringApplication.run(BlogApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		initializeRoles();
	}

	private void initializeRoles() {
		if (roleRepo.count() == 0) {
			Role userRole = new Role();
			userRole.setId(AppConstant.ROLE_USER_ID);
			userRole.setName(AppConstant.ROLE_USER);
			roleRepo.save(userRole);
			Role adminRole = new Role();
			adminRole.setId(AppConstant.ROLE_ADMIN_ID);
			adminRole.setName(AppConstant.ROLE_ADMIN);
			roleRepo.save(adminRole);
		} else {
			System.out.println("ℹ️  Roles already exist in database. Skipping initialization.");
		}
	}

}
