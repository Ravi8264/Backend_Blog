package com.blog.blog.service;

import com.blog.blog.payloads.UserDto;

import java.util.List;

public interface UserService {
   UserDto createUser(UserDto userDto);

   UserDto update(UserDto userDto, Long userId);

   UserDto getUserById(Long userId);

   UserDto getUserByEmail(String email);

   List<UserDto> getAllUsers();

   void DeleteUser(Long userId);

   UserDto registerUser(UserDto user);

}
