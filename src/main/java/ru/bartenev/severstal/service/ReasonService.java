package ru.bartenev.severstal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bartenev.severstal.entity.Reason;
import ru.bartenev.severstal.exception.InvalidParametersException;
import ru.bartenev.severstal.repository.ReasonRepository;

@Service
public class ReasonService {
    private ReasonRepository reasonRepository;

    @Autowired
    public ReasonService(ReasonRepository reasonRepository) {
        this.reasonRepository = reasonRepository;
    }

    public Reason getReasonById(Long id) {
        return reasonRepository.findById(id).orElseThrow(() -> new InvalidParametersException("Invalid request body parameters. Reason with id: " + id + " does not exists."));
    }
}
