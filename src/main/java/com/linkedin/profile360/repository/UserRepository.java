package com.linkedin.profile360.repository;

import com.linkedin.profile360.model.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findByUserName(String userName);

    Optional<UserEntity> findByUserNameAndPassword(String userName, String password);
}
