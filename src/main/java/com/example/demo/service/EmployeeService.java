package com.example.demo.service;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public Map<String, String> getSalaryRanges(String competency){
        Map<String, String> salaryRange = new HashMap<>();
        salaryRange.put("minSalary", employeeRepository.findFirstByCompetenciesOrderBySalaryAsc(competency).getSalary());
        salaryRange.put("maxSalary", employeeRepository.findFirstByCompetenciesOrderBySalaryDesc(competency).getSalary());
        return salaryRange;
    }

    public Page<Employee> getEmployeesByPlace(String place, Pageable pageable){
        return employeeRepository.findByPlace(place, pageable);
    }

    public void updateSalary(String place, Float percentage){
        List<Employee> employeeList = employeeRepository.findByPlace(place);

        employeeList.forEach(e -> e.setSalary(Long.parseLong(StringUtils.chop(e.getSalary()))
                + Long.parseLong(StringUtils.chop(e.getSalary())) * (percentage/100f)+ "$"));

        employeeRepository.saveAll(employeeList);
    }
}
