package com.blog.blog.service.impl;

import com.blog.blog.entities.Role;
import com.blog.blog.entities.User;
import com.blog.blog.exceptions.ResourceNotFoundException;
import com.blog.blog.payloads.UserDto;
import com.blog.blog.repositorie.RoleRepo;
import com.blog.blog.repositorie.UserRepo;
import com.blog.blog.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        // Encode password before saving
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User saveUser = this.userRepo.save(user);
        return this.userToDto(saveUser);
    }

    @Override
    public UserDto update(UserDto userDto, Long userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        // Update user values
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        // Encode password if it's being updated
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        user.setAbout(userDto.getAbout());
        User updatedUser = this.userRepo.save(user);
        return userToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return userToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void DeleteUser(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        userRepo.delete(user);
    }

    @Override
    public UserDto registerUser(UserDto userDto) {
        // 1. Convert DTO to entity
        User user = modelMapper.map(userDto, User.class);

        // 2. Encode password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        // 3. Assign default role (ROLE_USER with ID = 501)
        Role defaultRole = roleRepo.findById(501L)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", 501L));

        user.getRoles().add(defaultRole);

        // 4. Save user
        User savedUser = this.userRepo.save(user);

        // 5. Return DTO
        return this.userToDto(savedUser);
    }

    private User dtoToUser(UserDto userDto) {
        // User user = new User();

        // user.setId(userDto.getId());
        // user.setName(userDto.getName());
        // user.setEmail(userDto.getEmail());
        // user.setPassword(userDto.getPassword());
        // user.setAbout(userDto.getAbout());
        return this.modelMapper.map(userDto, User.class);
    }

    public UserDto userToDto(User user) {
        // UserDto userDto = new UserDto();
        // userDto.setId(user.getId());
        // userDto.setName(user.getName());
        // userDto.setEmail(user.getEmail());
        // userDto.setPassword(user.getPassword());
        // userDto.setAbout(user.getAbout());
        // return userDto;
        return this.modelMapper.map(user, UserDto.class);
    }
}
