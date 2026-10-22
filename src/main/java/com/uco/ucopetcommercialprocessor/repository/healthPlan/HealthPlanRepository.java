package com.uco.ucopetcommercialprocessor.repository.healthPlan;

import com.uco.ucopetcommercialprocessor.domain.healthPlan.HealthPlanDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HealthPlanRepository extends JpaRepository<HealthPlanDomain, UUID> {
}