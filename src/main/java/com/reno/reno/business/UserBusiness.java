package com.reno.reno.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reno.reno.model.exception.ApiException;
import com.reno.reno.model.role.RoleTypeEntity;
import com.reno.reno.model.role.UserRoleEntity;
import com.reno.reno.model.user.UserEntity;
import com.reno.reno.model.user.UserSignUpRequest;
import com.reno.reno.repository.user.UserRepository;

@Component
public class UserBusiness {
    private @Autowired UserRepository userRepository;
    private @Autowired RoleTypeBusiness roleTypeBusiness;
    private @Autowired UserRoleBusiness userRoleBusiness;

    public UserEntity shouldSetUserNameIfNotExists(String usernameExpected, UserEntity user) throws ApiException {
        Boolean isHaveUser = userRepository.existsByUsername(usernameExpected);
        if (isHaveUser) {
            throw new ApiException("400", "Username is already taken!");
        }
        user.setUsername(usernameExpected);
        return user;
    }

    public UserEntity shouldSetEmailIfNotExists(String emailExpected, UserEntity user) throws ApiException {
        Boolean isHaveUser = userRepository.existsByEmail(emailExpected);
        if (isHaveUser) {
            throw new ApiException("400", "Email is already taken!");
        }
        user.setEmail(emailExpected);
        return user;
    }

    public UserEntity shouldRoleToUser(UserSignUpRequest request, UserEntity user) throws ApiException {
        if (!request.getRoleTypes().isEmpty()) {
            List<UserRoleEntity> userRoles = new ArrayList<UserRoleEntity>();
            for (RoleTypeEntity roleType : request.getRoleTypes()) {
                UserRoleEntity userRole = new UserRoleEntity();
                userRole.setRoleType(roleTypeBusiness.shouldGetRoleTypeByIdOrElseThrow(roleType.getId()));
                userRole.setUser(user);
                userRole.setCreatedAt(new Date());
                userRole.setUpdatedAt(new Date());
                userRoles.add(userRole);
            }
            userRoleBusiness.saveAll(userRoles);
        }
        return user;
    }
}
