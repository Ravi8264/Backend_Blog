package com.blog.blog.jwtAuthentication;

import com.blog.blog.securityBlog.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Skip JWT processing for public endpoints and OPTIONS requests
        String requestPath = request.getRequestURI();
        String requestMethod = request.getMethod();

        // Debug logging
        System.out.println("JWT Filter - Path: " + requestPath + ", Method: " + requestMethod);

        if (isPublicEndpoint(requestPath) || isOptionsRequest(request)) {
            System.out.println("JWT Filter - Skipping for public endpoint or OPTIONS request");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = getToken(request);

            if (token != null) {
                String username = jwtService.extractUsername(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                    if (jwtService.isTokenValid(token, username)) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }
        } catch (Exception ex) {
            // Log the exception and continue without setting authentication
            logger.error("JWT Authentication failed: " + ex.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private boolean isPublicEndpoint(String requestPath) {
        boolean isPublic = requestPath.startsWith("/api/v1/auth/") ||
                requestPath.startsWith("/auth/") ||
                requestPath.startsWith("/public/") ||
                requestPath.startsWith("/api/categories/") ||
                requestPath.startsWith("/api/posts/") ||
                requestPath.startsWith("/api/category/") ||
                requestPath.startsWith("/api/user/") ||
                requestPath.startsWith("/api/users/") ||
                requestPath.startsWith("/v3/api-docs") ||
                requestPath.startsWith("/swagger-ui/") ||
                requestPath.startsWith("/swagger-resources/") ||
                requestPath.startsWith("/webjars/") ||
                requestPath.equals("/swagger-ui.html");

        // Debug logging
        System.out.println("JWT Filter - isPublicEndpoint: " + requestPath + " = " + isPublic);

        return isPublic;
    }

    private boolean isOptionsRequest(HttpServletRequest request) {
        return "OPTIONS".equalsIgnoreCase(request.getMethod());
    }
}