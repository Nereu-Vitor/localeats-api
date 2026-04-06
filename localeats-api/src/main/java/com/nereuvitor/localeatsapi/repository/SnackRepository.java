package com.nereuvitor.localeatsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nereuvitor.localeatsapi.model.Snack;

@Repository
public interface SnackRepository extends JpaRepository<Snack, Long> {

}
