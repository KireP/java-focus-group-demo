package com.polarcape.organizationservice.service;

import com.polarcape.organizationservice.model.Organization;
import com.polarcape.organizationservice.repository.OrganizationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    public Organization getOrganization(String organizationId) {
        return organizationRepository.findById(organizationId).orElse(null);
    }

    public Organization saveOrganization(Organization organization) {
        organization.setId(UUID.randomUUID().toString());
        return organizationRepository.save(organization);
    }

    public Organization updateOrganization(Organization organization) {
        return organizationRepository.save(organization);
    }

    public void deleteOrganization(String organizationId) {
        organizationRepository.deleteById(organizationId);
    }
}

