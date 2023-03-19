package com.linkedin.profile360.service.serviceImpl;

import com.linkedin.profile360.model.request.profile.*;
import com.linkedin.profile360.model.response.CommonResponse;
import com.linkedin.profile360.model.response.ProfileResponse;
import com.linkedin.profile360.service.ProfileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileServiceImplementation implements ProfileService {

    @Override
    public ProfileResponse createProfile(CreateProfileRequest request) {
        return null;
    }

    @Override
    public ProfileResponse updateProfile(UpdateProfileRequest request) {
        return null;
    }

    @Override
    public List<ProfileResponse> getProfiles(GetProfileRequest request) {
        return null;
    }

    @Override
    public CommonResponse deleteProfile(DeleteProfileRequest request) {
        return null;
    }

    @Override
    public ProfileResponse updateByLinkedIn(UpdateProfileCallLinkedInRequest request) {
        return null;
    }

    @Override
    public List<CommonResponse> updateByAllProfile() {
        return null;
    }
}
