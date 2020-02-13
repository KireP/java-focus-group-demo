package com.polarcape.licensingservice.client;

import com.polarcape.licensingservice.configuration.RequestHeadersContextHolder;
import com.polarcape.licensingservice.model.Organization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrganizationRestTemplateClient {

    private static final Logger logger = LoggerFactory.getLogger(OrganizationRestTemplateClient.class);

    private final RestTemplate restTemplate;

    public OrganizationRestTemplateClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Organization getOrganization(String organizationId) {
        logger.debug(
                "In LicensingService.getOrganization: {}. Thread ID: {}",
                RequestHeadersContextHolder.getContext().toString(),
                Thread.currentThread().getId()
        );
        logger.debug("===============================================================================");
        ResponseEntity<Organization> restExchange = restTemplate.exchange(
                "http://organizationservice/v1/organizations/{organizationId}",
                HttpMethod.GET,
                null,
                Organization.class,
                organizationId
        );
        return restExchange.getBody();
    }
}
