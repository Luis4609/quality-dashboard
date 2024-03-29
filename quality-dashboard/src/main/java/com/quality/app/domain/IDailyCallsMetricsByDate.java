package com.quality.app.domain;

import java.sql.Date;

public interface IDailyCallsMetricsByDate {

    Integer getTotalReceivedCalls();

    Integer getTotalAttendedCalls();

    Integer getTotalLostCalls();

    Integer getTotalAttendedCallsExternalAgent();

    Integer getTotalAttendedCallsInternalAgent();

    Date getMetricDate();
}
