package com.blog.blog.payloads;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    @NotNull
    @Size(min = 4, message = "min length 4 char")
    private String name;
    @NotNull
    @Email(message = "email not valid")
    private String email;
    @NotNull
    @Size(min = 3, max = 10, message = "min 3 char max 10")
    private String password;
    @NotNull
    private String about;
    private Set<RoleDto> roles=new HashSet<>();
}
