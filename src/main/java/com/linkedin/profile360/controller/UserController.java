package com.linkedin.profile360.controller;


import com.linkedin.profile360.model.request.user.*;
import com.linkedin.profile360.model.response.CommonResponse;
import com.linkedin.profile360.model.response.DeletedUserResponse;
import com.linkedin.profile360.model.response.UserResponse;
import com.linkedin.profile360.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public UserResponse signup(@RequestBody SignUpRequest request) throws Exception {
        return userService.signUp(request);
    }

    @DeleteMapping("")
    public CommonResponse deleteUser(DeleteUserRequest request) {
        return userService.deleteUser(request);
    }

    @PutMapping("/forget/password")
    public CommonResponse forgetPassword(ForgetPasswordRequest request) {
        return userService.forgetPassword(request);
    }

    @PutMapping("/reset/password")
    public CommonResponse passwordReset(PasswordResetRequest request) {
        return userService.resetPassword(request);
    }

    @PostMapping("/signIn")
    public UserResponse signIn(SignInRequest request) throws Exception {
        return userService.signIn(request);
    }

    @PutMapping("/password/change")
    public CommonResponse passwordUpdate(UpdatePasswordRequest request) throws Exception {
        return userService.passwordUpdate(request);
    }

    @PutMapping("")
    public UserResponse userUpdate(UpdateUserRequest request) throws Exception {
        return userService.updateUser(request);
    }

    @GetMapping("")
    public List<UserResponse> getUsers(GetUsersRequest request) {
        return userService.getUsers(request);
    }

    @GetMapping("deleted/users")
    public List<DeletedUserResponse> deletedUsers(){
        return userService.deletedUsersDetails();
    }

}
