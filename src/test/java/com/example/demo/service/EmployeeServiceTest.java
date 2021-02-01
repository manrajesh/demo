package com.example.demo.service;

import com.example.demo.DemoApplicationTests;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeServiceTest extends DemoApplicationTests  {

    @MockBean
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeService employeeService;

    @Test
    public void whenGetSalaryRanges_validArguments_shouldReturnOk() throws Exception {

        Employee employee = new Employee();
        employee.setSalary("1000$");
        employee.setBusinessUnit("Bank");
        employee.setCompetencies("Developer");
        employee.setEmployeeId(1L);
        employee.setEmployeeName("test_user");
        employee.setPlace("Australia");
        employee.setSupervisorId(12345L);

        Employee employee2 = new Employee();
        employee2.setSalary("5000$");
        employee2.setBusinessUnit("Bank");
        employee2.setCompetencies("Developer");
        employee2.setEmployeeId(2L);
        employee2.setEmployeeName("test_user_2");
        employee2.setPlace("Australia");
        employee2.setSupervisorId(12346L);

        Map<String, String> salaryRange = new HashMap<>();
        salaryRange.put("minSalary", "1000$");
        salaryRange.put("maxSalary", "5000$");

        Mockito.when(employeeRepository.findFirstByCompetenciesOrderBySalaryAsc("Developer")).thenReturn(employee);
        Mockito.when(employeeRepository.findFirstByCompetenciesOrderBySalaryDesc("Developer")).thenReturn(employee2);
        Map<String, String> range = employeeService.getSalaryRanges("Developer");
        //Assert
        Mockito.verify(employeeRepository, Mockito.times(1)).findFirstByCompetenciesOrderBySalaryDesc("Developer");
        Mockito.verify(employeeRepository, Mockito.times(1)).findFirstByCompetenciesOrderBySalaryAsc("Developer");
        Assertions.assertEquals(salaryRange, range);
    }

    @Test
    public void whenUpdateSalary_validArguments_shouldReturnOk() throws Exception {
        Employee employee = new Employee();
        employee.setSalary("1000$");
        employee.setBusinessUnit("Bank");
        employee.setCompetencies("Developer");
        employee.setEmployeeId(1L);
        employee.setEmployeeName("test_user");
        employee.setPlace("Australia");
        employee.setSupervisorId(12345L);

        Employee employee2 = new Employee();
        employee2.setSalary("5000$");
        employee2.setBusinessUnit("Bank");
        employee2.setCompetencies("Developer");
        employee2.setEmployeeId(2L);
        employee2.setEmployeeName("test_user_2");
        employee2.setPlace("Australia");
        employee2.setSupervisorId(12346L);

        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        employeeList.add(employee2);

        Mockito.when(employeeRepository.findByPlace("Australia")).thenReturn(employeeList);
        employeeService.updateSalary("Australia", 33f);
        //Assert
        Mockito.verify(employeeRepository, Mockito.times(1)).findByPlace("Australia");
    }
}
