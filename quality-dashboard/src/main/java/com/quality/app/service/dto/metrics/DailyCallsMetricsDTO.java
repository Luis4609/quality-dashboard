package com.quality.app.service.dto.metrics;

public class DailyCallsMetricsDTO {

    private Integer totalReceivedCalls;
    private Integer totalAttendedCalls;
    private Integer totalLostCalls;

    private Integer totalAttendedCallsExternalAgent;

    private Integer totalDailyAttendedCallsInternalAgent;

    public DailyCallsMetricsDTO() {
    }

    public Integer getTotalReceivedCalls() {
        return totalReceivedCalls;
    }

    public void setTotalReceivedCalls(Integer totalReceivedCalls) {
        this.totalReceivedCalls = totalReceivedCalls;
    }

    public Integer getTotalAttendedCalls() {
        return totalAttendedCalls;
    }

    public void setTotalAttendedCalls(Integer totalAttendedCalls) {
        this.totalAttendedCalls = totalAttendedCalls;
    }

    public Integer getTotalLostCalls() {
        return totalLostCalls;
    }

    public void setTotalLostCalls(Integer totalLostCalls) {
        this.totalLostCalls = totalLostCalls;
    }

    public Integer getTotalAttendedCallsExternalAgent() {
        return totalAttendedCallsExternalAgent;
    }

    public void setTotalAttendedCallsExternalAgent(Integer totalAttendedCallsExternalAgent) {
        this.totalAttendedCallsExternalAgent = totalAttendedCallsExternalAgent;
    }

    public Integer getTotalDailyAttendedCallsInternalAgent() {
        return totalDailyAttendedCallsInternalAgent;
    }

    public void setTotalDailyAttendedCallsInternalAgent(Integer totalDailyAttendedCallsInternalAgent) {
        this.totalDailyAttendedCallsInternalAgent = totalDailyAttendedCallsInternalAgent;
    }

    @Override
    public String toString() {
        return "DailyCallsMetricsDTO{" +
            "totalReceivedCalls=" + totalReceivedCalls +
            ", totalAttendedCalls=" + totalAttendedCalls +
            ", totalLostCalls=" + totalLostCalls +
            ", totalAttendedCallsExternalAgent=" + totalAttendedCallsExternalAgent +
            ", totalDailyAttendedCallsInternalAgent=" + totalDailyAttendedCallsInternalAgent +
            '}';
    }
}
