package com.linkedin.profile360.repository;

import com.linkedin.profile360.model.entity.ProfileEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends MongoRepository<ProfileEntity,String> {
}
