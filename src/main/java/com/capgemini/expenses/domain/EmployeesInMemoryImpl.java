package com.capgemini.expenses.domain;

import com.capgemini.expenses.exceptions.EmployeeNotFoundException;

import java.util.*;


public class EmployeesInMemoryImpl implements Employees {

    private Map<Integer, Employee> employees = new HashMap<>();


    @Override
    public void addEmployee(Employee emp) {

        employees.put(emp.getId(), emp);
    }

    @Override
    public List<Employee> getListOfEmployees(){
        return new ArrayList<Employee>(employees.values());
    }

    @Override
    public Employee findBySurname(String surname) {
        for (Employee e : employees.values()) {
            if (e.getSurname().equals(surname)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public Employee findByID(int id) {

        return employees.get(id);
    }

    @Override
    public void printEmployees() {
        List<Employee> temp = new ArrayList<>(employees.values());
        Collections.sort(temp);
        for (Employee e : temp) {
            System.out.println(e);
            for (ExpenseClaim c : e.getClaims().values()) {
                System.out.println(c);
                c.printExpanseItems();
                System.out.println("Total amount for all items in claim id " + c.getId() + ": " + c.getTotalAmount());
            }

        }

    }

    @Override
    public boolean employeeExist(int id) {

        return employees.containsKey(id);
    }


    @Override
    public void addExpanseClaim(ExpenseClaim claim) throws EmployeeNotFoundException {

        int employeeID = claim.getEmployeeId();
        if (!employeeExist(claim.getEmployeeId())) {
            throw new EmployeeNotFoundException();
        }
        for (Employee employee : employees.values()) {
            if (employeeID == employee.getId()) {
                employee.getClaims().put(claim.getId(), claim);
            }
        }


    }
}
