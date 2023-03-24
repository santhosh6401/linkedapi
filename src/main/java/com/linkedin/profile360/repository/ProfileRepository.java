package com.linkedin.profile360.repository;

import com.linkedin.profile360.model.entity.ProfileEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends MongoRepository<ProfileEntity, String> {
    List<ProfileEntity> findByBatch(String batch);

    Optional<ProfileEntity> findByEmailId(String emailId);

    Optional<ProfileEntity> findByLinkedInUrl(String linkedInUrl);
}
