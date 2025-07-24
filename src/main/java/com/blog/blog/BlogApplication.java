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
