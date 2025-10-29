package org.example.model;

import java.util.HashMap;
import java.util.Map;

public class LoanResult {
    private final LoanRequest loanRequest;
    private final Map<Integer, YearlyInstallment> installments;

    public LoanResult(LoanRequest loanRequest) {
        this.loanRequest = loanRequest;
        this.installments = new HashMap<>();
    }

    public void addYearlyInstallment(Integer year, double monthlyPayment, double interestRate) {
        installments.put(year, new YearlyInstallment(year, monthlyPayment, interestRate));
    }

    public Map<Integer, YearlyInstallment> getInstallments() {
        return installments;
    }

    public LoanRequest getLoanRequest() {
        return loanRequest;
    }

    public record YearlyInstallment(Integer year, Double monthlyPayment, Double interestRate) {
    }
}
