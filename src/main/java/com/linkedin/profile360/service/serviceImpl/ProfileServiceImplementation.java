package com.linkedin.profile360.service.serviceImpl;

import com.linkedin.profile360.exception.GeneralException;
import com.linkedin.profile360.model.common.Experience;
import com.linkedin.profile360.model.dto.LinkedInProfileDto;
import com.linkedin.profile360.model.entity.ProfileEntity;
import com.linkedin.profile360.model.request.profile.*;
import com.linkedin.profile360.model.response.CommonResponse;
import com.linkedin.profile360.model.response.ProfileResponse;
import com.linkedin.profile360.repository.ProfileRepository;
import com.linkedin.profile360.service.ProfileService;
import com.linkedin.profile360.service.rest.LinkedInService;
import com.linkedin.profile360.utils.Helper;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

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
            throw new GeneralException(HttpStatus.NOT_FOUND, "PROFILE EMAIL-ID ALREADY EXIST");
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
            throw new GeneralException(HttpStatus.NOT_FOUND, "PROFILE EMAIL-ID NOT PRESENT");
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

        Collections.reverse(entities);
        if (!CollectionUtils.isEmpty(entities)) {
            entities.forEach(profileEntity -> {
                ProfileResponse profileResponse = new ProfileResponse();
                BeanUtils.copyProperties(profileEntity, profileResponse);
                if (Objects.nonNull(profileEntity.getCompanyExperienceDetails()) && Objects.nonNull(profileEntity.getCompanyExperienceDetails().get(profileEntity.getCompanyExperienceDetails().size() - 1)))
                    profileResponse.setLatestCompanyDetails(profileEntity.getCompanyExperienceDetails().get(profileEntity.getCompanyExperienceDetails().size() - 1));
                else {
                    profileResponse.setLatestCompanyDetails(new Experience());
                }
                int currentYear = LocalDate.now().getYear();
                if (Objects.nonNull(profileEntity.getBatch())) {
                    int batch = Integer.parseInt(profileEntity.getBatch());
                    if (batch + 4 < currentYear) {
                        profileResponse.setAlumini(true);
                    }
                }
                response.add(profileResponse);
            });
            return response;
        }
        throw new GeneralException(HttpStatus.NOT_FOUND, "RECORDS NOT FOUND");
    }

    @Override
    public CommonResponse deleteProfile(DeleteProfileRequest request) throws Exception {
        Optional<ProfileEntity> profileEntityOptional = profileRepository.findByEmailId(request.getEmailId());
        if (profileEntityOptional.isEmpty()) {
            throw new GeneralException(HttpStatus.NOT_FOUND, "PROFILE EMAIL-ID NOT PRESENT");
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
            throw new GeneralException(HttpStatus.NOT_FOUND, "PROFILE NOT PRESENT || NOT ABLE TO UPDATE");
        }
        ProfileEntity entity = profileEntityOptional.get();
        LinkedInProfileDto linkedInProfileDto = linkedInService.callLinkedInApi(entity.getLinkedInUrl());
        //entity.setLinkedInProfileUrl(linkedInProfileDto.getProfile_pic_url());
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
            throw new GeneralException(HttpStatus.NOT_FOUND, "RECORDS NOT FOUND");
        }
        entities.forEach(profileEntity -> {
            CommonResponse response = new CommonResponse();
            LinkedInProfileDto linkedInProfileDto = null;
            try {
                linkedInProfileDto = linkedInService.callLinkedInApi(profileEntity.getLinkedInUrl());
            } catch (Exception e) {
                throw new GeneralException(HttpStatus.NOT_FOUND, "ERROR WHILE CALLING LINKEDIN API");
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

    @Override
    public List<String> uploadExcel(MultipartFile multipartFile) throws IOException {

        List<String> response = new ArrayList<>();
        XSSFWorkbook workBook = new XSSFWorkbook(multipartFile.getInputStream());
        XSSFSheet sheet = workBook.getSheetAt(0);

        for (int rowIndex = 0; rowIndex <= getNumberOfNonEmptyCells(sheet, 0) - 1; rowIndex++) {

            XSSFRow row = sheet.getRow(rowIndex);
            if (rowIndex == 0) {
                continue;
            }

            DataFormatter dataFormatter = new DataFormatter();
            CreateProfileRequest request = CreateProfileRequest.builder()
                    .firstname(dataFormatter.formatCellValue(row.getCell(0)))
                    .lastname(dataFormatter.formatCellValue(row.getCell(1)))
                    .department(dataFormatter.formatCellValue(row.getCell(2)))
                    .batch(dataFormatter.formatCellValue(row.getCell(3)))
                    .mobileNo(dataFormatter.formatCellValue(row.getCell(4)))
                    .emailId(dataFormatter.formatCellValue(row.getCell(5)))
                    .linkedInUrl(dataFormatter.formatCellValue(row.getCell(6)))
                    .linkedInProfileUrl(dataFormatter.formatCellValue(row.getCell(7)))
                    .build();
            UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();
            BeanUtils.copyProperties(request, updateProfileRequest);

            String result = " ";
            try {
                createProfile(request);
                result = request.getEmailId() + " - CREATED SUCCESSFULLY";
                response.add(result);
            } catch (Exception e) {
                try {
                    updateProfile(updateProfileRequest);
                    result = request.getEmailId() + " - UPDATED SUCCESSFULLY";
                } catch (Exception ex) {
                    result = ex.getMessage();
                }
                response.add(result);
            }
        }
        return response;
    }

    public static int getNumberOfNonEmptyCells(XSSFSheet sheet, int columnIndex) {
        int numOfNonEmptyCells = 0;
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            if (row != null) {
                XSSFCell cell = row.getCell(columnIndex);
                if (cell != null && cell.getCellType() != CellType.BLANK) {
                    numOfNonEmptyCells++;
                }
            }
        }
        return numOfNonEmptyCells;
    }
}
