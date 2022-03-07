package com.polarcape.licensingservice.rest;

import com.polarcape.licensingservice.client.Client;
import com.polarcape.licensingservice.model.License;
import com.polarcape.licensingservice.service.LicenseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/licences")
public class LicenseController {

    private final LicenseService licenseService;

    public LicenseController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @GetMapping
    public List<License> getAllLicenses() {
        return licenseService.getAllLicences();
    }

    @GetMapping(value = "/organization/{organizationId}")
    public List<License> getLicencesByOrganizationId(@PathVariable("organizationId") String organizationId) {
        return licenseService.getLicensesByOrganizationId(organizationId);
    }

    @GetMapping(value = "/{licenseId}")
    public License getLicense(@PathVariable("licenseId") String licenseId, @RequestParam(value = "client", required = false) Client client) {
        return licenseService.getLicense(licenseId, client);
    }

    @PostMapping
    public License saveLicense(@RequestBody License license) {
        return licenseService.saveLicense(license);
    }

    @PutMapping
    public License updateLicense(@RequestBody License license) {
        return licenseService.updateLicense(license);
    }

    @DeleteMapping(value = "/{licenseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLicense(@PathVariable("licenseId") String licenseId) {
        licenseService.deleteLicense(licenseId);
    }
}
