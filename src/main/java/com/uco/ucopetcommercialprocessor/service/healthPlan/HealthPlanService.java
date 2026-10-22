package com.uco.ucopetcommercialprocessor.service.healthPlan;

import com.uco.ucopetcommercialprocessor.domain.healthPlan.HealthPlanDomain;
import com.uco.ucopetcommercialprocessor.repository.healthPlan.HealthPlanRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class HealthPlanService {

    private final HealthPlanRepository healthPlanRepository;

    public HealthPlanService(HealthPlanRepository healthPlanRepository) {
        this.healthPlanRepository = healthPlanRepository;
    }

    @Transactional
    public void create(HealthPlanDomain incoming) {
        if (healthPlanRepository.existsById(incoming.getId())) {
            return;
        }
        healthPlanRepository.save(incoming);
    }

    @Transactional
    public void update(HealthPlanDomain incoming) {
        HealthPlanDomain healthPlan = healthPlanRepository.findById(incoming.getId())
                .orElseThrow(() -> new NoSuchElementException("Plan de salud no encontrado: " + incoming.getId()));

        healthPlan.setName(incoming.getName());
        healthPlan.setDescription(incoming.getDescription());
        healthPlan.setStatus(incoming.getStatus());
        healthPlan.setInsuranceCompany(incoming.getInsuranceCompany());

        healthPlanRepository.save(healthPlan);
    }

    @Transactional
    public void deactivate(HealthPlanDomain incoming) {
        HealthPlanDomain healthPlan = healthPlanRepository.findById(incoming.getId())
                .orElseThrow(() -> new NoSuchElementException("Plan de salud no encontrado: " + incoming.getId()));

        healthPlan.setDeleted(true);
        healthPlan.setStatus(HealthPlanDomain.Status.INACTIVA);

        healthPlanRepository.save(healthPlan);
    }

    @Transactional
    public void save(HealthPlanDomain incoming) {
        if (healthPlanRepository.existsById(incoming.getId())) {
            update(incoming);
        } else {
            create(incoming);
        }
    }
}