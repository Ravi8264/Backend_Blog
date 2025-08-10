package com.blog.blog;

import com.blog.blog.entities.Role;
import com.blog.blog.payloads.AppConstant;
import com.blog.blog.repositorie.RoleRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(BlogApplication.class);

    @Autowired
    private RoleRepo roleRepo;

    public static void main(String[] args) {
        // CRITICAL: Fix PORT issue before Spring Boot starts
        fixPortIssue();
        
        SpringApplication.run(BlogApplication.class, args);
    }

    private static void fixPortIssue() {
        String portEnv = System.getenv("PORT");
        
        System.out.println("=== PORT FIX DEBUG ===");
        System.out.println("Original PORT env: " + portEnv);
        
        // Force set system property to override any problematic values
        if (portEnv == null || "$PORT".equals(portEnv) || portEnv.contains("$PORT")) {
            System.out.println("FIXING: PORT is null or contains $PORT literal");
            System.setProperty("server.port", "8080");
            
            // Also try to clear the problematic environment
            try {
                System.clearProperty("PORT");
            } catch (Exception e) {
                System.out.println("Could not clear PORT property: " + e.getMessage());
            }
        } else {
            try {
                int port = Integer.parseInt(portEnv);
                System.out.println("Valid PORT found: " + port);
                System.setProperty("server.port", String.valueOf(port));
            } catch (NumberFormatException e) {
                System.out.println("Invalid PORT format, using 8080");
                System.setProperty("server.port", "8080");
            }
        }
        
        System.out.println("Final server.port: " + System.getProperty("server.port"));
        System.out.println("=====================");
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
