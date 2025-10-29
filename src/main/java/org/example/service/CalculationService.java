package org.example.service;

import org.example.model.LoanRequest;
import org.example.model.LoanResult;

public class CalculationService {
    public LoanResult calculateLoan(LoanRequest loanRequest) {
        double baseRate = loanRequest.getVehicleType().equalsIgnoreCase("mobil") ? 0.08 : 0.09;
        double remainingPrincipal = loanRequest.getTotalLoanAmount() - loanRequest.getDownPayment();

        LoanResult loanResult = new LoanResult(loanRequest);
        double currentRate = baseRate;

        for (int i = 0; i < loanRequest.getLoanTenure(); i++) {
            double totalLoanYear = remainingPrincipal * (1 + currentRate);

            double monthlyInstallment = totalLoanYear / ((12 * loanRequest.getLoanTenure()) - (i * 12));

            loanResult.addYearlyInstallment(i + 1, monthlyInstallment, currentRate * 100);

            double totalPaidThisYear = monthlyInstallment * 12;

            remainingPrincipal = Math.max(0, totalLoanYear - totalPaidThisYear);

            if ((i + 1) % 2 == 0) {
                currentRate = currentRate + 0.005;
            } else {
                currentRate = currentRate + 0.001;
            }
        }

        return loanResult;
    }
}
