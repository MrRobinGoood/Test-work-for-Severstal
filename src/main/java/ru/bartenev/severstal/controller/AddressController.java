package ru.bartenev.severstal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bartenev.severstal.entity.Address;
import ru.bartenev.severstal.service.AddressService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/addresses")
public class AddressController {
    private AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public List<Address> getAllAdresses(){
        return addressService.getAllAddresses();
    }
}
