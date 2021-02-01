package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Employee")
@Setter
@Getter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EmployeeID")
    private Long employeeId;

    @Column(name = "Employeename")
    private String employeeName;

    @Column(name = "Title")
    private String title;

    @Column(name = "Businessunit")
    private String businessUnit;

    @Column(name = "Place")
    private String place;

    @Column(name = "SupervisorID")
    private Long supervisorId;

    @Column(name = "Competencies")
    private String competencies;

    @Column(name = "Salary")
    private String salary;
}
