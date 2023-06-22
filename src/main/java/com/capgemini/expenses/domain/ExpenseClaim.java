package com.capgemini.expenses.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExpenseClaim {

    private int id;
    private int employeeId;
    private LocalDate dateOfClaim;
    private boolean approved;
    private boolean paid;
    private List<ExpanseItem> expanseItemList;

    public ExpenseClaim(int id, int employeeId, LocalDate dateOfClaim) {
        this.id = id;
        this.employeeId = employeeId;
        this.dateOfClaim = dateOfClaim;
        approved = false;
        paid = false;
        expanseItemList = new ArrayList<>();
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public void setPaid(boolean paid) {
        if(paid && !approved ) {
            System.out.println("This item cannot be paid as it has not yet been approved");
        }else{
            this.paid = paid;
        }
    }

    public int getId() {
        return id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public LocalDate getDateOfClaim() {
        return dateOfClaim;
    }

    public double getTotalAmount() {
        double total = 0d;
        for(ExpanseItem e : expanseItemList){
            total += e.getAmount();
        }
        return total;
    }

    public boolean isApproved() {
        return approved;
    }

    public boolean isPaid() {
        return paid;
    }

    public List<ExpanseItem> getExpanseItemList(){
        return expanseItemList;
    }

    public void addExpanseItem(ExpanseItem item){
        expanseItemList.add(item);
    }

    public void printExpanseItems(){
        for (ExpanseItem a:expanseItemList) {
            System.out.println(a);

        }
    }

    @Override
    public String toString() {
        return "ExpenseClaim{" +
                "id=" + id +
                ", employeeId=" + employeeId +
                ", dateOfClaim='" + dateOfClaim +
                ", approved=" + approved +
                ", paid=" + paid +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseClaim that = (ExpenseClaim) o;
        return id == that.id && employeeId == that.employeeId && approved == that.approved && paid == that.paid && Objects.equals(dateOfClaim, that.dateOfClaim) && Objects.equals(expanseItemList, that.expanseItemList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employeeId, dateOfClaim, approved, paid, expanseItemList);
    }
}
