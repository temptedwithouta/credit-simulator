import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.LoanRequest;
import org.example.service.ApiService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiServiceTest {
    private HttpClient mockClient;
    private HttpResponse<String> mockResponse;
    private ObjectMapper objectMapper;
    private ApiService apiService;

    @Before
    public void setUp() {
        this.mockClient = Mockito.mock(HttpClient.class);
        this.mockResponse = Mockito.mock(HttpResponse.class);
        this.objectMapper = new ObjectMapper();
        this.apiService = new ApiService() {
            {
                try {
                    var clientField = ApiService.class.getDeclaredField("client");
                    clientField.setAccessible(true);
                    clientField.set(this, mockClient);

                    var mapperField = ApiService.class.getDeclaredField("objectMapper");
                    mapperField.setAccessible(true);
                    mapperField.set(this, objectMapper);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        };
    }

    @Test
    public void testFetchLoanRequestSuccess() throws Exception {
        String jsonResponse = """
                {
                    "vehicleType": "Mobil",
                    "vehicleCondition": "Baru",
                    "vehicleYear": 2025,
                    "totalLoanAmount": 100000000,
                    "loanTenure": 3,
                    "downPayment": 35000000
                }
                """;

        Mockito.when(mockResponse.statusCode()).thenReturn(200);
        Mockito.when(mockResponse.body()).thenReturn(jsonResponse);
        Mockito.when(mockClient.send(Mockito.any(HttpRequest.class), Mockito.any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        LoanRequest loanRequest = apiService.fetchLoanRequest();

        Assert.assertNotNull(loanRequest);
        Assert.assertEquals("Mobil", loanRequest.getVehicleType());
        Assert.assertEquals("Baru", loanRequest.getVehicleCondition());
        Assert.assertEquals(2025, loanRequest.getVehicleYear(), 0);
        Assert.assertEquals(100_000_000L, loanRequest.getTotalLoanAmount(), 0);
        Assert.assertEquals(3, loanRequest.getLoanTenure(), 0);
        Assert.assertEquals(35_000_000L, loanRequest.getDownPayment(), 0);
    }

    @Test
    public void testFetchLoanRequestReturnsNullWhenNon200Response() throws Exception {
        Mockito.when(mockResponse.statusCode()).thenReturn(500);
        Mockito.when(mockClient.send(Mockito.any(HttpRequest.class), Mockito.any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        LoanRequest result = apiService.fetchLoanRequest();

        Assert.assertNull(result);
    }

    @Test
    public void testFetchLoanRequestThrowsIOException() throws Exception {
        Mockito.when(mockClient.send(Mockito.any(HttpRequest.class), Mockito.any(HttpResponse.BodyHandler.class)))
                .thenThrow(new IOException());

        Assert.assertThrows(IOException.class, () -> apiService.fetchLoanRequest());
    }

    @Test
    public void testFetchLoanRequestThrowsInterruptedException() throws Exception {
        Mockito.when(mockClient.send(Mockito.any(HttpRequest.class), Mockito.any(HttpResponse.BodyHandler.class)))
                .thenThrow(new InterruptedException());

        Assert.assertThrows(InterruptedException.class, () -> apiService.fetchLoanRequest());
    }
}
