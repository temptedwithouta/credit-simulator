package org.example.controller;

import org.example.model.LoanRequest;
import org.example.model.LoanResult;
import org.example.service.CalculationService;
import org.example.service.ValidationService;
import org.example.view.ConsoleView;

import java.io.File;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.locks.StampedLock;

public class MenuController {
    private final ConsoleView consoleView;
    private final Scanner scanner;
    private final CreditController creditController;
    private final ValidationService validationService;
    private final CalculationService calculationService;

    public MenuController() {
        this.consoleView = new ConsoleView();
        this.scanner = new Scanner(System.in);
        this.creditController = new CreditController();
        this.validationService = new ValidationService();
        this.calculationService = new CalculationService();
    }

    public void start() {
        this.consoleView.printWelcome();

        boolean running = true;

        while (running) {
            this.consoleView.printPrompt();

            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "show":
                    consoleView.printMenuOptions();
                    break;
                case "calculate":
                    creditController.handleCalculation();
                    this.consoleView.printWelcome();
                    break;
                case "load":
                    creditController.loadExistingCalculation();
                    this.consoleView.printWelcome();
                    break;
                case "exit":
                    running = false;
                    consoleView.printGoodbye();
                    break;
                default:
                    consoleView.printUnknownCommand(command);
                    this.consoleView.printWelcome();
                    break;
            }
        }
    }

    public void startWithFile(String fileName) {
        System.out.println("Membaca input dari file: " + fileName);

        try (Scanner fileScanner = new Scanner(new File(fileName))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split("#");

                if (parts.length != 6) {
                    System.out.println("Format tidak sesuai.");
                    continue;
                }

                LoanRequest loanRequest = new LoanRequest();

                loanRequest.setVehicleType(parts[0].trim().toLowerCase());
                loanRequest.setVehicleCondition(parts[1].trim().toLowerCase());
                loanRequest.setVehicleYear(Integer.parseInt(parts[2].trim()));
                loanRequest.setTotalLoanAmount(Long.parseLong(parts[3].trim()));
                loanRequest.setLoanTenure(Integer.parseInt(parts[4].trim()));
                loanRequest.setDownPayment(Long.parseLong(parts[5].trim()));

                Optional<String> validate = validationService.validateLoanRequest(loanRequest);
                if (validate.isPresent()) {
                    System.out.println(validate.get());

                    continue;
                }

                LoanResult result = calculationService.calculateLoan(loanRequest);

                consoleView.printLoanResult(result);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
