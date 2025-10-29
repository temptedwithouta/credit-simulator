import org.example.model.LoanRequest;
import org.example.model.LoanResult;
import org.example.service.CalculationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalculationServiceTest {

    private CalculationService calculationService;

    @Before
    public void setUp() {
        this.calculationService = new CalculationService();
    }

    @Test
    public void testCalculateLoan() {
        LoanRequest loanRequest = new LoanRequest();

        loanRequest.setVehicleType("Mobil");
        loanRequest.setVehicleCondition("Bekas");
        loanRequest.setVehicleYear(2024);
        loanRequest.setTotalLoanAmount(100_000_000L);
        loanRequest.setLoanTenure(3);
        loanRequest.setDownPayment(25_000_000L);

        LoanResult loanResult = this.calculationService.calculateLoan(loanRequest);

        Assert.assertEquals(3, loanResult.getInstallments().size());


        double[] expectedYearRate = {8.00, 8.10, 8.60};
        double[] expectedYearMonthly = {2_250_000.00, 2_432_250.00, 2_641_423.50};

        double toleranceRate = 0.001;
        double tolerancePayment = 1.0;

        for (int i = 0; i < loanResult.getInstallments().size(); i++) {
            Assert.assertEquals(expectedYearRate[i], loanResult.getInstallments().get(i + 1).interestRate(), toleranceRate);
            Assert.assertEquals(expectedYearMonthly[i], loanResult.getInstallments().get(i + 1).monthlyPayment(), tolerancePayment);
        }
    }
}
