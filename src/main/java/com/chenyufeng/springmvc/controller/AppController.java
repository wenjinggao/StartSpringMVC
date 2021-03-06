package com.chenyufeng.springmvc.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import com.chenyufeng.springmvc.model.Employee;
import com.chenyufeng.springmvc.service.EmployeeService;
import com.wordnik.swagger.annotations.ApiParam;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/emp")
public class AppController {

    @Autowired
    EmployeeService service;

    @Autowired
    MessageSource messageSource;

    @ResponseBody
    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    public List<Employee> listEmployees() {
        List<Employee> employees = service.findAllEmployees();
        return employees;
    }

    @ResponseBody
    @RequestMapping(value = {"/new"}, method = RequestMethod.POST)
    public String saveEmployee(
            @ApiParam(value = "用户名", required = true) @RequestParam String name,
            @ApiParam(value = "日期", required = true) @RequestParam String joiningDate,
            @ApiParam(value = "薪水", required = true) @RequestParam BigDecimal salary,
            @ApiParam(value = "ssn", required = true) @RequestParam String ssn
    ) {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setJoiningDate(joiningDate);
        employee.setSalary(salary);
        employee.setSsn(ssn);

        service.saveEmployee(employee);

        return "success";
    }

    @ResponseBody
    @RequestMapping(value = {"/edit-{ssn}-employee"}, method = RequestMethod.POST)
    public String updateEmployee(@PathVariable String ssn,
            @ApiParam(value = "用户名", required = true) @RequestParam String name,
            @ApiParam(value = "日期", required = true) @RequestParam String joiningDate,
            @ApiParam(value = "薪水", required = true) @RequestParam BigDecimal salary
    ) {
        Employee employee = service.findEmployeeBySsn(ssn);
        employee.setName(name);
        employee.setJoiningDate(joiningDate);
        employee.setSalary(salary);

        service.updateEmployee(employee);

        return "success update";
    }

    /**
     * 通过以下的方式可以获得路径中传递的参数，在swagger中也能使用
     * @param ssn
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/delete-{ssn}-employee"}, method = RequestMethod.GET)
    public String deleteEmployee(@PathVariable String ssn) {
        service.deleteEmployeeBySsn(ssn);
        return "success delete";
    }
}