package com.blog.blog.payloads;

public class JwtAuthResponse {
    private String token;
    private String refreshToken;
    private UserDto user;

    public JwtAuthResponse() {
    }

    public JwtAuthResponse(String token) {
        this.token = token;
    }

    public JwtAuthResponse(String token, UserDto user) {
        this.token = token;
        this.user = user;
    }

    public JwtAuthResponse(String token, String refreshToken, UserDto user) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.user = user;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
