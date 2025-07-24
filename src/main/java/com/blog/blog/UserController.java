package com.blog.blog;

import com.blog.blog.payloads.ApiResponce;
import com.blog.blog.payloads.UserDto;
import com.blog.blog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")

public class UserController {

    @Autowired
    private UserService userService;

    // ✅ Create User (POST)
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createdUser = this.userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // ✅ Update User (PUT)
    // pathVariable
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Long id) {
        UserDto updatedUser = this.userService.update(userDto, id);
        return ResponseEntity.ok(updatedUser);
    }

    // ✅ Delete User (DELETE)
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponce> deleteUser(@PathVariable("userId") Long Id) {
        this.userService.DeleteUser(Id);
        ApiResponce response = new ApiResponce("User deleted successfully", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // ✅ Get User by ID (GET)
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        UserDto userDto = this.userService.getUserById(userId);
        return ResponseEntity.ok(userDto);
    }

    // ✅ Get All Users (GET)
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }
}
