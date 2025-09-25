package com.blog.blog.service.impl;

import com.blog.blog.entities.Role;
import com.blog.blog.entities.User;
import com.blog.blog.exceptions.ResourceNotFoundException;
import com.blog.blog.payloads.AppConstant;
import com.blog.blog.payloads.UserDto;
import com.blog.blog.repositorie.RoleRepo;
import com.blog.blog.repositorie.UserRepo;
import com.blog.blog.service.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createAdminUser(Long userId) {
        // Find the user by ID
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        // Get the ADMIN role
        Role adminRole = roleRepo.findById(AppConstant.ROLE_ADMIN_ID)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", AppConstant.ROLE_ADMIN_ID));

        // Add ADMIN role to user's existing roles
        user.getRoles().add(adminRole);

        // Save updated user
        User savedUser = userRepo.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto removeAdminRole(Long userId) {
        // Find the user by ID
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        // Remove ADMIN role from user
        user.getRoles().removeIf(role -> role.getName().equals(AppConstant.ROLE_ADMIN));

        // Save updated user
        User savedUser = userRepo.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto getAdminUser(Long adminId) {
        // Find the admin user by adminId
        User adminUser = userRepo.findById(adminId)
                .orElseThrow(() -> new ResourceNotFoundException("Admin User", "id", adminId));

        // Check if user has ADMIN role
        boolean isAdmin = adminUser.getRoles().stream()
                .anyMatch(role -> role.getName().equals(AppConstant.ROLE_ADMIN));

        if (!isAdmin) {
            throw new ResourceNotFoundException("Admin User", "id", adminId);
        }

        return modelMapper.map(adminUser, UserDto.class);
    }

    @Override
    public List<UserDto> getAllAdminUsers() {
        // Find all users with ADMIN role
        List<User> adminUsers = userRepo.findAll().stream()
                .filter(user -> user.getRoles().stream()
                        .anyMatch(role -> role.getName().equals(AppConstant.ROLE_ADMIN)))
                .toList();

        return adminUsers.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }

    @Override
    public List<UserDto> getAllUsers() {
        // Get all users from database
        List<User> allUsers = userRepo.findAll();
        return allUsers.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {
        // Find the user by ID
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        // Update user fields (excluding roles and password for security)
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());

        // Save updated user
        User savedUser = userRepo.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public String deleteUserAccount(Long userId) {
        // Find the user by ID
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        // Delete the user account
        userRepo.delete(user);
        return "User account with ID " + userId + " has been deleted successfully.";
    }
}
