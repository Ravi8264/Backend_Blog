package com.blog.blog.service;

import com.blog.blog.payloads.UserDto;

import java.util.List;

public interface AdminService {
    // Admin role management
    UserDto createAdminUser(Long userId);

    UserDto removeAdminRole(Long userId);

    UserDto getAdminUser(Long adminId);

    List<UserDto> getAllAdminUsers();

    // User management by admin
    List<UserDto> getAllUsers();

    UserDto updateUser(Long userId, UserDto userDto);

    String deleteUserAccount(Long userId);
}
