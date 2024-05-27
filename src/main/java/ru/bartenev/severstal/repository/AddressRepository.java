package ru.bartenev.severstal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bartenev.severstal.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
