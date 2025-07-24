package com.blog.blog.repositorie;

import com.blog.blog.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Long> {
}
