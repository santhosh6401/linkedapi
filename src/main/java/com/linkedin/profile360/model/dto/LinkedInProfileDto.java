package com.linkedin.profile360.model.dto;

import com.linkedin.profile360.model.common.Activity;
import com.linkedin.profile360.model.common.Education;
import com.linkedin.profile360.model.common.Experience;
import com.linkedin.profile360.model.common.SimilarlyNamedProfile;
import lombok.Data;

import java.util.List;

@Data
public class LinkedInProfileDto {
    private String public_identifier;
    private String profile_pic_url;
    private String background_cover_image_url;
    private String first_name;
    private String last_name;
    private String full_name;
    private int follower_count;
    private String occupation;
    private String headline;
    private Object summary;
    private String country;
    private String country_full_name;
    private String city;
    private String state;
    private List<Experience> experiences;
    private List<Education> education;
    private List<Object> languages;
    private List<Object> accomplishment_organisations;
    private List<Object> accomplishment_publications;
    private List<Object> accomplishment_honors_awards;
    private List<Object> accomplishment_patents;
    private List<Object> accomplishment_courses;
    private List<Object> accomplishment_projects;
    private List<Object> accomplishment_test_scores;
    private List<Object> volunteer_work;
    private List<Object> certifications;
    private int connections;
    private List<Object> people_also_viewed;
    private List<Object> recommendations;
    private List<Activity> activities;
    private List<SimilarlyNamedProfile> similarly_named_profiles;
    private List<Object> articles;
    private List<Object> groups;
}
