package com.polarcape.licensingservice.repository;

import com.polarcape.licensingservice.model.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseRepository extends JpaRepository<License, String> {

    License findByLicenseId(String licenseId);

    List<License> findByOrganizationId(String organizationId);
}
