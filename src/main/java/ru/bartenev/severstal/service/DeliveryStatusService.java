package ru.bartenev.severstal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bartenev.severstal.entity.DeliveryStatus;
import ru.bartenev.severstal.entity.Reason;
import ru.bartenev.severstal.exception.ComplaintNotFoundException;
import ru.bartenev.severstal.exception.DeliveryStatusNotFoundException;
import ru.bartenev.severstal.exception.InvalidParametersException;
import ru.bartenev.severstal.repository.DeliveryStatusRepository;

import java.util.List;

@Service
public class DeliveryStatusService {
    private DeliveryStatusRepository deliveryStatusRepository;

    @Autowired
    public DeliveryStatusService(DeliveryStatusRepository deliveryStatusRepository) {
        this.deliveryStatusRepository = deliveryStatusRepository;
    }

    public DeliveryStatus getDeliveryStatusById(Long id) {
        return deliveryStatusRepository.findById(id).orElseThrow(() -> new DeliveryStatusNotFoundException("Delivery status with id: " + id + " not found."));
    }

    public List<DeliveryStatus> getAllDeliveryStatuses(){
        return deliveryStatusRepository.findAll();
    }
}
