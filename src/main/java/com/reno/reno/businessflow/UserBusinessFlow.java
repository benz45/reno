package com.reno.reno.businessflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.reno.reno.business.UserBusiness;
import com.reno.reno.model.exception.ApiException;
import com.reno.reno.model.user.UserEntity;
import com.reno.reno.model.user.UserSignUpRequest;
import com.reno.reno.repository.user.UserRepository;

@Component
public class UserBusinessFlow {
    private @Autowired UserBusiness userBusiness;
    private @Autowired UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    public UserEntity signupUser(UserSignUpRequest request) throws ApiException {
        UserEntity user = new UserEntity();

        userBusiness.shouldSetUserNameIfNotExists(request.getUsername(), user);
        userBusiness.shouldSetEmailIfNotExists(request.getEmail(), user);
        user.setName(request.getName());
        user.setPassword(request.getPassword());
        user = userRepository.save(user);
        user = userBusiness.shouldRoleToUser(request, user);
        return user;
    }
}
