package com.example.demo.repository;


import com.example.demo.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Page<Employee> findByPlace(String place, Pageable pageable);
    List<Employee> findByPlace(String place);
    Employee findFirstByCompetenciesOrderBySalaryDesc(String Competencies);
    Employee findFirstByCompetenciesOrderBySalaryAsc(String Competencies);

}
