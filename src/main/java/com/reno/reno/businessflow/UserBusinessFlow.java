package com.reno.reno.businessflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reno.reno.business.UserBusiness;

@Component
public class UserBusinessFlow {

    // private @Autowired PasswordEncoder encoder;
    private @Autowired UserBusiness userBusiness;

    // @Transactional(rollbackFor = Exception.class)
    // public UserEntity signupUser(SignupRequest request) throws ApiException {
    // UserEntity user = new UserEntity();

    // // userBusiness.shouldSetUserNameIfNotExists(request.getUsername(), user);
    // // userBusiness.shouldSetEmailIfNotExists(request.getEmail(), user);
    // // user.setName(request.getName());
    // // user.setPassword(request.getPassword());
    // // // user = userBusiness.shouldRoleToUser(request, user);
    // // user = userRepository.save(user);
    // return user;
    // }
}
