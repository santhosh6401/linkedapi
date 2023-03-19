package com.linkedin.profile360.service.serviceImpl;


import com.linkedin.profile360.model.entity.DeletedUserEntity;
import com.linkedin.profile360.model.entity.UserEntity;
import com.linkedin.profile360.model.request.user.*;
import com.linkedin.profile360.model.response.CommonResponse;
import com.linkedin.profile360.model.response.DeletedUserResponse;
import com.linkedin.profile360.model.response.UserResponse;
import com.linkedin.profile360.repository.DeletedUserRepository;
import com.linkedin.profile360.repository.UserRepository;
import com.linkedin.profile360.service.UserService;
import com.linkedin.profile360.utils.Helper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeletedUserRepository deletedUserRepository;

    @Autowired
    private Helper helper;

    @Override
    public UserResponse signUp(SignUpRequest request) throws Exception {
        Optional<UserEntity> userEntityOptional = userRepository.findByUserName(request.getUserName());
        if (userEntityOptional.isPresent()) {
            throw new Exception("user name already exist");
        }
        if (request.getConfirmPassword().equals(request.getPassword())) {
            UserEntity entity = new UserEntity();
            BeanUtils.copyProperties(request, entity);
            helper.setAudit(entity);
            userRepository.save(entity);
            UserResponse userResponse = new UserResponse();
            BeanUtils.copyProperties(entity, userResponse);
            return userResponse;
        }
        throw new Exception("confirmation password is in correct...");
    }

    @Override
    public CommonResponse deleteUser(DeleteUserRequest request) throws Exception {
        Optional<UserEntity> userEntityOptional = userRepository.findByUserNameAndPassword(request.getUserName(), request.getPassword());
        if (userEntityOptional.isPresent()) {
            CommonResponse response = new CommonResponse();
            DeletedUserEntity entity = new DeletedUserEntity();
            BeanUtils.copyProperties(userEntityOptional.get(),entity);
            entity.setReason(request.getReason());
            deletedUserRepository.save(entity);
            userRepository.delete(userEntityOptional.get());
            return response;
        }
        throw new Exception("username or password is incorrect");
    }

    @Override
    public CommonResponse forgetPassword(ForgetPasswordRequest request) {
        return null;
    }

    @Override
    public CommonResponse resetPassword(PasswordResetRequest request) {
        return null;
    }

    @Override
    public UserResponse signIn(SignInRequest request) throws Exception {
        Optional<UserEntity> userEntityOptional = userRepository.findByUserNameAndPassword(request.getUserName(), request.getPassword());
        if (userEntityOptional.isPresent()) {
            UserResponse response = new UserResponse();
            BeanUtils.copyProperties(userEntityOptional.get(), response);
            return response;
        }
        throw new Exception("username or password is incorrect");
    }

    @Override
    public CommonResponse passwordUpdate(UpdatePasswordRequest request) throws Exception {
        Optional<UserEntity> userEntityOptional = userRepository.findByUserNameAndPassword(request.getUserName(), request.getCurrentPassword());
        if (userEntityOptional.isPresent()) {
            UserEntity entity = userEntityOptional.get();
            if (request.getNewPassword().equals(request.getConfirmNewPassword())) {
                entity.setPassword(request.getNewPassword());
                CommonResponse response = new CommonResponse();
                response.setResult("new password updated successfully");
                return response;
            }
            throw new Exception("new password and confirm password not match");
        }
        throw new Exception("username or password is incorrect");
    }

    @Override
    public UserResponse updateUser(UpdateUserRequest request) throws Exception {
        Optional<UserEntity> userEntityOptional = userRepository.findByUserName(request.getUserName());
        if (userEntityOptional.isPresent()) {
            UserEntity entity = userEntityOptional.get();
            BeanUtils.copyProperties(request, entity);
            helper.setAudit(entity);
            userRepository.save(entity);
            UserResponse userResponse = new UserResponse();
            BeanUtils.copyProperties(entity, userResponse);
            return userResponse;
        }
        throw new Exception("username is incorrect");
    }

    @Override
    public List<UserResponse> getUsers(GetUsersRequest request) {
        return null;
    }

    @Override
    public List<DeletedUserResponse> deletedUsersDetails() {
        return null;
    }
}
