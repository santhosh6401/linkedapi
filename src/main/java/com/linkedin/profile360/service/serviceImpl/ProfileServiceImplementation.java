package com.linkedin.profile360.service.serviceImpl;

import com.linkedin.profile360.model.dto.LinkedInProfileDto;
import com.linkedin.profile360.model.entity.ProfileEntity;
import com.linkedin.profile360.model.request.profile.*;
import com.linkedin.profile360.model.response.CommonResponse;
import com.linkedin.profile360.model.response.ProfileResponse;
import com.linkedin.profile360.repository.ProfileRepository;
import com.linkedin.profile360.service.ProfileService;
import com.linkedin.profile360.service.rest.LinkedInService;
import com.linkedin.profile360.utils.Helper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileServiceImplementation implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private Helper helper;
    @Autowired
    private LinkedInService linkedInService;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public ProfileResponse createProfile(CreateProfileRequest request) throws Exception {
        Optional<ProfileEntity> profileEntityOptional = profileRepository.findByEmailId(request.getEmailId());
        if (profileEntityOptional.isPresent()) {
            throw new Exception("PROFILE EMAIL-ID ALREADY EXIST");
        }
        ProfileEntity entity = new ProfileEntity();
        BeanUtils.copyProperties(request, entity);
        helper.setAudit(entity);
        profileRepository.save(entity);
        ProfileResponse response = new ProfileResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    @Override
    public ProfileResponse updateProfile(UpdateProfileRequest request) throws Exception {
        Optional<ProfileEntity> profileEntityOptional = profileRepository.findByEmailId(request.getEmailId());
        if (profileEntityOptional.isEmpty()) {
            throw new Exception("PROFILE EMAIL-ID NOT PRESENT");
        }
        ProfileEntity entity = profileEntityOptional.get();
        BeanUtils.copyProperties(request, entity);
        helper.setAudit(entity);
        profileRepository.save(entity);
        ProfileResponse response = new ProfileResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    @Override
    public List<ProfileResponse> getProfiles(GetProfileRequest request) throws Exception {

        List<ProfileResponse> response = new ArrayList<>();

        Query query = new Query();
        if (!ObjectUtils.isEmpty(request.getEmailId())) {
            query.addCriteria(Criteria.where("emailId").is(request.getEmailId()));
        }
        if (!ObjectUtils.isEmpty(request.getBatch())) {
            query.addCriteria(Criteria.where("batch").is(request.getBatch()));
        }
        if (!ObjectUtils.isEmpty(request.getDepartment())) {
            query.addCriteria(Criteria.where("department").is(request.getDepartment()));
        }

        List<ProfileEntity> entities = mongoTemplate.find(query, ProfileEntity.class);

        if (!CollectionUtils.isEmpty(entities)) {
            entities.forEach(profileEntity -> {
                ProfileResponse profileResponse = new ProfileResponse();
                BeanUtils.copyProperties(profileEntity, profileResponse);
                response.add(profileResponse);
            });
            return response;
        }
        throw new Exception("RECORDS NOT FOUND");
    }

    @Override
    public CommonResponse deleteProfile(DeleteProfileRequest request) throws Exception {
        Optional<ProfileEntity> profileEntityOptional = profileRepository.findByEmailId(request.getEmailId());
        if (profileEntityOptional.isEmpty()) {
            throw new Exception("PROFILE EMAIL-ID NOT PRESENT");
        }
        ProfileEntity entity = profileEntityOptional.get();
        profileRepository.delete(entity);
        CommonResponse response = new CommonResponse();
        response.setResult(request.getEmailId() + " DELETED SUCCESSFULLY ");
        return response;
    }

    @Override
    public ProfileResponse updateByLinkedIn(UpdateProfileCallLinkedInRequest request) throws Exception {
        Optional<ProfileEntity> profileEntityOptional = profileRepository.findByEmailId(request.getEmailId());
        ProfileResponse response = new ProfileResponse();
        if (profileEntityOptional.isEmpty()) {
            throw new Exception("PROFILE NOT PRESENT || NOT ABLE TO UPDATE");
        }
        ProfileEntity entity = profileEntityOptional.get();
        LinkedInProfileDto linkedInProfileDto = linkedInService.callLinkedInApi(entity.getLinkedInUrl());
        entity.setLinkedInProfileUrl(linkedInProfileDto.getProfile_pic_url());
        entity.setCompanyExperienceDetails(linkedInProfileDto.getExperiences());
        entity.setCurrentOccupation(linkedInProfileDto.getOccupation());
        entity.setWorkedCompaniesCount(String.valueOf(linkedInProfileDto.getExperiences().size()));
        entity.setEducationDetails(linkedInProfileDto.getEducation());
        profileRepository.save(entity);
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    @Override
    public List<CommonResponse> updateByAllProfile() throws Exception {
        List<ProfileEntity> entities = profileRepository.findAll();
        List<CommonResponse> responses = new ArrayList<>();
        if (CollectionUtils.isEmpty(entities)) {
            throw new Exception("RECORDS NOT FOUND");
        }
        entities.forEach(profileEntity -> {
            CommonResponse response = new CommonResponse();
            LinkedInProfileDto linkedInProfileDto = null;
            try {
                linkedInProfileDto = linkedInService.callLinkedInApi(profileEntity.getLinkedInUrl());
            } catch (Exception e) {
                throw new RuntimeException("ERROR WHILE CALLING LINKEDIN API");
            }
            profileEntity.setLinkedInProfileUrl(linkedInProfileDto.getProfile_pic_url());
            profileEntity.setCompanyExperienceDetails(linkedInProfileDto.getExperiences());
            profileEntity.setCurrentOccupation(linkedInProfileDto.getOccupation());
            profileEntity.setWorkedCompaniesCount(String.valueOf(linkedInProfileDto.getExperiences().size()));
            profileEntity.setEducationDetails(linkedInProfileDto.getEducation());
            profileRepository.save(profileEntity);
            response.setResult(profileEntity.getEmailId() + " UPDATED SUCCESSFULLY");
            responses.add(response);
        });
        return responses;
    }
}
