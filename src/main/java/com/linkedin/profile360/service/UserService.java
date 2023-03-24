package com.linkedin.profile360.service;

import com.linkedin.profile360.model.request.user.*;
import com.linkedin.profile360.model.response.CommonResponse;
import com.linkedin.profile360.model.response.DeletedUserResponse;
import com.linkedin.profile360.model.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse signUp(SignUpRequest request) throws Exception;

    CommonResponse deleteUser(DeleteUserRequest request) throws Exception;

    UserResponse signIn(SignInRequest request) throws Exception;

    CommonResponse passwordUpdate(UpdatePasswordRequest request) throws Exception;

    UserResponse updateUser(UpdateUserRequest request) throws Exception;

    List<UserResponse> getUsers(GetUsersRequest request);
}
