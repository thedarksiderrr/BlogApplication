package com.myapp.blog.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.myapp.blog.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Long id;

    @NotEmpty(message = "First Name can't be empty!!")
    private String firstName;
    @NotEmpty(message = "Last Name can't be empty!!")
    private String lastName;

    @Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Email address is invalid!!")
    private String email;

    @NotEmpty(message = "Password can't be empty!!")
    @Size(min = 6, max = 14, message = "Password must be contain between {min} - {max}")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String about;

    private Set<RoleDTO> roles = new HashSet<>();
}
