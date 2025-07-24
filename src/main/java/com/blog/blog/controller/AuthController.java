package com.blog.blog.controller;
import com.blog.blog.jwtAuthentication.JwtService;
import com.blog.blog.payloads.JwtAuthRequest;
import com.blog.blog.payloads.JwtAuthResponse;
import com.blog.blog.payloads.UserDto;
import com.blog.blog.securityBlog.CustomUserDetailsService;
import com.blog.blog.service.UserService;
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

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest jwtAuthRequest) {
        try {
            // Authenticate using email and password
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtAuthRequest.getEmail(),
                            jwtAuthRequest.getPassword()
                    )
            );

            // Load user details by email
            UserDetails userDetails = userDetailsService.loadUserByUsername(jwtAuthRequest.getEmail());

            // Generate JWT token
            String token = jwtService.generateToken(userDetails.getUsername());

            // Return the token in response
            return ResponseEntity.ok(new JwtAuthResponse(token));

        } catch (BadCredentialsException e) {
            // Handle incorrect credentials
            throw new BadCredentialsException("Invalid email or password");
        } catch (UsernameNotFoundException e) {
            // Handle user not found
            throw new UsernameNotFoundException("User not found with email: " + jwtAuthRequest.getEmail());
        }
    }

    //register
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto){
        UserDto registerUser=userService.registerUser(userDto);
        return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
    }
}