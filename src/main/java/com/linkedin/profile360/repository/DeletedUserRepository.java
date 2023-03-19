package com.linkedin.profile360.repository;

import com.linkedin.profile360.model.entity.DeletedUserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeletedUserRepository extends MongoRepository<DeletedUserEntity, String> {
}
