package com.capgemini.expenses.utilities;

import java.time.LocalDate;

public interface ExpanseAnalysis {

    public void printOutstandingExpanse();

    public void printPaidExpanse(LocalDate from, LocalDate to);

    public void printClaimsOverAmount(double claimedAmount);
}