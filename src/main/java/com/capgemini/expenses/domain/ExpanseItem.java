package com.capgemini.expenses.domain;

import java.util.Objects;

public class ExpanseItem {

    private int id;
    private int claimId;
    private ExpanseType expanseType;
    private String description;
    private double amount;


    public ExpanseItem(int id, int claimId, ExpanseType expanseType, String description, double amount) {
        this.id = id;
        this.claimId = claimId;
        this.expanseType = expanseType;
        this.description = description;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public int getClaimId() {
        return claimId;
    }

    public ExpanseType getExpanseType() {
        return expanseType;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public void setExpanseType(ExpanseType expanseType) {
        this.expanseType = expanseType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClaimId(int claimId) {
        this.claimId = claimId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ExpanseItem{" +
                "id=" + id +
                ", claimId=" + claimId +
                ", expanseType=" + expanseType +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpanseItem that = (ExpanseItem) o;
        return id == that.id && claimId == that.claimId && Double.compare(that.amount, amount) == 0 && expanseType == that.expanseType && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, claimId, expanseType, description, amount);
    }
}
