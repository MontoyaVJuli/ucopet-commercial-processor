package com.uco.ucopetcommercialprocessor.domain.healthPlanCoverage;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uco.ucopetcommercialprocessor.domain.healthPlan.HealthPlanDomain;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "health_plan_coverages")
public class HealthPlanCoverageDomain {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_plan_id")
    private HealthPlanDomain healthPlan;

    private UUID serviceId;

    @Column(name = "procedure_id", nullable = false)
    private UUID procedureId; // <-- ¡Agregado en el procesador para que JPA lo mapee hacia Postgres!

    private Integer coveragePercentage;
    private Double coverageLimit;
    private boolean deleted;

    public HealthPlanCoverageDomain() {
    }

    // Mapea el campo "healthPlanId" del JSON de RabbitMQ hacia el objeto HealthPlan
    @JsonProperty("healthPlanId")
    public void setHealthPlanId(UUID healthPlanId) {
        if (healthPlanId != null) {
            this.healthPlan = new HealthPlanDomain();
            this.healthPlan.setId(healthPlanId);
        }
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public HealthPlanDomain getHealthPlan() { return healthPlan; }
    public void setHealthPlan(HealthPlanDomain healthPlan) { this.healthPlan = healthPlan; }

    public UUID getServiceId() { return serviceId; }
    public void setServiceId(UUID serviceId) { this.serviceId = serviceId; }

    public UUID getProcedureId() { return procedureId; } // <-- Get
    public void setProcedureId(UUID procedureId) { this.procedureId = procedureId; } // <-- Set

    public Integer getCoveragePercentage() { return coveragePercentage; }
    public void setCoveragePercentage(Integer coveragePercentage) { this.coveragePercentage = coveragePercentage; }

    public Double getCoverageLimit() { return coverageLimit; }
    public void setCoverageLimit(Double coverageLimit) { this.coverageLimit = coverageLimit; }

    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
}