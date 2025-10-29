package org.example.model;

public class LoanRequest {
    private String vehicleType;

    private String vehicleCondition;

    private Integer vehicleYear;

    private Long totalLoanAmount;

    private Integer loanTenure;

    private Long downPayment;

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleCondition() {
        return vehicleCondition;
    }

    public void setVehicleCondition(String vehicleCondition) {
        this.vehicleCondition = vehicleCondition;
    }

    public Integer getVehicleYear() {
        return vehicleYear;
    }

    public void setVehicleYear(Integer vehicleYear) {
        this.vehicleYear = vehicleYear;
    }

    public Long getTotalLoanAmount() {
        return totalLoanAmount;
    }

    public void setTotalLoanAmount(Long totalLoanAmount) {
        this.totalLoanAmount = totalLoanAmount;
    }

    public Integer getLoanTenure() {
        return loanTenure;
    }

    public void setLoanTenure(Integer loanTenure) {
        this.loanTenure = loanTenure;
    }

    public Long getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(Long downPayment) {
        this.downPayment = downPayment;
    }

    public String toString(){
        return String.format("Jenis: %s, Kondisi: %s, Tahun: %d, Pinjaman: Rp %,d, Tenor: %d tahun, DP: Rp %,d", vehicleType, vehicleCondition, vehicleYear, totalLoanAmount, loanTenure, downPayment);
    }
}
