package com.reno.reno.business;

import org.springframework.stereotype.Component;

@Component
public class UserBusiness {
    // public UserEntity shouldGetUserByIdOrElseThrow(Long id) throws ApiException {
    // return userRepository.findById(id)
    // .orElseThrow(() -> new ApiException("400", "Can't find user by id: " + id));
    // }

    // public UserEntity shouldGetUserByUsernameOrElseThrow(String username) throws
    // ApiException {
    // return userRepository.findByUsername(username)
    // .orElseThrow(() -> new ApiException("400", "Can't find user by username: " +
    // username));
    // }

    // public UserEntity shouldSetUserNameIfNotExists(String usernameExpected,
    // UserEntity user) throws ApiException {
    // Boolean isHaveUser = userRepository.existsByUsername(usernameExpected);
    // if (isHaveUser) {
    // throw new ApiException("400", "Username is already taken!");
    // }
    // user.setUsername(usernameExpected);
    // return user;
    // }

    // public UserEntity shouldSetEmailIfNotExists(String emailExpected, UserEntity
    // user) throws ApiException {
    // Boolean isHaveUser = userRepository.existsByEmail(emailExpected);
    // if (isHaveUser) {
    // throw new ApiException("400", "Email is already taken!");
    // }
    // user.setEmail(emailExpected);
    // return user;
    // }

    // public UserEntity shouldRoleToUser(SignupRequest request, UserEntity user)
    // throws ApiException {
    // // if (!request.getRoleTypes().isEmpty()) {
    // // List<UserRoleEntity> userRoles = new ArrayList<UserRoleEntity>();
    // // for (RoleTypeEntity roleType : request.getRoleTypes()) {
    // // UserRoleEntity userRole = new UserRoleEntity();
    // //
    // userRole.setRoleType(roleTypeBusiness.shouldGetRoleTypeByIdOrElseThrow(roleType.getId()));
    // // userRole.setUser(user);
    // // userRole.setCreatedAt(new Date());
    // // userRole.setUpdatedAt(new Date());
    // // userRoles.add(userRole);
    // // }
    // // userRoleBusiness.saveAll(userRoles);
    // // }
    // Set<String> strRoles = request.getRole();
    // Set<RoleTypeEntity> roles = new HashSet<>();

    // if (strRoles == null) {
    // RoleTypeEntity userRole =
    // roleTypeBusiness.shouldGetRoleTypeByNameEnOrElseThrow(RoleName.ROLE_USER);
    // roles.add(userRole);
    // } else {
    // strRoles.forEach(role -> {
    // switch (role) {
    // case "admin":
    // RoleTypeEntity userRoleAdmin = roleTypeBusiness
    // .shouldGetRoleTypeByNameEnOrElseThrow(RoleName.ROLE_ADMIN);
    // roles.add(userRoleAdmin);
    // break;
    // case "pm":
    // RoleTypeEntity userRolePM = roleTypeBusiness
    // .shouldGetRoleTypeByNameEnOrElseThrow(RoleName.ROLE_PM);
    // roles.add(userRolePM);
    // break;
    // default:
    // RoleTypeEntity userRole = roleTypeBusiness
    // .shouldGetRoleTypeByNameEnOrElseThrow(RoleName.ROLE_USER);
    // roles.add(userRole);
    // }
    // });
    // }
    // user.setRoles(roles);
    // return user;
    // }
}
