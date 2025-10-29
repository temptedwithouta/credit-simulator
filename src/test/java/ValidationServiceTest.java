import org.example.model.LoanRequest;
import org.example.service.ValidationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Year;
import java.util.Optional;

public class ValidationServiceTest {
    private ValidationService validationService;

    @Before
    public void setUp() {
        validationService = new ValidationService();
    }

    @Test
    public void testValidLoanRequestForNewVehicle() {
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setVehicleType("Mobil");
        loanRequest.setVehicleCondition("Baru");
        loanRequest.setVehicleYear(Year.now().getValue());
        loanRequest.setTotalLoanAmount(100_000_000L);
        loanRequest.setLoanTenure(3);
        loanRequest.setDownPayment(40_000_000L);

        Optional<String> result = validationService.validateLoanRequest(loanRequest);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testValidLoanRequestForUsedVehicle() {
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setVehicleType("Mobil");
        loanRequest.setVehicleCondition("Bekas");
        loanRequest.setVehicleYear(2022);
        loanRequest.setTotalLoanAmount(100_000_000L);
        loanRequest.setLoanTenure(3);
        loanRequest.setDownPayment(40_000_000L);

        Optional<String> result = validationService.validateLoanRequest(loanRequest);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testInvalidVehicleType() {
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setVehicleType("Pesawat");
        loanRequest.setVehicleCondition("Baru");
        loanRequest.setVehicleYear(Year.now().getValue());
        loanRequest.setTotalLoanAmount(100_000_000L);
        loanRequest.setLoanTenure(3);
        loanRequest.setDownPayment(40_000_000L);

        Optional<String> result = validationService.validateLoanRequest(loanRequest);
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals("Jenis kendaraan harus mobil atau motor.", result.get());
    }

    @Test
    public void testInvalidVehicleCondition() {
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setVehicleType("Mobil");
        loanRequest.setVehicleCondition("Rongsok");
        loanRequest.setVehicleYear(Year.now().getValue());
        loanRequest.setTotalLoanAmount(100_000_000L);
        loanRequest.setLoanTenure(3);
        loanRequest.setDownPayment(40_000_000L);

        Optional<String> result = validationService.validateLoanRequest(loanRequest);
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals("Kondisi kendaraan harus bekas atau baru.", result.get());
    }

    @Test
    public void testInvalidTenor() {
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setVehicleType("Mobil");
        loanRequest.setVehicleCondition("Baru");
        loanRequest.setVehicleYear(Year.now().getValue());
        loanRequest.setTotalLoanAmount(100_000_000L);
        loanRequest.setLoanTenure(10);
        loanRequest.setDownPayment(40_000_000L);

        Optional<String> result = validationService.validateLoanRequest(loanRequest);
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals("Tenor harus antara 1 sampai 6 tahun.", result.get());
    }

    @Test
    public void testExceedingLoanAmount() {
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setVehicleType("Mobil");
        loanRequest.setVehicleCondition("Baru");
        loanRequest.setVehicleYear(Year.now().getValue());
        loanRequest.setTotalLoanAmount(1_500_000_000L);
        loanRequest.setLoanTenure(3);
        loanRequest.setDownPayment(40_000_000L);

        Optional<String> result = validationService.validateLoanRequest(loanRequest);
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals("Total pinjaman tidak boleh lebih dari 1 miliar.", result.get());
    }

    @Test
    public void testNewCarTooOld() {
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setVehicleType("Mobil");
        loanRequest.setVehicleCondition("Baru");
        loanRequest.setVehicleYear(Year.now().getValue() - 2);
        loanRequest.setTotalLoanAmount(100_000_000L);
        loanRequest.setLoanTenure(3);
        loanRequest.setDownPayment(40_000_000L);

        Optional<String> result = validationService.validateLoanRequest(loanRequest);
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals("Kendaraan baru tidak boleh lebih dari 1 tahun sebelumnya.", result.get());
    }

    @Test
    public void testNewCarInsufficientDP() {
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setVehicleType("Mobil");
        loanRequest.setVehicleCondition("Baru");
        loanRequest.setVehicleYear(Year.now().getValue());
        loanRequest.setTotalLoanAmount(100_000_000L);
        loanRequest.setLoanTenure(3);
        loanRequest.setDownPayment(30_000_000L);

        Optional<String> result = validationService.validateLoanRequest(loanRequest);
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals("DP untuk kendaraan baru minimal 35%.", result.get());
    }

    @Test
    public void testUsedCarInsufficientDP() {
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setVehicleType("Mobil");
        loanRequest.setVehicleCondition("Bekas");
        loanRequest.setVehicleYear(Year.now().getValue());
        loanRequest.setTotalLoanAmount(100_000_000L);
        loanRequest.setLoanTenure(3);
        loanRequest.setDownPayment(20_000_000L);

        Optional<String> result = validationService.validateLoanRequest(loanRequest);
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals("DP untuk kendaraan bekas minimal 25%.", result.get());
    }
}
