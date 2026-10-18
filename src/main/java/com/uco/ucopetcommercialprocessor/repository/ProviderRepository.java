package com.uco.ucopetcommercialprocessor.repository;

import com.uco.ucopetcommercialprocessor.domain.DocumentType;
import com.uco.ucopetcommercialprocessor.domain.ProviderDomain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProviderRepository extends JpaRepository<ProviderDomain, UUID> {
    boolean existsByIdTypeAndDocumentNumber(DocumentType idType, String documentNumber);
    boolean existsByIdTypeAndDocumentNumberAndIdNot(DocumentType idType, String documentNumber, UUID id);
}
