package com.nereuvitor.localeatsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nereuvitor.localeatsapi.model.Drink;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, Long> {

}
