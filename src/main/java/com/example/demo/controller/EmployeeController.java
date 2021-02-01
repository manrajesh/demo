package com.example.demo.controller;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("employee")
@Slf4j
@Validated
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getAllEmployees(){
        log.info("++++++ In get All Employees ++++++++");
        return employeeService.getAllEmployees();
    }

    @GetMapping(value = "range", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getSalaryRanges(@RequestParam @NotBlank(message = "competency is required.") String competency){
        log.info("++++++ In getSalaryRanges ++++++++");
        return employeeService.getSalaryRanges(competency);
    }

    @GetMapping(value = "{place}/{pageNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Employee> getEmployeesByPlace(@PathVariable @NotBlank(message = "place is required.") String place,
                                              @PathVariable @Min(0) Integer pageNumber){
        log.info("++++++ In getEmployeesByPlace ++++++++");
        Pageable pageable = PageRequest.of(pageNumber, 5);
        return employeeService.getEmployeesByPlace(place, pageable);
    }

    @PutMapping(value = "place/{place}/salary/{percentage}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateSalary(@PathVariable @NotBlank(message = "place is required.") String place,
                             @PathVariable @Max(value = 55, message = "percentage should not be more than 55.") Float percentage){
        log.info("++++++ In updateSalary ++++++++");
        employeeService.updateSalary(place, percentage);
    }
}
