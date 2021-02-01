package com.example.demo.controller;

import com.example.demo.DemoApplicationTests;
import com.example.demo.entity.Employee;
import com.example.demo.error.CustomErrorResponse;
import com.example.demo.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EmployeeControllerTest extends DemoApplicationTests {

    @MockBean
    EmployeeService employeeService;

    @Test
    public void whenUpdateSalary_validArguments_shouldReturnOk() throws Exception {
        //AAA concept
        //ARRANGE

        Mockito.doNothing().when(employeeService).updateSalary("Canada", 30f);
        mockMvc.perform(MockMvcRequestBuilders.put("/employee/place/Canada/salary/30")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print());

        Mockito.verify(employeeService, Mockito.times(1)).updateSalary("Canada", 30f);
    }

    @Test
    public void whenUpdateSalary_InValidArguments_shouldThrowException() throws Exception {
        //AAA concept
        //ARRANGE
        CustomErrorResponse customErrorResponse = new CustomErrorResponse("ERROR", "updateSalary.percentage: percentage should not be more than 55.");
        String expectedApiResponseJson = objectWriter.writeValueAsString(customErrorResponse);

          mockMvc.perform(MockMvcRequestBuilders.put("/employee/place/Canada/salary/57")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().is4xxClientError()).andDo(print())
                  .andExpect(jsonPath("$", notNullValue())).andExpect(MockMvcResultMatchers.content().json(expectedApiResponseJson));

        Mockito.verify(employeeService, Mockito.times(0)).updateSalary("Canada", 57f);
    }

    @Test
    public void whenGetEmployeesByPlace_validArguments_shouldReturnOk() throws Exception {
        //AAA concept
        //ARRANGE
        Employee employee = new Employee();
        employee.setSalary("1000$");
        employee.setBusinessUnit("Bank");
        employee.setCompetencies("Developer");
        employee.setEmployeeId(1L);
        employee.setEmployeeName("test_user");
        employee.setPlace("Australia");
        employee.setSupervisorId(12345L);

        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        Page<Employee> pagedResponse = new PageImpl(employeeList);

        String expectedApiResponseJson = "{\"content\":[{\"employeeId\":1,\"employeeName\":\"test_user\",\"title\":null,\"businessUnit\":\"Bank\",\"place\":\"Australia\",\"supervisorId\":12345,\"competencies\":\"Developer\",\"salary\":\"1000$\"}],\"pageable\":\"INSTANCE\",\"last\":true,\"totalPages\":1,\"totalElements\":1,\"size\":1,\"number\":0,\"sort\":{\"sorted\":false,\"unsorted\":true,\"empty\":true},\"first\":true,\"numberOfElements\":1,\"empty\":false}";

        Mockito.when(employeeService.getEmployeesByPlace("Australia", PageRequest.of(0, 5))).thenReturn(pagedResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/Australia/0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$", notNullValue())).andExpect(MockMvcResultMatchers.content().json(expectedApiResponseJson));

        Mockito.verify(employeeService, Mockito.times(1)).getEmployeesByPlace("Australia", PageRequest.of(0, 5));
    }

    @Test
    public void whenGetSalaryRanges_validArguments_shouldReturnOk() throws Exception {
        //AAA concept
        //ARRANGE
        Map<String, String> salaryRange = new HashMap<>();
        salaryRange.put("minSalary", "1000$");
        salaryRange.put("maxSalary", "5000$");
        String expectedApiResponseJson = objectWriter.writeValueAsString(salaryRange);

        Mockito.when(employeeService.getSalaryRanges("Developer")).thenReturn(salaryRange);
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/range")
                .param("competency", "Developer")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$", notNullValue())).andExpect(MockMvcResultMatchers.content().json(expectedApiResponseJson));

        Mockito.verify(employeeService, Mockito.times(1)).getSalaryRanges("Developer");
    }

    @Test
    public void whenGetSalaryRanges_InValidArguments_shouldThrowException() throws Exception {
        //AAA concept
        //ARRANGE
        CustomErrorResponse customErrorResponse = new CustomErrorResponse("ERROR", "getSalaryRanges.competency: competency is required.");
        String expectedApiResponseJson = objectWriter.writeValueAsString(customErrorResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/range")
                .param("competency", "")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()).andDo(print())
                .andExpect(jsonPath("$", notNullValue())).andExpect(MockMvcResultMatchers.content().json(expectedApiResponseJson));

        Mockito.verify(employeeService, Mockito.times(0)).getSalaryRanges("Developer");
    }
}
