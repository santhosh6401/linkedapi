package com.linkedin.profile360.service.rest;

import com.linkedin.profile360.config.ConfigProperties;
import com.linkedin.profile360.model.dto.LinkedInProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class LinkedInService {

    RestTemplate restTemplate = new RestTemplate();


    private final ConfigProperties configProperties;

    public LinkedInProfileDto callLinkedInApi(String linkedInUrl) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + configProperties.getNubelaSecretKey());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        URI uri = UriComponentsBuilder.fromUriString(configProperties.getLinkedInUrl())
                .queryParam("url", linkedInUrl)
                .buildAndExpand().toUri();

        try {
            return restTemplate.exchange(uri,
                    HttpMethod.GET,
                    entity,
                    LinkedInProfileDto.class).getBody();
        } catch (Exception e) {
            throw new Exception("ERROR WHILE CALLING THE LINKEDIN API - " + e.getMessage());
        }
    }


}
