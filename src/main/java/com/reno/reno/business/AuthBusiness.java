package com.reno.reno.business;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.reno.reno.constant.RoleTypeConstant;
import com.reno.reno.model.auth.RefreshTokenEntity;
import com.reno.reno.model.auth.RoleTypeEntity;
import com.reno.reno.model.auth.TokenRefreshRequest;
import com.reno.reno.model.auth.TokenRefreshResponse;
import com.reno.reno.model.auth.UserEntity;
import com.reno.reno.model.exception.TokenRefreshException;
import com.reno.reno.payload.request.SigninRequest;
import com.reno.reno.payload.request.SignupRequest;
import com.reno.reno.payload.response.JwtResponse;
import com.reno.reno.payload.response.MessageResponse;
import com.reno.reno.repository.auth.RoleRepository;
import com.reno.reno.repository.auth.UserRepository;
import com.reno.reno.security.jwt.JwtUtils;
import com.reno.reno.security.services.RefreshTokenService;
import com.reno.reno.security.services.UserDetailsImpl;

import jakarta.validation.Valid;

@Component
public class AuthBusiness {
    private @Autowired JwtUtils jwtUtils;
    private @Autowired AuthenticationManager authenticationManager;
    private @Autowired RefreshTokenService refreshTokenService;
    private @Autowired UserRepository userRepository;
    private @Autowired PasswordEncoder encoder;
    private @Autowired RoleRepository roleRepository;

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<JwtResponse> signinUser(SigninRequest signinRequest) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(signinRequest.getUsername(),
                signinRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(auth);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity
                .ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(), userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> signupUaer(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        UserEntity user = new UserEntity();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));

        Set<Integer> roleTypeRequests = signUpRequest.getRole();
        Set<RoleTypeEntity> roleTypes = new HashSet<>();

        if (roleTypeRequests == null) {
            RoleTypeEntity userRole = roleRepository.findByNameEn(RoleTypeConstant.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roleTypes.add(userRole);
        } else {
            roleTypeRequests.stream().forEach((roleId) -> {
                RoleTypeEntity _roleTypes = roleRepository.findById(roleId)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found id: " + roleId.toString()));
                roleTypes.add(_roleTypes);
            });
        }

        user.setRoleTypes(roleTypes);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public ResponseEntity<?> getRefreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshTokenEntity::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<MessageResponse> signoutUser(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        RefreshTokenEntity refreshToken = refreshTokenService.findByToken(requestRefreshToken)
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
        refreshTokenService.setIsDeleteRefreshToken(refreshToken);
        return ResponseEntity.ok(new MessageResponse("User signout successfully!"));
    }
}
