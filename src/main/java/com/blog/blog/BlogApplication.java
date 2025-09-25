package com.blog.blog;

import com.blog.blog.entities.Role;
import com.blog.blog.payloads.AppConstant;
import com.blog.blog.repositorie.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
		System.out.println("🔧 Application initialized successfully.");
	}

	private void initializeRoles() {
		try {
			// Check if roles already exist
			if (roleRepo.findById(AppConstant.ROLE_ADMIN_ID).isEmpty()) {
				Role adminRole = new Role();
				adminRole.setId(AppConstant.ROLE_ADMIN_ID);
				adminRole.setName("ROLE_ADMIN");
				roleRepo.save(adminRole);
				System.out.println("✅ Created ADMIN role");
			}

			if (roleRepo.findById(AppConstant.ROLE_USER_ID).isEmpty()) {
				Role userRole = new Role();
				userRole.setId(AppConstant.ROLE_USER_ID);
				userRole.setName("ROLE_USER");
				roleRepo.save(userRole);
				System.out.println("✅ Created USER role");
			}
		} catch (Exception e) {
			System.err.println("❌ Error initializing roles: " + e.getMessage());
		}
	}

}
