package com.nereuvitor.localeatsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nereuvitor.localeatsapi.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
