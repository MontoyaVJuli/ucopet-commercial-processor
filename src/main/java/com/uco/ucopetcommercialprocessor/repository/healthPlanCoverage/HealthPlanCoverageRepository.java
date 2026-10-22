package com.uco.ucopetcommercialprocessor.repository.healthPlanCoverage;

import com.uco.ucopetcommercialprocessor.domain.healthPlanCoverage.HealthPlanCoverageDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HealthPlanCoverageRepository extends JpaRepository<HealthPlanCoverageDomain, UUID> {
}