package com.polarcape.licensingservice.service;

import com.polarcape.licensingservice.client.OrganizationRestTemplateClient;
import com.polarcape.licensingservice.model.License;
import com.polarcape.licensingservice.model.Organization;
import com.polarcape.licensingservice.repository.LicenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LicenseService {

    private final LicenseRepository licenseRepository;
    private final OrganizationRestTemplateClient organizationRestTemplateClient;

    public LicenseService(LicenseRepository licenseRepository, OrganizationRestTemplateClient organizationRestTemplateClient) {
        this.licenseRepository = licenseRepository;
        this.organizationRestTemplateClient = organizationRestTemplateClient;
    }

    public List<License> getAllLicences() {
        return licenseRepository.findAll();
    }

    public License getLicense(String licenceId) {
        License license = licenseRepository.findByLicenseId(licenceId);
        Organization organization = organizationRestTemplateClient.getOrganization(license.getOrganizationId());
        return license
                .withOrganizationName(organization.getName())
                .withContactName(organization.getContactName())
                .withContactEmail(organization.getContactEmail())
                .withContactPhone(organization.getContactPhone());
    }

    public List<License> getLicensesByOrganizationId(String organizationId) {
        return licenseRepository.findByOrganizationId(organizationId);
    }

    public License saveLicense(License license) {
        license.setLicenseId(UUID.randomUUID().toString());
        return licenseRepository.save(license);
    }

    public License updateLicense(License license) {
        return licenseRepository.save(license);
    }

    public void deleteLicense(String licenseId) {
        licenseRepository.deleteById(licenseId);
    }
}
