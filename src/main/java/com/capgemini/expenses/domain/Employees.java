package com.capgemini.expenses.domain;

import com.capgemini.expenses.exceptions.EmployeeNotFoundException;

import java.util.List;

public interface Employees {
    void addEmployee(Employee emp);

    List<Employee> getListOfEmployees();

    Employee findBySurname(String surname);

    Employee findByID(int id);

    void printEmployees();

    boolean employeeExist(int id);

    void addExpanseClaim(ExpenseClaim claim) throws EmployeeNotFoundException;
}
