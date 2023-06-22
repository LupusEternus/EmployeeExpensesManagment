package com.capgemini.expenses.utilities;

import java.time.LocalDate;

public class ExpanseAnalysisTempImpl implements ExpanseAnalysis{

    @Override
    public void printOutstandingExpanse() {
        System.out.println("This feature is not currently available.");
    }

    @Override
    public void printPaidExpanse(LocalDate from, LocalDate to) {
        System.out.println("This feature is not currently available.");
    }

    @Override
    public void printClaimsOverAmount(double claimedAmount) {
        System.out.println("This feature is not currently available.");
    }
}
