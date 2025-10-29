package org.example.controller;

import org.example.model.LoanRequest;
import org.example.model.LoanResult;
import org.example.service.ApiService;
import org.example.service.CalculationService;
import org.example.service.ValidationService;
import org.example.view.ConsoleView;

import java.io.File;
import java.io.FileWriter;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;

public class CreditController {
    private final Scanner scanner;
    private final ValidationService validationService;
    private final CalculationService calculationService;
    private final ConsoleView consoleView;
    private final ApiService apiService;

    public CreditController() {
        this.scanner = new Scanner(System.in);
        this.validationService = new ValidationService();
        this.calculationService = new CalculationService();
        this.consoleView = new ConsoleView();
        this.apiService = new ApiService();
    }

    public void handleCalculation() {
        try {
            LoanRequest loanRequest = new LoanRequest();

            Optional<String> validate;

            do {
                System.out.print("Masukkan jenis kendaraan (Mobil/Motor): ");
                loanRequest.setVehicleType(scanner.nextLine().trim().toLowerCase());

                System.out.print("Masukkan kondisi kendaraan (Baru/Bekas): ");
                loanRequest.setVehicleCondition(scanner.nextLine().trim().toLowerCase());

                System.out.print("Masukkan tahun kendaraan (4 digit): ");
                loanRequest.setVehicleYear(Integer.parseInt(scanner.nextLine().trim()));

                System.out.print("Masukkan total pinjaman (maks Rp 1.000.000.000): ");
                loanRequest.setTotalLoanAmount(Long.parseLong(scanner.nextLine().trim()));

                System.out.print("Masukkan tenor pinjaman (1-6 tahun): ");
                loanRequest.setLoanTenure(Integer.parseInt(scanner.nextLine().trim()));

                System.out.print("Masukkan jumlah DP: ");
                loanRequest.setDownPayment(Long.parseLong(scanner.nextLine().trim()));

                validate = this.validationService.validateLoanRequest(loanRequest);

                if (validate.isPresent()) {
                    System.out.println(validate.get());
                }
            }
            while (validate.isPresent());

            LoanResult loanResult = calculationService.calculateLoan(loanRequest);

            consoleView.printLoanResult(loanResult);

            this.saveSheet(loanResult);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void loadExistingCalculation() {
        System.out.println("Memuat data dari API...");

        try {
            LoanRequest loanRequest = this.apiService.fetchLoanRequest();

            if (loanRequest == null) {
                System.out.println("Gagal memuat data dari API");

                return;
            }

            System.out.println("Data berhasil dimuat:");
            System.out.println(loanRequest);

            Optional<String> validate = validationService.validateLoanRequest(loanRequest);

            if (validate.isPresent()) {
                System.out.println(validate.get());

                return;
            }

            LoanResult loanResult = calculationService.calculateLoan(loanRequest);

            consoleView.printLoanResult(loanResult);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void saveSheet(LoanResult loanResult) {
        String command = "";

        do {
            System.out.print("Data mau disimpan? (Y/N): ");

            command = scanner.nextLine().trim();
        }
        while (!command.equalsIgnoreCase("y") && !command.equalsIgnoreCase("n"));

        if (command.equalsIgnoreCase("y")) {
            System.out.print("Masukkan nama file untuk menyimpan hasil (contoh: hasil1.txt): ");

            String fileName = scanner.nextLine().trim();

            try (FileWriter fileWriter = new FileWriter(fileName, true)) {
                fileWriter.write("\nSimulasi Kredit\n");
                fileWriter.write("Data ini disimpan pada: " + java.time.LocalDateTime.now() + "\n");
                fileWriter.write("=== Hasil Simulasi Kredit ===\n");
                fileWriter.write(loanResult.getLoanRequest().toString());

                NumberFormat currency = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
                for (LoanResult.YearlyInstallment y : loanResult.getInstallments().values()) {
                    fileWriter.write(String.format("\nTahun: %d: %s/bulan, Suku Bunga: %.2f%%", y.year(), currency.format(y.monthlyPayment()), y.interestRate()));
                }
                fileWriter.write("\n===============================");
                System.out.println("Hasil simulasi disimpan ke file: " + fileName);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
