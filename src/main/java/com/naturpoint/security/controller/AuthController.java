package com.naturpoint.security.controller;

import com.naturpoint.exception.UserException;
import com.naturpoint.security.dto.JwtDTO;
import com.naturpoint.security.dto.LoginDTO;
import com.naturpoint.security.dto.UserDTO;
import com.naturpoint.security.enums.RoleName;
import com.naturpoint.security.jwt.JwtProvider;
import com.naturpoint.security.model.Role;
import com.naturpoint.security.model.User;
import com.naturpoint.security.service.IRoleService;
import com.naturpoint.security.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;
import java.net.URI;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private ModelMapper mapper;

    @PostMapping("/new")
    public ResponseEntity<User> newUser(@Valid @RequestBody UserDTO userDTO) {
        Optional<User> optionalUser = userService.findByUsername(userDTO.getUsername());
        if (optionalUser.isPresent()) {
            throw new UserException("USERNAME ALREADY EXISTS: " + userDTO.getUsername());
        }
        User user = new User(userDTO.getUsername(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
                passwordEncoder.encode(userDTO.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getByRoleName(RoleName.ROLE_USER).get());

        if (userDTO.getRoles().contains("admin"))
            roles.add(roleService.getByRoleName(RoleName.ROLE_ADMIN).get());
        user.setRoles(roles);
        userService.save(user);
        //User user = userService.save(mapper.map(userDTO, User.class));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getIdUser()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDTO> login(@Valid @RequestBody LoginDTO loginDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new UserException("INCORRECT FIELDS");

        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(), loginDTO.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = jwtProvider.generateToken(auth);
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        JwtDTO jwtDTO = new JwtDTO(jwt, userDetails.getUsername(), userDetails.getAuthorities());

        return new ResponseEntity<>(jwtDTO, HttpStatus.OK);
    }
}
