package com.blog.blog.controller;

import com.blog.blog.entities.User;
import com.blog.blog.jwtAuthentication.JwtService;
import com.blog.blog.payloads.JwtAuthRequest;
import com.blog.blog.payloads.JwtAuthResponse;
import com.blog.blog.payloads.UserDto;
import com.blog.blog.repositorie.UserRepo;
import com.blog.blog.securityBlog.CustomUserDetailsService;
import com.blog.blog.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> loginUser(@RequestBody JwtAuthRequest request) {
        try {
            // 1. Authenticate
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()));

            // 2. Generate tokens
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(userDetails.getUsername());
            String refreshToken = jwtService.generateRefreshToken(userDetails.getUsername());

            // 3. Get user details
            User user = userRepo.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            // 4. Convert to DTO
            UserDto userDto = modelMapper.map(user, UserDto.class);

            // 5. Prepare response
            JwtAuthResponse response = new JwtAuthResponse(token, refreshToken, userDto);

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid credentials");
        }
    }
    // register

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        UserDto registerUser = userService.registerUser(userDto);
        return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthResponse> refreshToken(@RequestBody Map<String, String> request) {
        try {
            String refreshToken = request.get("refreshToken");

            // Extract username from refresh token
            String username = jwtService.extractUsername(refreshToken);

            // Validate refresh token
            if (jwtService.isRefreshTokenValid(refreshToken, username)) {
                // Generate new access token
                String newAccessToken = jwtService.generateToken(username);

                // Get user details
                User user = userRepo.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                UserDto userDto = modelMapper.map(user, UserDto.class);

                // Return new access token with the same refresh token
                JwtAuthResponse response = new JwtAuthResponse(newAccessToken, refreshToken, userDto);

                return ResponseEntity.ok(response);
            } else {
                throw new BadCredentialsException("Invalid refresh token");
            }
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid refresh token");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser() {
        // In JWT, logout is typically handled client-side by deleting the token.
        // Optionally, implement token blacklisting if needed.
        return ResponseEntity.ok("User logged out successfully");
    }

}