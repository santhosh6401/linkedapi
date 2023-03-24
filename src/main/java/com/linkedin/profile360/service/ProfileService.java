package com.linkedin.profile360.service;

import com.linkedin.profile360.model.request.profile.*;
import com.linkedin.profile360.model.response.CommonResponse;
import com.linkedin.profile360.model.response.ProfileResponse;

import java.util.List;

public interface ProfileService {

    ProfileResponse createProfile(CreateProfileRequest request) throws Exception;

    ProfileResponse updateProfile(UpdateProfileRequest request) throws Exception;

    List<ProfileResponse> getProfiles(GetProfileRequest request) throws Exception;

    CommonResponse deleteProfile(DeleteProfileRequest request) throws Exception;

    ProfileResponse updateByLinkedIn(UpdateProfileCallLinkedInRequest request) throws Exception;

    List<CommonResponse> updateByAllProfile() throws Exception;
}
