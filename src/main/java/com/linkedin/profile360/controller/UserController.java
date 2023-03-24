package com.linkedin.profile360.controller;


import com.linkedin.profile360.model.request.user.*;
import com.linkedin.profile360.model.response.CommonResponse;
import com.linkedin.profile360.model.response.UserResponse;
import com.linkedin.profile360.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    @ResponseStatus(code = HttpStatus.OK)
    public UserResponse signup(SignUpRequest request) throws Exception {
        return userService.signUp(request);
    }

    @DeleteMapping("")
    @ResponseStatus(code = HttpStatus.OK)
    public CommonResponse deleteUser(DeleteUserRequest request) throws Exception {
        return userService.deleteUser(request);
    }

    @PostMapping("/signIn")
    @ResponseStatus(code = HttpStatus.OK)
    public UserResponse signIn(SignInRequest request) throws Exception {
        return userService.signIn(request);
    }

    @PutMapping("/password/change")
    public CommonResponse passwordUpdate(UpdatePasswordRequest request) throws Exception {
        return userService.passwordUpdate(request);
    }

    @PutMapping("")
    @ResponseStatus(code = HttpStatus.OK)
    public UserResponse userUpdate(UpdateUserRequest request) throws Exception {
        return userService.updateUser(request);
    }

    @GetMapping("")
    @ResponseStatus(code = HttpStatus.OK)
    public List<UserResponse> getUsers(GetUsersRequest request) {
        return userService.getUsers(request);
    }


}
