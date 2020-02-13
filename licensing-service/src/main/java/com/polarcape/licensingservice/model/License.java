package com.polarcape.licensingservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "licenses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class License {

    @Id
    @Column(name = "license_id", nullable = false)
    private String licenseId;

    @Column(name = "organization_id", nullable = false)
    private String organizationId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "license_type", nullable = false)
    private String licenseType;

    @Column(name = "license_max", nullable = false)
    private Integer licenseMax;

    @Column(name = "license_allocated", nullable = false)
    private Integer licenseAllocated;

    @Column(name = "comment")
    private String comment;

    @Transient
    @With(AccessLevel.PUBLIC)
    private String organizationName;

    @Transient
    @With(AccessLevel.PUBLIC)
    private String contactName;

    @Transient
    @With(AccessLevel.PUBLIC)
    private String contactPhone;

    @Transient
    @With(AccessLevel.PUBLIC)
    private String contactEmail;
}
