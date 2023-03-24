package com.linkedin.profile360.service.serviceImpl;


import com.linkedin.profile360.model.entity.DeletedUserEntity;
import com.linkedin.profile360.model.entity.UserEntity;
import com.linkedin.profile360.model.request.user.*;
import com.linkedin.profile360.model.response.CommonResponse;
import com.linkedin.profile360.model.response.UserResponse;
import com.linkedin.profile360.repository.DeletedUserRepository;
import com.linkedin.profile360.repository.UserRepository;
import com.linkedin.profile360.service.UserService;
import com.linkedin.profile360.utils.Helper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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
            throw new Exception("USER NAME ALREADY EXIST");
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
        throw new Exception("CONFIRMATION PASSWORD IS IN CORRECT...");
    }

    @Override
    public CommonResponse deleteUser(DeleteUserRequest request) throws Exception {
        Optional<UserEntity> userEntityOptional = userRepository.findByUserNameAndPassword(request.getUserName(), request.getPassword());
        if (userEntityOptional.isPresent()) {
            CommonResponse response = new CommonResponse();
            DeletedUserEntity entity = new DeletedUserEntity();
            BeanUtils.copyProperties(userEntityOptional.get(), entity);
            entity.setReason(request.getReason());
            deletedUserRepository.save(entity);
            userRepository.delete(userEntityOptional.get());
            return response;
        }
        throw new Exception("username or password is incorrect");
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
                userRepository.save(entity);
                CommonResponse response = new CommonResponse();
                response.setResult("NEW PASSWORD UPDATED SUCCESSFULLY");
                return response;
            }
            throw new Exception("NEW PASSWORD AND CONFIRM PASSWORD NOT MATCH");
        }
        throw new Exception("USERNAME OR PASSWORD IS INCORRECT");
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
        throw new Exception("USERNAME IS INCORRECT");
    }

    @Override
    public List<UserResponse> getUsers(GetUsersRequest request) {
        List<UserEntity> entities = userRepository.findAll();
        if (!CollectionUtils.isEmpty(entities)) {
            List<UserResponse> responses = new ArrayList<>();
            entities.forEach(userEntity -> {
                UserResponse response = new UserResponse();
                BeanUtils.copyProperties(userEntity, response);
                responses.add(response);
            });
            return responses;
        }
        return null;
    }
}
