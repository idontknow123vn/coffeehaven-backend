package com.altair12d.coffeehaven.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altair12d.coffeehaven.entity.Employee;

@Repository
public interface EmployeeRepos extends JpaRepository<Employee, Integer> {
    Employee findByUsername(String username);
    Optional<Employee> findByEmail(String email);
    Employee findByUsernameAndPasswordHash(String username, String passwordHash);
    Optional<Employee> findByEmailAndPasswordHash(String email, String passwordHash);
    Employee findByUsernameAndEmail(String username, String email);
}
