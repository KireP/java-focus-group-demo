package com.polarcape.organizationservice.rest;

import com.polarcape.organizationservice.configuration.RequestHeadersContextHolder;
import com.polarcape.organizationservice.model.Organization;
import com.polarcape.organizationservice.service.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/organizations")
public class OrganizationController {

    private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping
    public List<Organization> getAllOrganizations() {
        return organizationService.getAllOrganizations();
    }

    @GetMapping(value = "/{organizationId}")
    public Organization getOrganization(@PathVariable("organizationId") String organizationId) {
        logger.debug("In OrganizationController.getOrganization: {}. Thread ID: {}", RequestHeadersContextHolder.getContext().toString(), Thread.currentThread().getId());
        logger.debug("Looking up data for organization {}", organizationId);
        logger.debug("===============================================================================");

        return organizationService.getOrganization(organizationId);
    }

    @PostMapping
    public Organization saveOrganization(@RequestBody Organization organization) {
        return organizationService.saveOrganization(organization);
    }

    @PutMapping
    public Organization updateOrganization(@RequestBody Organization organization) {
        return organizationService.updateOrganization(organization);
    }

    @DeleteMapping(value = "/{organizationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization(@PathVariable("organizationId") String organizationId) {
        organizationService.deleteOrganization(organizationId);
    }
}

