package com.capgemini.expenses.management;

import com.capgemini.expenses.domain.Employee;
import com.capgemini.expenses.domain.ExpenseClaim;

public class ExpressExpanseManagementProcess implements  ExpanseManagementProcess{

    private ExpenseClaim claim;

    @Override
    public int registerExpenseClaim(ExpenseClaim claim) {
        this.claim = claim;
        return -1;
    }

    @Override
    public boolean approveClaim(int id, Employee approver) {
       return claim.getTotalAmount() < 50;
    }
}
