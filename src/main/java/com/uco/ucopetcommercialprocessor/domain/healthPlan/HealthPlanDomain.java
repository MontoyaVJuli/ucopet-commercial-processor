package com.uco.ucopetcommercialprocessor.domain.healthPlan;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "health_plans")
public class HealthPlanDomain {

    @Id
    private UUID id;

    private String name;
    private String insuranceCompany;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    private boolean deleted;

    public enum Status {
        ACTIVA, INACTIVA
    }

    public HealthPlanDomain() {
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getInsuranceCompany() { return insuranceCompany; }
    public void setInsuranceCompany(String insuranceCompany) { this.insuranceCompany = insuranceCompany; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
}