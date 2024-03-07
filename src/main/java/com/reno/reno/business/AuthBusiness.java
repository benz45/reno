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
import com.reno.reno.constant.UserTypeIdConstant;
import com.reno.reno.model.auth.RefreshTokenEntity;
import com.reno.reno.model.auth.RoleTypeEntity;
import com.reno.reno.model.auth.TokenRefreshRequest;
import com.reno.reno.model.auth.TokenRefreshResponse;
import com.reno.reno.model.auth.UserEntity;
import com.reno.reno.model.auth.UserTypeEntity;
import com.reno.reno.model.exception.TokenRefreshException;
import com.reno.reno.payload.request.SigninRequest;
import com.reno.reno.payload.request.SignupRequest;
import com.reno.reno.payload.response.JwtResponse;
import com.reno.reno.payload.response.MessageResponse;
import com.reno.reno.repository.auth.RoleTypeRepository;
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
    private @Autowired UserTypeBusiness userTypeBusiness;
    private @Autowired PasswordEncoder encoder;
    private @Autowired RoleTypeRepository roleRepository;
    private @Autowired CustomerBusiness customerBusiness;
    private @Autowired EmployeeBusiness employeeBusiness;

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
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setRefreshToken(refreshToken.getToken());
        jwtResponse.setAccessToken(jwt);
        jwtResponse.setId(userDetails.getId());
        jwtResponse.setUsername(userDetails.getUsername());
        jwtResponse.setUserType(userDetails.getUserType());
        jwtResponse.setRoles(roles);
        return ResponseEntity.ok(jwtResponse);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<MessageResponse> signupUser(@Valid @RequestBody SignupRequest request) throws Exception {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }
        UserTypeEntity userType = userTypeBusiness.shouldGetUserTypeByIdOrElseThrow(request.getUserTypeId());
        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setUserType(userType);
        shouldSetRoleToUserOrElseThrow(user, request);
        userRepository.save(user);
        if (userType.getId().equals(UserTypeIdConstant.CUSTOMER)) {
            customerBusiness.createCustomer(request, user);
        } else if (userType.getId().equals(UserTypeIdConstant.EMPLOYEE)) {
            employeeBusiness.createEmployee(request, user);
        }
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    public UserEntity shouldSetRoleToUserOrElseThrow(UserEntity user, SignupRequest request) {
        Set<Integer> roleTypeRequests = request.getRole();
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
        return user;
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
