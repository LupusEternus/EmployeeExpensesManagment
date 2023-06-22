package com.capgemini.expenses.management;

import com.capgemini.expenses.domain.Employee;
import com.capgemini.expenses.domain.ExpenseClaim;

public interface ExpanseManagementProcess {

    int registerExpenseClaim(ExpenseClaim claim);
    boolean approveClaim(int id, Employee approver);

}
