package ru.bartenev.severstal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bartenev.severstal.dto.PaginatedComplaintsDTO;
import ru.bartenev.severstal.entity.Reason;
import ru.bartenev.severstal.service.ReasonService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/reasons")
public class ReasonController {
    private ReasonService reasonService;

    @Autowired
    public ReasonController(ReasonService reasonService) {
        this.reasonService = reasonService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public List<Reason> getAllReasons() {
        return reasonService.getAllReasons();
    }
}
