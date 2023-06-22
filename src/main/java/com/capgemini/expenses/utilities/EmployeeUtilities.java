package com.capgemini.expenses.utilities;

import com.capgemini.expenses.domain.Employee;
import com.capgemini.expenses.domain.EmployeesInMemoryImpl;
import com.capgemini.expenses.exceptions.InvalidEmployeeIdException;
import com.capgemini.expenses.exceptions.NameTooShortException;

public class EmployeeUtilities {

    public static boolean employeeExist(Employee emp, EmployeesInMemoryImpl employees) {
        return employees.findBySurname(emp.getSurname()) != null;
    }

    public static int validEmployeeID(String id) throws InvalidEmployeeIdException {
        try {
            int result = Integer.valueOf(id);
            return result;
        }catch (NumberFormatException e){
            throw new InvalidEmployeeIdException();
        }
    }
    public static void validateEmployeeName(String firstname, String surname) throws NameTooShortException {

        if(firstname.length() + surname.length() < 6){
            throw new NameTooShortException("Invalid name length.");
        }
    }

}
