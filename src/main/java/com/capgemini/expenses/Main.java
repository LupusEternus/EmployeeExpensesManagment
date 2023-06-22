package com.capgemini.expenses;

import com.capgemini.expenses.domain.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {


//        Employee employee1 = new Employee();
//        employee1.setId(1);
//        employee1.setFirstName("Tomek");
//        employee1.setSurname("Wilk");
//        employee1.setTitle("Mr");
//
//        System.out.println(employee1.getMailingName());
//        System.out.println(employee1.getMailingName(true));
//        System.out.println(employee1.getMailingName(false));
//        Employee employee2 = new Employee(2,"Manager");
//        employee2.setTitle("Dr.");
//        employee2.setFirstName("Szymon");
//        employee2.setSurname("Cos");
//
//        Employees employees = new Employees(15);
//        employees.addEmployee(employee1);
//        employees.addEmployee(employee2);
//        employees.addEmployee(new Employee(20,"Mr.","Oskar", "Fabian","DevOps", Department.FINANCE));
//
//        employees.printEmployees();
//
//        System.out.println("======================================");
//
//        ExpenseClaim expanseClaim = new ExpenseClaim(1,1,"12/05/2022",200);
//        System.out.println(expanseClaim.getDateOfClaim());
//        expanseClaim.setPaid(true);
//        System.out.println(expanseClaim.isPaid());
//        expanseClaim.setApproved(true);
//        expanseClaim.setPaid(true);
//        System.out.println(expanseClaim.isPaid());
//
//
//        ExpanseItem expanseItem = new ExpanseItem(1,1,ExpanseType.TRAVEL,"China",200);
//        System.out.println(expanseItem.getDescription());
//
//        System.out.println(employee1.toString());
//
//        Employee employee3 = new Employee();
//        employee3.setId(1);
//        employee3.setFirstName("Tomek");
//        employee3.setSurname("Wilk");
//        employee3.setTitle("Mr");
//
//        System.out.println(employee1.equals(employee3));

        Class.forName("org.h2.Driver");
        try(Connection connection = DriverManager.getConnection("jdbc:h2:./customerdata", "sa", "");) {
            Statement statement = connection.createStatement();
            //statement.executeUpdate("CREATE TABLE customer(id INTEGER, name VARCHAR(255),age INTEGER, PRIMARY KEY(id))");
            //statement.executeUpdate("INSERT INTO  customer(id,name,age) VALUES (2, 'Szymon',23)");

            ResultSet rs = statement.executeQuery("SELECT * from customer");

            while (rs.next()) {
                System.out.println(rs.getInt("id"));
                System.out.println(rs.getString("name"));
            }
        }



    }
}
