package com.capgemini.expenses.management;

import com.capgemini.expenses.domain.Employee;
import com.capgemini.expenses.domain.ExpenseClaim;
import com.capgemini.expenses.domain.StaffEmployee;
import com.capgemini.expenses.management.ExpanseManagementProcess;

import java.util.ArrayList;
import java.util.List;

public class RegularExpanseManagementProcess implements ExpanseManagementProcess {

    private List<ExpenseClaim> claims = new ArrayList<>();

    @Override
    public int registerExpenseClaim(ExpenseClaim claim) {
        claims.add(claim);
        return claims.size() - 1;

    }

    @Override
    public boolean approveClaim(int id, Employee approver) {
        if (claims.get(id).getTotalAmount() < 100 || (claims.get(id).getTotalAmount() > 100 && approver instanceof StaffEmployee)) {
            return true;
        }
        return false;
    }
}
