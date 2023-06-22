package com.capgemini.expenses.utilities;

import com.capgemini.expenses.domain.Employees;
import com.capgemini.expenses.domain.EmployeesInMemoryImpl;

import java.time.LocalDate;

public class ExpanseAnalysisImpl implements ExpanseAnalysis{

    private Employees employees;

    public ExpanseAnalysisImpl(Employees employees) {
        this.employees = employees;
    }

    @Override
    public void printOutstandingExpanse() {
        employees.getListOfEmployees().stream().forEach(
                emp -> {
                    emp.getClaims().values().stream()
                            .filter(a -> !a.isApproved())
                            .forEach(System.out::println);
        });
    }

    @Override
    public void printPaidExpanse(LocalDate from, LocalDate to) {
        employees.getListOfEmployees().stream().forEach(
                employee -> {
                    employee.getClaims().values().stream()
                            .filter(e -> e.isPaid())
                            .filter(date -> date.getDateOfClaim().isAfter(from)
                            && date.getDateOfClaim().isBefore(to))
                            .forEach(System.out::println);

                }
        );

    }

    @Override
    public void printClaimsOverAmount(double claimedAmount) {
        employees.getListOfEmployees().stream().forEach(
                employee -> {
                    employee.getClaims().values().stream()
                            .filter(empl -> empl.getTotalAmount() >= claimedAmount)
                            .forEach(System.out::println);
                }
        );

    }
}

