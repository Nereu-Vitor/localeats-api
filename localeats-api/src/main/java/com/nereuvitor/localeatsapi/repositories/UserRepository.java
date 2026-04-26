package com.nereuvitor.localeatsapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nereuvitor.localeatsapi.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
