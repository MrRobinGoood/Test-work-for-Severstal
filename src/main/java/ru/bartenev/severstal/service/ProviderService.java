package ru.bartenev.severstal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bartenev.severstal.entity.Provider;
import ru.bartenev.severstal.repository.ProviderRepository;

import java.util.List;

@Service
public class ProviderService {
    private ProviderRepository providerRepository;

    @Autowired
    public ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    public List<Provider> getAllProviders(){
        return providerRepository.findAll();
    }
}
