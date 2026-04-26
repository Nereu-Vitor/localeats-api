package com.nereuvitor.localeatsapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nereuvitor.localeatsapi.models.Drink;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, Long> {

}
