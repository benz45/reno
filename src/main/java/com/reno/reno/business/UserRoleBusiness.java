package com.reno.reno.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reno.reno.model.role.UserRoleEntity;
import com.reno.reno.repository.role.UserRoleRepository;

@Component
public class UserRoleBusiness {
    private @Autowired UserRoleRepository userRoleRepository;

    public List<UserRoleEntity> saveAll(List<UserRoleEntity> userRoles) {
        return userRoleRepository.saveAll(userRoles);
    }

}
