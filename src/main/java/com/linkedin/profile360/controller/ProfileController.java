package com.linkedin.profile360.controller;


import com.linkedin.profile360.model.request.profile.*;
import com.linkedin.profile360.model.response.CommonResponse;
import com.linkedin.profile360.model.response.ProfileResponse;
import com.linkedin.profile360.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("")
    public ProfileResponse createProfile(CreateProfileRequest request) throws Exception {
        return profileService.createProfile(request);
    }

    @PutMapping("")
    public ProfileResponse updateProfile(UpdateProfileRequest request) throws Exception {
        return profileService.updateProfile(request);
    }

    @DeleteMapping("")
    public CommonResponse deleteProfile(DeleteProfileRequest request) throws Exception {
        return profileService.deleteProfile(request);
    }

    @GetMapping("")
    public List<ProfileResponse> getProfiles(GetProfileRequest request) throws Exception {
        return profileService.getProfiles(request);
    }

    @PutMapping("/linkedIn")
    public ProfileResponse updateByLinkedIn(UpdateProfileCallLinkedInRequest request) throws Exception {
        return profileService.updateByLinkedIn(request);
    }

    @PutMapping("/linkedIn/all")
    public List<CommonResponse> updateAllProfileByLinkedIn() throws Exception {
        return profileService.updateByAllProfile();
    }
}
