package com.quality.app.service.dto.metrics;

import java.util.Date;

public interface IDailyCallsMetricsByDate {

    Integer getTotalReceivedCalls();

    Integer getTotalAttendedCalls();

    Integer getTotalLostCalls();

    Integer getTotalAttendedCallsExternalAgent();

    Integer getTotalAttendedCallsInternalAgent();

    Integer getMetricDate();
}
