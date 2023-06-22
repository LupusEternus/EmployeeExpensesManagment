package com.capgemini.expenses.domain;

import java.util.Objects;

public class StaffEmployee extends Employee implements Comparable<Employee>{

    private String username;
    private String password;

    public StaffEmployee(Employee employee, String username, String password){
        super(employee.getId(),employee.getTitle(),employee.getFirstName(),employee.getSurname(),employee.getJobTitle(),employee.getDepartment());
        this.password = password;
        this.username = username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StaffEmployee that = (StaffEmployee) o;
        return Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, password);
    }
}
