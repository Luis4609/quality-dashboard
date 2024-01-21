package com.quality.app.domain;

public interface IDailyCallsMetrics {

    Integer getTotalReceivedCalls();

    Integer getTotalAttendedCalls();

    Integer getTotalLostCalls();

    Integer getTotalAttendedCallsExternalAgent();

    Integer getTotalAttendedCallsInternalAgent();
}
