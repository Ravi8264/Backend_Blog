package com.blog.blog.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {  // Changed from "extends" to "implements"

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Email
    @Size(max = 100)
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @Size(max = 500)
    private String about;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    // Custom constructor
    public User(String name, String email, String password, String about) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.about = about;
    }

    /* UserDetails implementation */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.email; // Using email as username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Modify as needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Modify as needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Modify as needed
    }

    @Override
    public boolean isEnabled() {
        return true; // Modify as needed
    }
}