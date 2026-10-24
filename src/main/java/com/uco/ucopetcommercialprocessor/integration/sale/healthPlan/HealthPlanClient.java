package com.uco.ucopetcommercialprocessor.integration.sale.healthPlan;

import com.uco.ucopetcommercialprocessor.integration.sale.healthPlan.HealthPlanResponse;

import java.util.UUID;

public interface HealthPlanClient {

    HealthPlanResponse getHealthPlan(UUID healthPlanId);
}
