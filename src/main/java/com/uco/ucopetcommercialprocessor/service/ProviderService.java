package com.uco.ucopetcommercialprocessor.service;

import com.uco.ucopetcommercialprocessor.domain.ProviderDomain;
import com.uco.ucopetcommercialprocessor.domain.ProviderValidationException;
import com.uco.ucopetcommercialprocessor.repository.ProviderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;
@Service
public class ProviderService {
    private final ProviderRepository providerRepository;
    public ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }
    @Transactional
    public void create(ProviderDomain incoming) {
        if (providerRepository.existsById(incoming.getId())) {
            return;
        }
        ProviderDomain provider = ProviderDomain.builder()
                .id(incoming.getId())
                .providerName(incoming.getProviderName())
                .representName(incoming.getRepresentName())
                .idType(incoming.getIdType())
                .documentNumber(incoming.getDocumentNumber())
                .mobileNumber(incoming.getMobileNumber())
                .address(incoming.getAddress())
                .email(incoming.getEmail())
                .active(true)
                .build();
        if (providerRepository.existsByIdTypeAndDocumentNumber(
                provider.getIdType(), provider.getDocumentNumber())) {
            throw new ProviderValidationException(
                    "Ya existe un proveedor con ese tipo y número de documento");
        }
        providerRepository.save(provider);
    }
    @Transactional
    public void update(ProviderDomain incoming) {
        ProviderDomain provider = providerRepository.findById(incoming.getId())
                .orElseThrow(() -> new NoSuchElementException("Proveedor no encontrado: " + incoming.getId()));
        provider.updateWith(
                incoming.getProviderName(),
                incoming.getRepresentName(),
                incoming.getIdType(),
                incoming.getDocumentNumber(),
                incoming.getMobileNumber(),
                incoming.getAddress(),
                incoming.getEmail());
        if (providerRepository.existsByIdTypeAndDocumentNumberAndIdNot(
                provider.getIdType(), provider.getDocumentNumber(), provider.getId())) {
            throw new ProviderValidationException(
                    "Ya existe un proveedor con ese tipo y número de documento");
        }
        providerRepository.save(provider);
    }
    @Transactional
    public void deactivate(ProviderDomain incoming) {
        ProviderDomain provider = providerRepository.findById(incoming.getId())
                .orElseThrow(() -> new NoSuchElementException("Proveedor no encontrado: " + incoming.getId()));
        if (!provider.isActive()) {
            return;
        }
        provider.deactivate();
        providerRepository.save(provider);
    }
    @Transactional
    public void save(ProviderDomain incoming) {
        if (providerRepository.existsById(incoming.getId())) {
            update(incoming);
        } else {
            create(incoming);
        }
    }
}
