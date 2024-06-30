package wgu.etreece.swat.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import wgu.etreece.swat.models.user.AppUser;
import wgu.etreece.swat.models.user.Role;
import wgu.etreece.swat.models.user.RoleEnum;
import wgu.etreece.swat.payload.request.LoginRequest;
import wgu.etreece.swat.payload.request.SignUpRequest;
import wgu.etreece.swat.payload.response.JwtResponse;
import wgu.etreece.swat.payload.response.MessageResponse;
import wgu.etreece.swat.repositories.user.AppUserRepository;
import wgu.etreece.swat.repositories.user.RoleRepository;
import wgu.etreece.swat.security.jwt.AuthUserDetails;
import wgu.etreece.swat.security.jwt.JwtUtils;
import wgu.etreece.swat.services.user.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user")
public class AppUserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);

        AuthUserDetails userDetails = (AuthUserDetails) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), userDetails.isDarkModeEnabled(), roles));
    }

    @PostMapping("/signup")
    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use"));
        }

        // Create new user's account
        AppUser user = new AppUser(signUpRequest.getUsername(), signUpRequest.getEmail(), passwordEncoder.encode(signUpRequest.getPassword()), false);

        Set<String> strRoles = signUpRequest.getRoles();

        Set<RoleEnum> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            roles.add(userRole.getName());
        } else {
            strRoles.forEach(role -> {
                if (role.equals("ROLE_ADMIN")) {
                    Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                    roles.add(adminRole.getName());
                } else {
                    Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                    roles.add(userRole.getName());
                }
            });
        }

        user.setRoleEnums(roles);

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Signup Successful"));
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getUsers() {
        try {
            List<AppUser> eventTypes = userRepository.findAll();
            return new ResponseEntity<>(eventTypes, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/change-role")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateUser(@RequestParam String username, @RequestParam String role) {
        try {
            log.info("HERE " + username + role);
            Set<RoleEnum> roles = new HashSet<>();
            roles.add(RoleEnum.valueOf(role));
            userService.changeRole(username, roles);
            return ResponseEntity.ok(new MessageResponse("Role changed"));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Unable to change role: " + e.getMessage());
        }
    }

    @PostMapping("/delete-user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteUser(@RequestParam String username) {
        try {
            userService.deleteUser(username);
            return ResponseEntity.ok(new MessageResponse("User deleted"));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Unable to delete user: " + e.getMessage());
        }
    }

    @GetMapping("/roles")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getRoles() {
        try {
            List<Role> roles = userService.getRoles();
            return new ResponseEntity<>(roles, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
