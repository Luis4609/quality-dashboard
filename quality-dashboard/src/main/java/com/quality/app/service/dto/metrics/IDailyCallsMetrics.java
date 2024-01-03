package com.quality.app.service.dto.metrics;

public interface IDailyCallsMetrics {

    Integer getTotalReceivedCalls();

    Integer getTotalAttendedCalls();

    Integer getTotalLostCalls();

    Integer getTotalAttendedCallsExternalAgent();

    Integer getTotalAttendedCallsInternalAgent();
}
