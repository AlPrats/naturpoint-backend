package com.naturpoint.security.dto;

import com.naturpoint.security.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<Role> roles = new HashSet<>();
}
