package com.capgemini.expenses.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SetupDataBase {

    public static void main(String[] args) {
        createExpanseItemsTable();
        createExpanseClaimsTable();
        createEmployeesTable();
    }


    public static void createEmployeesTable(){
        try(Connection connection = DriverManager.getConnection("jdbc:h2:./expanses", "sa", "")){

            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE employees (id INTEGER, title VARCHAR(50),firstname VARCHAR(50),surname VARCHAR(50)," +
                    " jobtitle VARCHAR(30),department VARCHAR(20), PRIMARY KEY (id))";
            statement.executeUpdate(sql);

        }catch (SQLException e){
            System.out.println("Cannot connect to DB");
        }
    }

    public static void createExpanseClaimsTable(){
        try(Connection connection = DriverManager.getConnection("jdbc:h2:./expanses","sa","")){

            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE expenseclaims (id INTEGER, employeeId INTEGER, dateOfClaim VARCHAR(30), approved TINYINT," +
                    " paid TINYINT, PRIMARY KEY (id))";
            statement.executeUpdate(sql);

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }
    public static void createExpanseItemsTable(){
        try(Connection connection = DriverManager.getConnection("jdbc:h2:./expanses","sa","")){
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE expanseitems (id INTEGER, claimid  INTEGER, expanseType VARCHAR(20), " +
                    "description VARCHAR(255), amount DOUBLE, PRIMARY KEY(id))";
            statement.executeUpdate(sql);

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

}
