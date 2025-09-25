package com.blog.blog.controller;
import com.blog.blog.payloads.UserDto;
import com.blog.blog.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/admin")
public class AdminController {

   @Autowired
   private AdminService adminService;

   // Admin Role Management
   @PostMapping("/make-admin/{userId}")
   public ResponseEntity<UserDto> makeUserAdmin(@PathVariable Long userId) {
      UserDto adminUser = adminService.createAdminUser(userId);
      return new ResponseEntity<>(adminUser, HttpStatus.OK);
   }

   @PutMapping("/remove-admin/{userId}")
   public ResponseEntity<UserDto> removeAdminRole(@PathVariable Long userId) {
      UserDto user = adminService.removeAdminRole(userId);
      return new ResponseEntity<>(user, HttpStatus.OK);
   }

   @GetMapping("/get-admin/{adminId}")
   public ResponseEntity<UserDto> getAdmin(@PathVariable Long adminId) {
      UserDto adminUser = adminService.getAdminUser(adminId);
      return new ResponseEntity<>(adminUser, HttpStatus.OK);
   }

   @GetMapping("/get-all-admins")
   public ResponseEntity<List<UserDto>> getAllAdmins() {
      List<UserDto> adminUsers = adminService.getAllAdminUsers();
      return new ResponseEntity<>(adminUsers, HttpStatus.OK);
   }

   // User Management by Admin
   @GetMapping("/users/all")
   public ResponseEntity<List<UserDto>> getAllUsers() {
      List<UserDto> allUsers = adminService.getAllUsers();
      return new ResponseEntity<>(allUsers, HttpStatus.OK);
   }

   @PutMapping("/users/update/{userId}")
   public ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
      UserDto updatedUser = adminService.updateUser(userId, userDto);
      return new ResponseEntity<>(updatedUser, HttpStatus.OK);
   }

   @DeleteMapping("/users/delete/{userId}")
   public ResponseEntity<String> deleteUserAccount(@PathVariable Long userId) {
      String message = adminService.deleteUserAccount(userId);
      return new ResponseEntity<>(message, HttpStatus.OK);
   }
}
