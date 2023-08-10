package com.imaginnovate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imaginnovate.domain.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {

}
