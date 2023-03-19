package com.linkedin.profile360.model.common;

import lombok.Data;

@Data
public class Education {
    private StartsAt starts_at;
    private EndsAt ends_at;
    private String field_of_study;
    private String degree_name;
    private String school;
    private String school_linkedin_profile_url;
    private String description;
    private String logo_url;
    private String grade;
    private String activities_and_societies;
}
