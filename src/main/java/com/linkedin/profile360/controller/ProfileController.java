package com.linkedin.profile360.controller;


import com.linkedin.profile360.model.entity.ProfileEntity;
import com.linkedin.profile360.model.request.profile.*;
import com.linkedin.profile360.model.response.CommonResponse;
import com.linkedin.profile360.model.response.ProfileResponse;
import com.linkedin.profile360.repository.ProfileRepository;
import com.linkedin.profile360.service.ProfileService;
import com.linkedin.profile360.service.exporter.ExcelExporter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileRepository profileRepository;

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

    @PostMapping("generate/excel")
    public CommonResponse readForExcel(HttpServletResponse response) throws IOException {
        CommonResponse commonResponse = new CommonResponse();
        try {
            response.setContentType("application/octet-stream");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            String currentDateTime = dateFormatter.format(new Date());
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=profile-" + currentDateTime + ".xlsx";
            response.setHeader(headerKey, headerValue);

            List<ProfileEntity> entities = profileRepository.findAll();
            ExcelExporter excelExporter = new ExcelExporter(entities);
            excelExporter.export(response);

            commonResponse.setResult("EXPORTED SUCCESSFULLY");
            return commonResponse;
        } catch (Exception e) {
            commonResponse.setResult("EXPORTED FAILED " + e.getMessage());
            return commonResponse;
        }
    }

    @PostMapping(value = "/upload/excel" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public List<String> uploadExcelSheet(@RequestParam(value = "file", required = false) @RequestPart() @NonNull MultipartFile multipartFile) throws Exception {
        return profileService.uploadExcel(multipartFile);
    }
}
