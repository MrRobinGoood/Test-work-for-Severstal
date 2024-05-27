package ru.bartenev.severstal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bartenev.severstal.entity.Address;
import ru.bartenev.severstal.repository.AddressRepository;


import java.util.List;

@Service
public class AddressService {
    private AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> getAllAddresses(){
        return addressRepository.findAll();
    }
}
