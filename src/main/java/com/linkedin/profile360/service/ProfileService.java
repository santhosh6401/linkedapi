package com.linkedin.profile360.service;

import com.linkedin.profile360.model.request.profile.*;
import com.linkedin.profile360.model.response.CommonResponse;
import com.linkedin.profile360.model.response.ProfileResponse;

import java.util.List;

public interface ProfileService {

    ProfileResponse createProfile(CreateProfileRequest request);

    ProfileResponse updateProfile(UpdateProfileRequest request);

    List<ProfileResponse> getProfiles(GetProfileRequest request);

    CommonResponse deleteProfile(DeleteProfileRequest request);

    ProfileResponse updateByLinkedIn(UpdateProfileCallLinkedInRequest request);

    List<CommonResponse> updateByAllProfile();
}
