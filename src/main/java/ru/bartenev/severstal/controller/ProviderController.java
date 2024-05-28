package ru.bartenev.severstal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bartenev.severstal.entity.Provider;
import ru.bartenev.severstal.service.ProviderService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/providers")
public class ProviderController {
    private ProviderService providerService;

    @Autowired
    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public List<Provider> getAllProviders(){
        return providerService.getAllProviders();
    }
}
