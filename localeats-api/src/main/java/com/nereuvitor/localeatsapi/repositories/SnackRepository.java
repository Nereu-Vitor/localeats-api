package com.nereuvitor.localeatsapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nereuvitor.localeatsapi.models.Snack;

@Repository
public interface SnackRepository extends JpaRepository<Snack, Long> {

}
