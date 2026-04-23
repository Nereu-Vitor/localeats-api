package com.nereuvitor.localeatsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nereuvitor.localeatsapi.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
