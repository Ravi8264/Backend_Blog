package com.blog.blog;

import com.blog.blog.repositorie.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogApplicationTests {
	@Autowired
	private UserRepo userRepo;

	@Test
	void contextLoads() {
		String className=this.userRepo.getClass().getName();
		String packageName=userRepo.getClass().getPackageName();
		System.out.println(className);
		System.out.println(packageName);
	}

}
