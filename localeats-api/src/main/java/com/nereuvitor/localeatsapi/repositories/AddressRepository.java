package com.nereuvitor.localeatsapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nereuvitor.localeatsapi.models.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
