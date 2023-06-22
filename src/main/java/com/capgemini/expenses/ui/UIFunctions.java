package com.capgemini.expenses.ui;


import com.capgemini.expenses.domain.*;
import com.capgemini.expenses.exceptions.InvalidEmployeeIdException;
import com.capgemini.expenses.exceptions.NameTooShortException;
import com.capgemini.expenses.utilities.EmployeeUtilities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;


public class UIFunctions {

    public static Employee registerNewEmployee() {

        Employee newOne = new Employee();
        Scanner scanner = new Scanner(System.in);
        boolean validID = false;


        while (!validID) {
            try {
                System.out.println("Enter new employee ID: ");
                String string_id = scanner.nextLine();
                int ID = EmployeeUtilities.validEmployeeID(string_id);
                newOne.setId(ID);
                validID = true;
            } catch (InvalidEmployeeIdException e) {
                System.out.println("Wrong employee ID");
            }
        }
        System.out.println("Enter title");
        String title = scanner.nextLine();
        newOne.setTitle(title);
        boolean validName = false;
        while (!validName) {
            try {
                System.out.println("Please enter first name: ");
                String firstName = scanner.nextLine();
                System.out.println("Enter surname: ");
                String surName = scanner.nextLine();
                EmployeeUtilities.validateEmployeeName(firstName, surName);
                newOne.setFirstName(firstName);
                newOne.setSurname(surName);
                validName = true;

            } catch (NameTooShortException e) {
                System.out.println("The name you entered was not valid - try again");
            }
        }
        System.out.println("Enter job title");
        String jobTitle = scanner.nextLine();
        newOne.setJobTitle(jobTitle);
        boolean validDepa = false;
        while (!validDepa) {
            try {
                System.out.println("Enter valid department name: ");
                System.out.print("\"");
                for (Department d : Department.values()) {
                    System.out.print(d + " ");
                }
                System.out.println("\"");
                String depa = scanner.nextLine();
                newOne.setDepartment(Department.valueOf(depa.toUpperCase()));
                validDepa = true;

            } catch (IllegalArgumentException e) {
                System.out.println("There is no such a department");
            }
        }
        System.out.println("Is this a member of staff? Y/N");
        String isStaff = scanner.nextLine();
        if (isStaff.equalsIgnoreCase("y")) {
            System.out.println("Enter the user name: ");
            String username = scanner.nextLine();
            System.out.println("Enter the password");
            String password = scanner.nextLine();
            StaffEmployee staffEmployee = new StaffEmployee(newOne, username, password);
            System.out.println("Staff employee successful added.");
            return staffEmployee;
        }

        System.out.println("Employee successful added.");
        return newOne;
    }

    public static ExpenseClaim registerNewExpenseClaim() {
        int claimId;
        int employeeId;
        int expanseItemId;
        String expanseTypeString;
        String description;
        double amount;
        boolean exit = false;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Claim ID:");
        claimId = scanner.nextInt();
        System.out.println("Enter employeeId: ");
        employeeId = scanner.nextInt();
        scanner.nextLine();
        ExpenseClaim expenseClaim = new ExpenseClaim(claimId, employeeId, LocalDate.now());


        while (!exit) {

            System.out.println("Enter expanse item id: ");
            expanseItemId = scanner.nextInt();
            scanner.nextLine();
            boolean expenseTypeIsValid = false;
            ExpanseType et = null;
            while (!expenseTypeIsValid) {
                System.out.println("Enter expanse type: ");
                expanseTypeString = scanner.nextLine().toUpperCase();
                try {

                    et = ExpanseType.valueOf(expanseTypeString.toUpperCase());
                    expenseTypeIsValid = true;
                } catch (IllegalArgumentException e) {
                    System.out.println("Wrong expanse type");
                }
            }
            System.out.println("Enter description: ");
            description = scanner.nextLine();

            System.out.println("Amount: ");
            amount = scanner.nextDouble();
            scanner.nextLine();

            ExpanseItem expanseItem = new ExpanseItem(expanseItemId, claimId, et, description, amount);
            expenseClaim.addExpanseItem(expanseItem);

            System.out.println("Add another expanse item? Y/N");
            String anotherItem = scanner.nextLine();
            if (!anotherItem.equalsIgnoreCase("y"))
                exit = true;
        }

        return expenseClaim;
    }


}
