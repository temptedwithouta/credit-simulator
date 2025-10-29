package org.example.view;

import org.example.model.LoanResult;

import java.text.NumberFormat;
import java.util.Locale;

public class ConsoleView {
    public void printWelcome() {
        System.out.println("======================================");
        System.out.println("          CREDIT SIMULATOR            ");
        System.out.println("======================================");
        System.out.println("Ketik 'show' untuk melihat perintah yang tersedia.");
    }

    public void printPrompt() {
        System.out.print("\n> ");
    }

    public void printMenuOptions() {
        System.out.println("\n=== Daftar Perintah ===");
        System.out.println("show       : Tampilkan daftar perintah");
        System.out.println("calculate  : Hitung cicilan baru");
        System.out.println("load       : Muat data dari API (existing)");
        System.out.println("save       : Simpan perhitungan ke sheet");
        System.out.println("exit       : Keluar dari program");
    }

    public void printUnknownCommand(String command) {
        System.out.println("Perintah tidak dikenal: '" + command + "'");
        System.out.println("Ketik 'show' untuk melihat daftar perintah.");
    }

    public void printGoodbye() {
        System.out.println("\nTerima kasih telah menggunakan Credit Simulator!");
    }

    public void printLoanResult(LoanResult loanResult) {
        System.out.println("\n=== Hasil Simulasi Kredit ===");
        System.out.println(loanResult.getLoanRequest());

        NumberFormat currency = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        for (LoanResult.YearlyInstallment y : loanResult.getInstallments().values()) {
            System.out.printf("Tahun: %d: %s/bulan, Suku Bunga: %.2f%%\n", y.year(), currency.format(y.monthlyPayment()), y.interestRate());
        }
        System.out.println("===============================");
    }
}
