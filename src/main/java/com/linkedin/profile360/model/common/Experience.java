package com.linkedin.profile360.model.common;


import lombok.Data;

@Data
public class Experience {
    private StartsAt starts_at;
    private EndsAt ends_at;
    private String company;
    private String company_linkedin_profile_url;
    private String title;
    private String description;
    private String location;
    private String logo_url;
}
