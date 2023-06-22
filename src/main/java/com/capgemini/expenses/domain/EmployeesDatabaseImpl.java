package com.capgemini.expenses.domain;

import com.capgemini.expenses.exceptions.EmployeeNotFoundException;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmployeesDatabaseImpl implements Employees {

    public EmployeesDatabaseImpl() throws ClassNotFoundException {
        Class.forName("org.h2.Driver");
    }

    @Override
    public void addEmployee(Employee emp) {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:./expanses", "sa", "")) {

            Statement statement = connection.createStatement();
            String sql = "INSERT INTO employees (id, title,firstname,surname,jobtitle,department)" +
                    " VALUES (" + emp.getId() + ",'" + emp.getJobTitle() + "'," +
                    "'" + emp.getFirstName() + "','" + emp.getSurname() + "'," +
                    "'" + emp.getJobTitle() + "','" + emp.getDepartment() + "')";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("There was a problem connection to database");
            throw new RuntimeException(e);
        }
    }

    private List<ExpanseItem> getExpanseItemsForClaim(int claimID){
        List<ExpanseItem> expanseItemList = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection("jdbc:h2:./expanses", "sa", "")){
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM expanseitems WHERE claimid = " + claimID;
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
                int id = rs.getInt("id");
                ExpanseType expanseType = ExpanseType.valueOf(rs.getString("expanseType").toUpperCase());
                String desctiption = rs.getString("description");
                double amount = rs.getDouble("amount");
                ExpanseItem expanseItem = new ExpanseItem(id,claimID,expanseType,desctiption,amount);
                expanseItemList.add(expanseItem);
            }
        }catch (SQLException e){
            throw  new RuntimeException(e);
        }

        return expanseItemList;
    }

    private List<ExpenseClaim> getExpanseClaimsForEmployee(int employeeId){
        List<ExpenseClaim> claimList = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection("jdbc:h2:./expanses", "sa", "")){
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM expenseclaims WHERE employeeId = " + employeeId;
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
                int id = rs.getInt("id");
                String date = rs.getString("dateOfClaim");
                boolean approved = rs.getBoolean("approved");
                boolean paid = rs.getBoolean("paid");
                ExpenseClaim expenseClaim = new ExpenseClaim(id,employeeId, LocalDate.parse(date));
                expenseClaim.setApproved(approved);
                expenseClaim.setPaid(paid);
                List<ExpanseItem> expanseItemList = getExpanseItemsForClaim(id);
                expanseItemList.forEach(item ->expenseClaim.addExpanseItem(item));
                claimList.add(expenseClaim);

            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return claimList;
    }


    @Override
    public List<Employee> getListOfEmployees() {
        List<Employee> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:h2:./expanses", "sa", "")) {
            Statement statement = connection.createStatement();
            String query = "SELECT * from employees";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String firstname = rs.getString("firstname");
                String surname = rs.getString("surname");
                String jobtitle = rs.getString("jobtitle");
                Department department = Department.valueOf(rs.getString("department").toUpperCase());
                Employee e = new Employee(id,title,firstname,surname,jobtitle,department);
                List<ExpenseClaim> expenseClaims = getExpanseClaimsForEmployee(id);
                expenseClaims.forEach(claim -> e.addClaim(claim));

                list.add(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Employee findBySurname(String surname) {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:./expanses", "sa", "")) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM employees WHERE surname like %" + surname + "%");
            if (rs.next()) {
                return new Employee(rs.getInt("id"), rs.getString("title"), rs.getString("firstname"), rs.getString("surname"), rs.getString("jobtitle"), Department.valueOf(rs.getString("department").toUpperCase()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Employee findByID(int id) {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:./expanses", "sa", "")) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM employees WHERE id == " + id);
            if (rs.next()) {
                return new Employee(rs.getInt("id"), rs.getString("title"), rs.getString("firstname"), rs.getString("surname"), rs.getString("jobtitle"), Department.valueOf(rs.getString("department").toUpperCase()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void printEmployees() {

        List<Employee> temp = getListOfEmployees();
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
        return (findByID(id) == null) ? false : true;

    }

    private void addExpanseItem(ExpanseItem item){

        try(Connection connection = DriverManager.getConnection("jdbc:h2:./expanses", "sa", "")){
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO expanseitems (id, claimid, expanseType, description, amount) " +
                    " VALUES ( " + item.getId() + ", " + item.getClaimId() + ", '" +
                    item.getExpanseType().toString() + "', '" + item.getDescription() + "', " +
                    item.getAmount() + " )";
            statement.executeUpdate(sql);

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }



    @Override
    public void addExpanseClaim(ExpenseClaim claim) {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:./expanses", "sa", "")) {
            Statement statement = connection.createStatement();
            claim.getExpanseItemList().forEach(item -> addExpanseItem(item));
            String querry = "INSERT INTO expenseclaims (id, employeeId, dateOfClaim, approved, paid)" +
                    "  VALUES (" + claim.getId() + ", " + claim.getEmployeeId() + ", '" + claim.getDateOfClaim().toString() + "', " +
                    claim.isApproved() + ", " + claim.isPaid() + ")";
            statement.execute(querry);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
