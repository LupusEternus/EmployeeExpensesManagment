package com.capgemini.expenses;

import com.capgemini.expenses.domain.*;
import com.capgemini.expenses.exceptions.EmployeeNotFoundException;
import com.capgemini.expenses.management.ExpanseManagementProcess;
import com.capgemini.expenses.management.ExpressExpanseManagementProcess;
import com.capgemini.expenses.management.RegularExpanseManagementProcess;
import com.capgemini.expenses.ui.UIFunctions;
import com.capgemini.expenses.utilities.ExpanseAnalysis;
import com.capgemini.expenses.utilities.ExpanseAnalysisImpl;
import com.capgemini.expenses.utilities.ExpanseAnalysisTempImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ExpenseManagementSystem {

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        ExpanseManagementProcess regular = new RegularExpanseManagementProcess();
        ExpanseManagementProcess express = new ExpressExpanseManagementProcess();
        Employees employees = new EmployeesDatabaseImpl();
        ExpanseAnalysis analysis = new ExpanseAnalysisImpl(employees);



        boolean exit = false;
        Scanner scanner = new Scanner(System.in);
        while (!exit) {
            System.out.println("Expanse Management System");
            System.out.println("-------------------------");
            System.out.println("e  - register new employee");
            System.out.println("c  - register new claim");
            System.out.println("p  - print all employees");
            System.out.println("f - generate files with all employees");
            System.out.println("a  - approve claim");
            System.out.println("r1 - outstanding expense claims");
            System.out.println("r2 - paid expense claims");
            System.out.println("r3 - expense claims above specified amount");
            System.out.println("x - exit");
            String choice = scanner.nextLine();
            switch (choice) {
                case "e" -> employees.addEmployee(UIFunctions.registerNewEmployee());

                case "c" -> {
                    try {
                        ExpenseClaim claim = UIFunctions.registerNewExpenseClaim();
                        employees.addExpanseClaim(claim);
                        int regularID = regular.registerExpenseClaim(claim);
                        express.registerExpenseClaim(claim);
                        System.out.println("Id of the regular register is " + regularID);
                    } catch (EmployeeNotFoundException e) {
                        System.out.println("No employee with this id");
                    }
                }
                case "p" -> employees.printEmployees();
                case  "f" ->{
                    List<Employee> listOfEmployees = employees.getListOfEmployees();
                    Collections.sort(listOfEmployees);
                    String lineSeparator = System.lineSeparator();
                    Path report = Paths.get(System.getProperty("user.home") + File.separator + "expenses_report.txt");
                    for (Employee employee : listOfEmployees) {
                        Files.writeString(report,employee.toString() + lineSeparator,StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                        for (ExpenseClaim claim : employee.getClaims().values()) {
                            Files.writeString(report,"-" + claim.toString() + lineSeparator, StandardOpenOption.APPEND);
                            for (ExpanseItem expanseItem: claim.getExpanseItemList()) {
                                Files.writeString(report,"--" + expanseItem.toString() + lineSeparator,StandardOpenOption.APPEND);
                            }
                            Files.writeString(report,"Total amount for all items in claim id " + claim.getId() + ": " + claim.getTotalAmount() + " zl." + lineSeparator,StandardOpenOption.APPEND);
                        }

                    }
                }
                case "a" -> {
                    System.out.println("Enter claim ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter approver employee ID: ");
                    int empolyeeID = scanner.nextInt();
                    Employee found = employees.findByID(empolyeeID);
                    scanner.nextLine();

                    System.out.println("Enter r for regular, or e for express");
                    String claimType = scanner.nextLine();

                    ExpanseManagementProcess requestedProcess;
                    if (claimType.equals("r")) {
                        requestedProcess = regular;
                    } else {
                        requestedProcess = express;
                    }
                    boolean result = requestedProcess.approveClaim(id, found);
                    System.out.println("The result was " + result);

                }
                case "r1" -> analysis.printOutstandingExpanse();
                case "r2" -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                    System.out.println("Enter date from(dd-mm-yyyy):");
                    String fromS = scanner.nextLine();

                    System.out.println("Enter date to(dd-mm-yyyy): ");
                    String toS = scanner.nextLine();

                    analysis.printPaidExpanse(LocalDate.parse(fromS,formatter), LocalDate.parse(toS,formatter));
                }
                case "r3" -> {

                    System.out.println("Enter amount: ");
                    analysis.printClaimsOverAmount(scanner.nextDouble());
                    scanner.nextLine();
                }

                case "x" -> exit = true;
                default -> System.out.println("wrong choice provide valid one");
            }
        }
    }
}
