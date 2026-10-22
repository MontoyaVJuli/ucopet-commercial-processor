package com.uco.ucopetcommercialprocessor.service.healthPlanCoverage;

import com.uco.ucopetcommercialprocessor.domain.healthPlanCoverage.HealthPlanCoverageDomain;
import com.uco.ucopetcommercialprocessor.repository.healthPlanCoverage.HealthPlanCoverageRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class HealthPlanCoverageService {

    private final HealthPlanCoverageRepository coverageRepository;

    public HealthPlanCoverageService(HealthPlanCoverageRepository coverageRepository) {
        this.coverageRepository = coverageRepository;
    }

    @Transactional
    public void create(HealthPlanCoverageDomain incoming) {
        if (coverageRepository.existsById(incoming.getId())) {
            return;
        }
        coverageRepository.save(incoming);
    }

    @Transactional
    public void update(HealthPlanCoverageDomain incoming) {
        HealthPlanCoverageDomain coverage = coverageRepository.findById(incoming.getId())
                .orElseThrow(() -> new NoSuchElementException("Cobertura no encontrada: " + incoming.getId()));

        coverage.setCoveragePercentage(incoming.getCoveragePercentage());
        coverage.setCoverageLimit(incoming.getCoverageLimit());
        coverage.setServiceId(incoming.getServiceId());

        coverageRepository.save(coverage);
    }

    @Transactional
    public void deactivate(HealthPlanCoverageDomain incoming) {
        HealthPlanCoverageDomain coverage = coverageRepository.findById(incoming.getId())
                .orElseThrow(() -> new NoSuchElementException("Cobertura no encontrada: " + incoming.getId()));

        coverage.setDeleted(true);
        coverageRepository.save(coverage);
    }

    @Transactional
    public void save(HealthPlanCoverageDomain incoming) {
        if (coverageRepository.existsById(incoming.getId())) {
            update(incoming);
        } else {
            create(incoming);
        }
    }
}