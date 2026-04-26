package com.nereuvitor.localeatsapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nereuvitor.localeatsapi.models.Order;
import com.nereuvitor.localeatsapi.models.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {    
    
    List<Order> findByUser(User user);  

    List<Order> findByStatusIn(List<Integer> statusCode);
}
