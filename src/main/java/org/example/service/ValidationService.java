package org.example.service;

import org.example.model.LoanRequest;

import java.time.Year;
import java.util.Optional;

public class ValidationService {
    public Optional<String> validateLoanRequest(LoanRequest loanRequest) {
        if (!loanRequest.getVehicleType().equalsIgnoreCase("mobil") && !loanRequest.getVehicleType().equalsIgnoreCase("motor")) {
            return Optional.of("Jenis kendaraan harus mobil atau motor.");
        }

        if (!loanRequest.getVehicleCondition().equalsIgnoreCase("bekas") && !loanRequest.getVehicleCondition().equalsIgnoreCase("baru")) {
            return Optional.of("Kondisi kendaraan harus bekas atau baru.");
        }

        if (loanRequest.getLoanTenure() < 1 || loanRequest.getLoanTenure() > 6) {
            return Optional.of("Tenor harus antara 1 sampai 6 tahun.");
        }

        if (loanRequest.getTotalLoanAmount() > 1_000_000_000L) {
            return Optional.of("Total pinjaman tidak boleh lebih dari 1 miliar.");
        }

        boolean isNew = loanRequest.getVehicleCondition().equalsIgnoreCase("baru");

        double dpPercent = (double) loanRequest.getDownPayment() / loanRequest.getTotalLoanAmount() * 100;

        if (isNew) {
            if (loanRequest.getVehicleYear() < Year.now().getValue() - 1) {
                return Optional.of("Kendaraan baru tidak boleh lebih dari 1 tahun sebelumnya.");
            }

            if (dpPercent < 35) {
                return Optional.of("DP untuk kendaraan baru minimal 35%.");
            }
        } else {
            if (dpPercent < 25) {
                return Optional.of("DP untuk kendaraan bekas minimal 25%.");
            }
        }

        return Optional.empty();
    }
}
